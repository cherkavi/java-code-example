/*
 * HelloMidlet.java
 *
 * Created on 2 травня 2007, 12:09
 */

package hello;

import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import javax.microedition.io.*;
import javax.wireless.messaging.*;

import java.io.*;
/**
 *
 * @author Техник
 */
public class HelloMidlet extends MIDlet implements CommandListener {
    
    /** Creates a new instance of HelloMidlet */
    static class comport{
        static String write_to_port(String s){
            CommConnection connection;
            java.io.InputStream connection_inputstream;
            java.io.OutputStream connection_outputstream;
            String portNames;    
            portNames=System.getProperty("microedition.commports");
            System.out.println(portNames);
            try{
                connection=(CommConnection)Connector.open("comm:0");
                //System.out.println("comm:0 is open");
                connection_inputstream=connection.openInputStream();
                //System.out.println("openInputStream - OK");
                connection_outputstream=connection.openOutputStream();
                //System.out.println("openOutputStream - OK");
                for(int i=0;i<s.length();i++){
                    connection_outputstream.write((int)s.charAt(i));
                    //System.out.print(s.charAt(i));
                }
                //System.out.println("\n OK");
                return "Write OK";
            }
            catch(java.io.IOException e){
                //System.out.println("Error in open port <comm0> \n"+e.getMessage());
                return "Error in open port";
            }
            
        }
    }
    
    class sender{
         void sms_send(String destination_number,String destination_text){
            String sms_port;
            javax.wireless.messaging.MessageConnection messageconnection;
            javax.wireless.messaging.TextMessage textmessage;
            try{
                sms_port=getAppProperty("SMS-Port");
                System.out.println("sms_port:"+sms_port);
                messageconnection = (javax.wireless.messaging.MessageConnection)Connector.open(sms_port);
                textmessage=(javax.wireless.messaging.TextMessage)messageconnection.newMessage(MessageConnection.TEXT_MESSAGE);
                textmessage.setAddress(destination_number);
                textmessage.setPayloadText(destination_text);
                messageconnection.send(textmessage);
                messageconnection.close();
            }
            catch(Exception e){
                System.out.println("Error in work with <messageconnection> \n"+e.getMessage());
            }
        }
    
    }
    
    public HelloMidlet() {
    }
    
    private Form helloForm;//GEN-BEGIN:MVDFields
    private Command exitCommand;
    private TextField textField_write;
    private TextField textField_read;
    private Command okCommand1;
    private TextField textField_destination_number;
    private TextField textField_destination_text;
    private Alert alert1;
    private Command okCommand2;
    private List list1;
    private Command okCommand3;
    private Ticker ticker1;//GEN-END:MVDFields
    
//GEN-LINE:MVDMethods

    /** This method initializes UI of the application.//GEN-BEGIN:MVDInitBegin
     */
    private void initialize() {//GEN-END:MVDInitBegin
        // Insert pre-init code here
        getDisplay().setCurrent(get_helloForm());//GEN-LINE:MVDInitInit
        // Insert post-init code here
    }//GEN-LINE:MVDInitEnd
    
    /** Called by the system to indicate that a command has been invoked on a particular displayable.//GEN-BEGIN:MVDCABegin
     * @param command the Command that ws invoked
     * @param displayable the Displayable on which the command was invoked
     */
    public void commandAction(Command command, Displayable displayable) {//GEN-END:MVDCABegin
        // Insert global pre-action code here
        if (displayable == helloForm) {//GEN-BEGIN:MVDCABody
            if (command == exitCommand) {//GEN-END:MVDCABody
                // Insert pre-action code here
                exitMIDlet();//GEN-LINE:MVDCAAction3
                // Insert post-action code here
            } else if (command == okCommand1) {//GEN-LINE:MVDCACase3
                // Insert pre-action code here
                // Do nothing//GEN-LINE:MVDCAAction9
                // Insert post-action code here
                // open port and write to 
                //textField_read.setString(comport.write_to_port(this.textField_write.getString()));
                sender s;
                s=new sender();
                s.sms_send(this.textField_destination_number.getString(),this.textField_destination_text.getString());
            } else if (command == okCommand2) {//GEN-LINE:MVDCACase9
                // Insert pre-action code here
                getDisplay().setCurrent(get_alert1(), get_list1());//GEN-LINE:MVDCAAction14
                // Insert post-action code here
            } else if (command == okCommand3) {//GEN-LINE:MVDCACase14
                // Insert pre-action code here
                getDisplay().setCurrent(get_list1());//GEN-LINE:MVDCAAction18
                // Insert post-action code here
            }//GEN-BEGIN:MVDCACase18
        } else if (displayable == list1) {
            if (command == list1.SELECT_COMMAND) {
                switch (get_list1().getSelectedIndex()) {
                    case 0://GEN-END:MVDCACase18
                        // Insert pre-action code here
                        getDisplay().setCurrent(get_alert1(), get_list1());//GEN-LINE:MVDCAAction20
                        // Insert post-action code here
                        break;//GEN-BEGIN:MVDCACase20
                    case 1://GEN-END:MVDCACase20
                        // Insert pre-action code here
                        getDisplay().setCurrent(get_helloForm());//GEN-LINE:MVDCAAction22
                        // Insert post-action code here
                        break;//GEN-BEGIN:MVDCACase22
                    case 2://GEN-END:MVDCACase22
                        // Insert pre-action code here
                        // Do nothing//GEN-LINE:MVDCAAction24
                        // Insert post-action code here
                        break;//GEN-BEGIN:MVDCACase24
                    case 3://GEN-END:MVDCACase24
                        // Insert pre-action code here
                        // Do nothing//GEN-LINE:MVDCAAction26
                        // Insert post-action code here
                        break;//GEN-BEGIN:MVDCACase26
                }
            }
        }//GEN-END:MVDCACase26
        // Insert global post-action code here
}//GEN-LINE:MVDCAEnd
    
    /**
     * This method should return an instance of the display.
     */
    public Display getDisplay() {//GEN-FIRST:MVDGetDisplay
        return Display.getDisplay(this);
    }//GEN-LAST:MVDGetDisplay
    
    /**
     * This method should exit the midlet.
     */
    public void exitMIDlet() {//GEN-FIRST:MVDExitMidlet
        getDisplay().setCurrent(null);
        destroyApp(true);
        notifyDestroyed();
    }//GEN-LAST:MVDExitMidlet
    
    /** This method returns instance for helloForm component and should be called instead of accessing helloForm field directly.//GEN-BEGIN:MVDGetBegin2
     * @return Instance for helloForm component
     */
    public Form get_helloForm() {
        if (helloForm == null) {//GEN-END:MVDGetBegin2
            // Insert pre-init code here
            helloForm = new Form(null, new Item[] {//GEN-BEGIN:MVDGetInit2
                get_textField_write(),
                get_textField_read(),
                get_textField_destination_number(),
                get_textField_destination_text()
            });
            helloForm.addCommand(get_exitCommand());
            helloForm.addCommand(get_okCommand1());
            helloForm.addCommand(get_okCommand2());
            helloForm.addCommand(get_okCommand3());
            helloForm.setCommandListener(this);//GEN-END:MVDGetInit2
            // Insert post-init code here
        }//GEN-BEGIN:MVDGetEnd2
        return helloForm;
    }//GEN-END:MVDGetEnd2
    
    
    /** This method returns instance for exitCommand component and should be called instead of accessing exitCommand field directly.//GEN-BEGIN:MVDGetBegin5
     * @return Instance for exitCommand component
     */
    public Command get_exitCommand() {
        if (exitCommand == null) {//GEN-END:MVDGetBegin5
            // Insert pre-init code here
            exitCommand = new Command("Exit", Command.EXIT, 1);//GEN-LINE:MVDGetInit5
            // Insert post-init code here
        }//GEN-BEGIN:MVDGetEnd5
        return exitCommand;
    }//GEN-END:MVDGetEnd5

    /** This method returns instance for textField_write component and should be called instead of accessing textField_write field directly.//GEN-BEGIN:MVDGetBegin6
     * @return Instance for textField_write component
     */
    public TextField get_textField_write() {
        if (textField_write == null) {//GEN-END:MVDGetBegin6
            // Insert pre-init code here
            textField_write = new TextField("text writ to port", "hello from phone", 120, TextField.ANY);//GEN-LINE:MVDGetInit6
            // Insert post-init code here
        }//GEN-BEGIN:MVDGetEnd6
        return textField_write;
    }//GEN-END:MVDGetEnd6

    /** This method returns instance for textField_read component and should be called instead of accessing textField_read field directly.//GEN-BEGIN:MVDGetBegin7
     * @return Instance for textField_read component
     */
    public TextField get_textField_read() {
        if (textField_read == null) {//GEN-END:MVDGetBegin7
            // Insert pre-init code here
            textField_read = new TextField("text read from port", null, 120, TextField.ANY);//GEN-LINE:MVDGetInit7
            // Insert post-init code here
        }//GEN-BEGIN:MVDGetEnd7
        return textField_read;
    }//GEN-END:MVDGetEnd7

    /** This method returns instance for okCommand1 component and should be called instead of accessing okCommand1 field directly.//GEN-BEGIN:MVDGetBegin8
     * @return Instance for okCommand1 component
     */
    public Command get_okCommand1() {
        if (okCommand1 == null) {//GEN-END:MVDGetBegin8
            // Insert pre-init code here
            okCommand1 = new Command("write to port", Command.OK, 1);//GEN-LINE:MVDGetInit8
            // Insert post-init code here
        }//GEN-BEGIN:MVDGetEnd8
        return okCommand1;
    }//GEN-END:MVDGetEnd8

    /** This method returns instance for textField_destination_number component and should be called instead of accessing textField_destination_number field directly.//GEN-BEGIN:MVDGetBegin10
     * @return Instance for textField_destination_number component
     */
    public TextField get_textField_destination_number() {
        if (textField_destination_number == null) {//GEN-END:MVDGetBegin10
            // Insert pre-init code here
            textField_destination_number = new TextField("Number destination", "+380971717367", 120, TextField.ANY);//GEN-LINE:MVDGetInit10
            // Insert post-init code here
        }//GEN-BEGIN:MVDGetEnd10
        return textField_destination_number;
    }//GEN-END:MVDGetEnd10

    /** This method returns instance for textField_destination_text component and should be called instead of accessing textField_destination_text field directly.//GEN-BEGIN:MVDGetBegin11
     * @return Instance for textField_destination_text component
     */
    public TextField get_textField_destination_text() {
        if (textField_destination_text == null) {//GEN-END:MVDGetBegin11
            // Insert pre-init code here
            textField_destination_text = new TextField("Text SMS for destination", "hello from java", 120, TextField.ANY);//GEN-LINE:MVDGetInit11
            // Insert post-init code here
        }//GEN-BEGIN:MVDGetEnd11
        return textField_destination_text;
    }//GEN-END:MVDGetEnd11

    /** This method returns instance for alert1 component and should be called instead of accessing alert1 field directly.//GEN-BEGIN:MVDGetBegin12
     * @return Instance for alert1 component
     */
    public Alert get_alert1() {
        if (alert1 == null) {//GEN-END:MVDGetBegin12
            // Insert pre-init code here
            alert1 = new Alert(null, "Alert - message", null, AlertType.ERROR);//GEN-BEGIN:MVDGetInit12
            alert1.setTimeout(5000);//GEN-END:MVDGetInit12
            // Insert post-init code here
        }//GEN-BEGIN:MVDGetEnd12
        return alert1;
    }//GEN-END:MVDGetEnd12

    /** This method returns instance for okCommand2 component and should be called instead of accessing okCommand2 field directly.//GEN-BEGIN:MVDGetBegin13
     * @return Instance for okCommand2 component
     */
    public Command get_okCommand2() {
        if (okCommand2 == null) {//GEN-END:MVDGetBegin13
            // Insert pre-init code here
            okCommand2 = new Command("Show Alert", Command.OK, 1);//GEN-LINE:MVDGetInit13
            // Insert post-init code here
        }//GEN-BEGIN:MVDGetEnd13
        return okCommand2;
    }//GEN-END:MVDGetEnd13

    /** This method returns instance for list1 component and should be called instead of accessing list1 field directly.//GEN-BEGIN:MVDGetBegin15
     * @return Instance for list1 component
     */
    public List get_list1() {
        if (list1 == null) {//GEN-END:MVDGetBegin15
            // Insert pre-init code here
            list1 = new List(null, Choice.EXCLUSIVE, new String[] {//GEN-BEGIN:MVDGetInit15
                "List Element",
                "List Element",
                "List Element",
                "List Element"
            }, new Image[] {
                null,
                null,
                null,
                null
            });
            list1.setCommandListener(this);
            list1.setTicker(get_ticker1());
            list1.setSelectedFlags(new boolean[] {
                false,
                false,
                false,
                false
            });//GEN-END:MVDGetInit15
            // Insert post-init code here
        }//GEN-BEGIN:MVDGetEnd15
        return list1;
    }//GEN-END:MVDGetEnd15

    /** This method returns instance for okCommand3 component and should be called instead of accessing okCommand3 field directly.//GEN-BEGIN:MVDGetBegin17
     * @return Instance for okCommand3 component
     */
    public Command get_okCommand3() {
        if (okCommand3 == null) {//GEN-END:MVDGetBegin17
            // Insert pre-init code here
            okCommand3 = new Command("Show List", Command.OK, 1);//GEN-LINE:MVDGetInit17
            // Insert post-init code here
        }//GEN-BEGIN:MVDGetEnd17
        return okCommand3;
    }//GEN-END:MVDGetEnd17

    /** This method returns instance for ticker1 component and should be called instead of accessing ticker1 field directly.//GEN-BEGIN:MVDGetBegin27
     * @return Instance for ticker1 component
     */
    public Ticker get_ticker1() {
        if (ticker1 == null) {//GEN-END:MVDGetBegin27
            // Insert pre-init code here
            ticker1 = new Ticker("<Enter Ticker Text>");//GEN-LINE:MVDGetInit27
            // Insert post-init code here
        }//GEN-BEGIN:MVDGetEnd27
        return ticker1;
    }//GEN-END:MVDGetEnd27
    
    public void startApp() {
        initialize();
    }
    
    public void pauseApp() {
    }
    
    public void destroyApp(boolean unconditional) {
    }
    
}
