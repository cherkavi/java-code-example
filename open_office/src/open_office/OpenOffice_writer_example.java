/*
 * OpenOffice_writer_example.java
 *
 * Created on 7 квітня 2008, 7:46
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package open_office;
import com.sun.star.beans.*;
import com.sun.star.util.*;
import com.sun.star.io.*;
import com.sun.star.connection.*;
import com.sun.star.lang.*;
import com.sun.star.text.*;
import com.sun.star.frame.*;
import com.sun.star.container.*;
import com.sun.star.view.XPrintable;
import com.sun.star.uno.UnoRuntime;
import com.sun.star.uno.XInterface;
import com.sun.star.uno.XNamingService;
import com.sun.star.uno.XComponentContext;
import java.util.HashMap;
import javax.swing.JOptionPane;

/**
 *
 * @author Technik
 */
public class OpenOffice_writer_example {
    private static XComponentContext xRemoteContext = null;
    private static XMultiComponentFactory xRemoteServiceManager = null;
    private static XURLTransformer xTransformer = null;
    private static XComponentLoader xComponentLoader = null;
    private static XDesktop xDesktop = null;    
    /**
     * Creates a new instance of OpenOffice_writer_example
     */
    public OpenOffice_writer_example() {
        HashMap<String,String> variableMap = new HashMap<String,String>();
    
        variableMap.put("CONTRACT_NUMBER", "1234567");    
        //variableMap.put("CONTRACT_DATE", "31   2007  .");
        //variableMap.put("EXECUTOR", "  \"     \"");
        //variableMap.put("EXECUTOR_PERSON", "     ");
        //variableMap.put("CUSTOMER", "     ");
    
        try {
            connect();
      
            XComponent currentDocument = openDocument("temp.odt");
      
            XTextFieldsSupplier xTextFieldsSupplier = (XTextFieldsSupplier)UnoRuntime.queryInterface(
                XTextFieldsSupplier.class, currentDocument);
            //         
            XEnumerationAccess xEnumerationAccess = xTextFieldsSupplier.getTextFields();
            XEnumeration xTextFieldsEnumeration = xEnumerationAccess.createEnumeration();
            XRefreshable xRefreshable = (XRefreshable)UnoRuntime.queryInterface(XRefreshable.class, xEnumerationAccess);
            while ( xTextFieldsEnumeration.hasMoreElements() ) {
                Object service = xTextFieldsEnumeration.nextElement();
                XServiceInfo xServiceInfo = (XServiceInfo)UnoRuntime.queryInterface(XServiceInfo.class, service);
        
                if (xServiceInfo.supportsService("com.sun.star.text.TextField.SetExpression")) {
                    XPropertySet xPropertySet = (XPropertySet)UnoRuntime.queryInterface(XPropertySet.class, service);
                    String name = (String)xPropertySet.getPropertyValue("VariableName");
                    Object content = variableMap.get(name);
                    xPropertySet.setPropertyValue("SubType",new Short(com.sun.star.text.SetVariableType.STRING));
                    xPropertySet.setPropertyValue("Content",content == null ? " " : content.toString());
                    xPropertySet.setPropertyValue("IsVisible", true);
                }
            }            
            xRefreshable.refresh();
      
            /*   
             * printDocument(currentDocument) -         
             * saveDocument (currentDocument, props) -     
             * saveAsDocument (currentDocument, sURL, props) -       
             */
            //String[] cmds = {"Print"};
            //executeCommands(cmds);
            /*closeDocument(currentDocument, false);
            XCloseable c = (XCloseable)UnoRuntime.queryInterface(XCloseable.class, currentDocument);
            while(true){
                try {
                    c.close(false);
                    break;
                } catch (com.sun.star.util.CloseVetoException e) {
                }            
            }
            Thread.sleep(200);*/
        }catch(Exception e) {
            System.out.println("     ");
        }
    }
    public static void connect() throws Exception {
        xRemoteContext = com.sun.star.comp.helper.Bootstrap.bootstrap();
        // adding library: bootstrapconnector.jar
        //import ooo.connector.BootstrapSocketConnector;
        //String oooExeFolder = "C:/Program Files/OpenOffice.org 2.0.2/program/";
        //xRemoteContext = BootstrapSocketConnector.bootstrap(oooExeFolder);

        xRemoteServiceManager = xRemoteContext.getServiceManager();
        //    ,        
        Object transformer = xRemoteServiceManager.createInstanceWithContext("com.sun.star.util.URLTransformer", xRemoteContext );
        xTransformer = (XURLTransformer)UnoRuntime.queryInterface(XURLTransformer.class, transformer);
    
        //     Desktop
        Object desktop = (XInterface) xRemoteServiceManager.createInstanceWithContext("com.sun.star.frame.Desktop", xRemoteContext);
        xDesktop = (XDesktop)UnoRuntime.queryInterface(XDesktop.class, desktop);
    
        //             
        xComponentLoader = (XComponentLoader)UnoRuntime.queryInterface(XComponentLoader.class, desktop);        
    }
    
    public static XComponent openDocument(String sURL) throws Exception {
        //           URL
        java.io.File sourceFile = new java.io.File(sURL);
        StringBuffer sTmp = new StringBuffer("file:///");
        sTmp.append(sourceFile.getCanonicalPath().replace('\\', '/'));
        sURL = sTmp.toString();
    
        PropertyValue[] loadProps = new PropertyValue[0];
        return xComponentLoader.loadComponentFromURL(sURL, "_blank", 0, loadProps);
    }    
    public static boolean closeDocument(XComponent comp, boolean askIfVetoed) {
        XCloseable c = (XCloseable)UnoRuntime.queryInterface(XCloseable.class, comp);
        boolean dispose = true;
        try {
            c.close(false);
        } catch (com.sun.star.util.CloseVetoException e) {
            if ( askIfVetoed ) {
                int action = JOptionPane.showConfirmDialog(null, "    !" +"     ?"," ", JOptionPane.WARNING_MESSAGE, JOptionPane.YES_NO_OPTION);
                if ( JOptionPane.NO_OPTION == action ) {
                    dispose = false;
                }
            }
            if ( dispose ) {
                comp.dispose();
            }    
        }
        return dispose;
    }    
    
    public static void printDocument(XComponent comp) throws com.sun.star.lang.IllegalArgumentException {
        XPrintable xPrintable = (XPrintable)UnoRuntime.queryInterface(XPrintable.class, comp);
        PropertyValue[] printOpts = new PropertyValue[0];
        xPrintable.print(printOpts);
    }    
    public static void saveDocument(XComponent comp, PropertyValue[] props) {
        XStorable store = (XStorable)UnoRuntime.queryInterface(XStorable.class, comp);    
        saveAsDocument(comp, store.getLocation(), props);
    }        
    public static void saveAsDocument(XComponent comp, String aURL, PropertyValue[] props) {
        XStorable store = (XStorable)UnoRuntime.queryInterface(XStorable.class, comp);
        try {
            store.storeToURL(aURL, props);
        } catch (Exception e) {
            System.out.println( "       !" + e );
        }
    }

    public static void executeCommands( String[] commands ) throws com.sun.star.uno.Exception {
        //     
        XFrame xFrame = xDesktop.getCurrentFrame();
        //     DispatchProvider.
        XDispatchProvider xDispatchProvider = (XDispatchProvider)UnoRuntime.queryInterface(XDispatchProvider.class, xFrame );
        for ( int n = 0; n < commands.length; n++ ) {
            //   URL
            com.sun.star.util.URL[] aURL  = new com.sun.star.util.URL[1];
            aURL[0] = new com.sun.star.util.URL();
            com.sun.star.frame.XDispatch xDispatch = null;
      
            aURL[0].Complete = ".uno:" + commands[n];
            xTransformer.parseSmart( aURL, ".uno:" );    
            xDispatch = xDispatchProvider.queryDispatch( aURL[0], "", 0 );
            if ( xDispatch != null ) {
                com.sun.star.beans.PropertyValue[] lParams = new com.sun.star.beans.PropertyValue[0];
                Object obj = xRemoteServiceManager.createInstanceWithContext("com.sun.star.frame.DispatchHelper", xRemoteContext );
                XDispatchHelper dh = (XDispatchHelper)UnoRuntime.queryInterface(XDispatchHelper.class, obj);
                dh.executeDispatch(xDispatchProvider, aURL[0].Complete, "", 0, lParams);
            } else {
                System.out.println( "        " + aURL[0].Complete );
            }
        }
    }        
}
