package sap.usb.crd.cetelem;

import sap.usb.tech.GatewaySAPConnector;

import java.util.Hashtable;
import java.util.Vector;

import com.sap.mw.jco.JCO;

/**
 * @author Oleksiy Pastukhov axvpast@gmail.com
 *         Date: Aug 20, 2009
 */
public class cdrFunctionCRC {

    public static void main( String [] argv ){
        JCO.Client connection = null;
        Vector<Hashtable<String, String>> returnDeal = null;
        Vector<Hashtable<String, String>> errorDeal = null;
        Vector<Vector<Hashtable<String, String>>> response = new Vector(2);
        try {
          GatewaySAPConnector connector = new GatewaySAPConnector(
              GatewaySAPConnector.
              CONNECT_TO_R3_SYSTEM);
          connection = connector.getConnection();
          JCO.Function function = connector.getFunction("ZAM_FN_CHECK_BP_IN_TERR");
          function.writeHTML("ZAM_FN_CHECK_BP_IN_TERR.html");
          function.writeXML("ZAM_FN_CHECK_BP_IN_TERR.xml");
        } catch (Exception ex){
            ex.printStackTrace();
        } finally {
          if (connection != null) {
            JCO.releaseClient(connection); //release the SAP connection back to the pool
          }
        }

    }

}
