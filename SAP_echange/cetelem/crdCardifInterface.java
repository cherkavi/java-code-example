package sap.usb.crd.cetelem;

import java.util.*;
import java.net.URL;
import java.net.URLConnection;
import java.io.*;

import org.apache.log4j.*;

/**
 * SAP Gateway Symmetric protocol implementation.
 * <p/>
 * Protocol contains 3 parameters:
 * Request vector is:
 * 1. function name
 * 2. encrypted or signed function arguments as byte[]
 * 3. reserved (null)
 * <p/>
 * Response vector is:
 * 1. function name
 * 2. errorMessage from GW side or null if no critical errors happens
 * 3. warningMessage from GW side or null if warnings happens or ping response for pingGW call which processid by cardridge.
 * 4. returned encrypted or signed object as byte[]
 * 5. reserved (null)
 * <p/>
 * Question: Why Request[2] and Response[4] is byte[] and not Object?
 * Answer: We do not need to do deserialize & serialize function arguments on SAP GW server, because our
 * interface classes is not deployed in that server.
 *
 * <p>
 * Configuration parameters:
 *
 * sap.usb.crd.cetelem.crdCardifInterface.endpoint.protocol=http - cardif host IP
 * sap.usb.crd.cetelem.crdCardifInterface.endpoint.host= - cardif host IP
 * sap.usb.crd.cetelem.crdCardifInterface.endpoint.port=80 - cardif host port
 * sap.usb.crd.cetelem.crdCardifInterface.endpoint.path=/srvLocalCrdObjHandler
 *
 * @author Oleksiy Pastukhov axvpast@gmail.com
 *         Date: Jun 11, 2009
 */

public class crdCardifInterface extends sap.usb.crd.abstractLogicalCartridge {

    Logger log = org.apache.log4j.Logger.getLogger( this.getClass().getName());

    // Copy & Paste from ua.cetelem.insurance.plugins.cardif.CardifOnlineGWProtocol
    public static String GW_CARDRIDGE_FUNCTION_PING_GW = "pingGW";
    public static String CONFIG_FILE_NAME = "crdCardifInterface.ini";

    /**
     * Creates a new instance
     */
    public crdCardifInterface() {
        // just for initialization
        getCardifEndpoint();
    }

    // static = reduse system load
    static File configFile = null;
    static long configFileLastModified = 0;
    static String endpoint = null;

    /**
     * Read configuration parameters and return cardif endpoint URL.
     * 
     * @return full endpoint URL
     * */
    synchronized String getCardifEndpoint() {
        if(configFile == null){
            String configFileName = null;
            configFileName = conf.ConfigParams.getConfigParam("INIT_FILES_PATH");
            if(configFileName == null){
                throw new RuntimeException("conf.ConfigParams.getConfigParam(\"INIT_FILES_PATH\") return null.");
            }
            if(!(configFileName.endsWith("/") || configFileName.endsWith("\\"))){
                // needs slash at the end
                configFileName += "/";
            }
            configFile = new File(configFileName + CONFIG_FILE_NAME);
            if( !configFile.exists() || !configFile.canRead() ){
                throw new RuntimeException("Configuration file:'" + configFileName + "' does noe exist or cannot be read.");
            }
        }
        if(configFileLastModified < configFile.lastModified()){
            // file modified
            log.debug("Detected config file modification. Reload configuration.");
            Properties props = new Properties();
            FileInputStream fis = null;
            try {
                fis = new FileInputStream( configFile );
                props.load(fis);
                fis.close();
            } catch (IOException e) {
                throw new RuntimeException("Cannot load configuration from file:'" + configFile.getAbsolutePath()+ "'.");
            }
            configFileLastModified = configFile.lastModified();
            String host = props.getProperty("sap.usb.crd.cetelem.crdCardifInterface.endpoint.host");
            String port = props.getProperty("sap.usb.crd.cetelem.crdCardifInterface.endpoint.port");
            String path = props.getProperty("sap.usb.crd.cetelem.crdCardifInterface.endpoint.path");
            String protocol = props.getProperty("sap.usb.crd.cetelem.crdCardifInterface.endpoint.protocol");
            endpoint = protocol + "://" + host + ":" + port + path;
        }
        return endpoint;
    }


    /**
     * Build report error to caller.
     *
     * @param message error text
     * @param funcName which finction return error
     * @return vector with error message
     * */
    Vector returnError(String funcName, String message) {
        log.error(message);
        Vector dataReturn = new Vector(5);
        //noinspection unchecked
        dataReturn.add(funcName);
        //noinspection unchecked
        dataReturn.add(message);
        //noinspection unchecked
        dataReturn.add(null);
        //noinspection unchecked
        dataReturn.add(null);
        //noinspection unchecked
        dataReturn.add(null);
        return dataReturn;
    }

    /**
     * Special mode response.
     *
     * Use (new byte[]{}) as encrypted payload and warnField to return ping response.
     *
     * @return pingGW response vector
     * */
    Vector returnPingGW() {
        log.info("pingGW call handled by cardridge.");
        Vector dataReturn = new Vector(5);
        String ip = null;
        try {
            ip = java.net.InetAddress.getLocalHost().getHostAddress();
        }catch(Exception ex){
            log.warn("java.net.InetAddress.getLocalHost().getHostAddress() throws error:"+ ex.getMessage());
        }
        //noinspection unchecked
        dataReturn.add(GW_CARDRIDGE_FUNCTION_PING_GW);
        //noinspection unchecked
        dataReturn.add(null); // no error
        //noinspection unchecked
        dataReturn.add(this.getClass().getName() + ";$Revision: 4846 $;$Date: 2009-06-12 18:23:42 +0300 (Fri, 12 Jun 2009) $:" + (new Date()) + ";" + ip + ";" + getCardifEndpoint());
        //noinspection unchecked
        dataReturn.add(new byte[]{}); // empty payload, because for ping we have warnField in response.
        //noinspection unchecked
        dataReturn.add(null);
        return dataReturn;
    }

    // @Override
    public Vector startProcessing(Vector in) {
        log.info("Begin processing");
        log.debug("Incoming Vector: " + in);
        // Stage 1 validate parameters
        // first vector - function name, we detects only type
        if (in == null || in.size() < 2) {
            // wrong request
            return returnError(null,"Request does not contain payload.");
        }
        if (!(in.get(1) instanceof Vector)) {
            return returnError(null,"Requestfirst vector payload is not a Vector.");
        }
        // Stage 2 - detecting commands
        // Payload vector
        Vector inRequest = (Vector) in.get(1);
        if (inRequest.size() < 3) {
            return returnError(null,"Protocol error, request parameters vector size is less than 3.");
        }
        if (!(inRequest.get(0) instanceof String)) {
            return returnError(null,"Protocol error, function name is missing or passed value is not String.");
        }
        String funcName = (String) inRequest.get(0);
        log.debug("Requested function is: " + funcName);

        // intercept pingGW function
        if (GW_CARDRIDGE_FUNCTION_PING_GW.equals(funcName)) {
            log.info("End Processing");
            return returnPingGW();
        }
        // ok, we need to retransfer in vector to endpoint.
        log.debug("Requested function is: " + funcName);
        // Stage 3 - processing request to endpoint
        Vector out;
        try {
            out = processHttpObjRequest(in); // full vector including first level vector
        } catch(Exception ex){
            // any exception here is fatal
            return returnError(funcName, "Protocol error, excaption happens when try to do server call by url:" + getCardifEndpoint());
        }
        log.info("End processing, SUCCESS.");
        return out;
    }

    /**
     * Copy & paste from GWClient wih small modification.
     *
     *  Process request via http, read response.
     * @param vecIn in vector
     * @return vector from remote side
     * @throws Exception all exception from this method is fatal
     * */
    private Vector processHttpObjRequest(Vector vecIn) throws Exception {
        //**** process request via http using object protocol.
        Vector vecOut; //  = null;
        String url = getCardifEndpoint();

        //connect to Webserver
        URL Url = new java.net.URL(url);
        URLConnection urlConn = Url.openConnection();
        urlConn.setDoInput(true); //by default
        urlConn.setDoOutput(true); //allow write to URLConnection
        urlConn.setAllowUserInteraction(false);
        //UrlConn.setRequestProperty(key, value);
        urlConn.connect();

        //write data to connection
        ObjectOutputStream
                oos = new ObjectOutputStream(urlConn.getOutputStream());
        oos.writeObject(vecIn);
        oos.flush();
        //oos.close();//not here !!! clinch in this place!!!//

        //read web-server response
        InputStream is = urlConn.getInputStream();//run servlet via UrlConnection
        ObjectInputStream ois = new ObjectInputStream(is);
        vecOut = (Vector) ois.readObject();

        ois.close();//ok
        oos.close();//ok

        return vecOut;
    }


}
