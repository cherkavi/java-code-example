/*
 * HelloMidlet.java
 *
 * Created on 1 Èþëü 2007 ã., 15:50
 */

package hello;

import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import com.siemens.mp.io.*;

/**
 *
 * @author root
 */
public class HelloMidlet extends MIDlet implements CommandListener,ConnectionListener {
    
    /** Creates a new instance of HelloMidlet */
    public HelloMidlet() {
    }
    
    private Form helloForm;//GEN-BEGIN:MVDFields
    private Command exitCommand;
    private TextField textField;
    private Command Command_send;
    private Command Command_clear;//GEN-END:MVDFields
    private com.siemens.mp.io.Connection connection=null;
    
    
//GEN-LINE:MVDMethods

    /** This method initializes UI of the application.//GEN-BEGIN:MVDInitBegin
     */
    private void initialize() {//GEN-END:MVDInitBegin
        // Insert pre-init code here
        getDisplay().setCurrent(get_helloForm());//GEN-LINE:MVDInitInit
        // Insert post-init code here
        try{
            connection=new com.siemens.mp.io.Connection("COM0:;baudrate=9600");
            connection.setListener(this);
            this.textField.setString("COM0:");
        }
        catch(Exception e){
            this.textField.setString("Error init COM:"+e.getMessage());
        }
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
            } else if (command == Command_send) {//GEN-LINE:MVDCACase3
                // Insert pre-action code here
                // Do nothing//GEN-LINE:MVDCAAction8
                // Insert post-action code here
                this.textField.setString("Begin send");
                try{
                    this.connection.send("Hello from Siemens".getBytes());
                    this.textField.setString("Sended string");
                }
                catch(Exception e){
                    this.textField.setString("Error send:"+e.getMessage());
                }
            } else if (command == Command_clear) {//GEN-LINE:MVDCACase8
                // Insert pre-action code here
                // Do nothing//GEN-LINE:MVDCAAction10
                // Insert post-action code here
                this.textField.setString("");
            }//GEN-BEGIN:MVDCACase10
        }//GEN-END:MVDCACase10
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
            helloForm = new Form(null, new Item[] {get_textField()});//GEN-BEGIN:MVDGetInit2
            helloForm.addCommand(get_exitCommand());
            helloForm.addCommand(get_Command_send());
            helloForm.addCommand(get_Command_clear());
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

    /** This method returns instance for textField component and should be called instead of accessing textField field directly.//GEN-BEGIN:MVDGetBegin6
     * @return Instance for textField component
     */
    public TextField get_textField() {
        if (textField == null) {//GEN-END:MVDGetBegin6
            // Insert pre-init code here
            textField = new TextField("Information", null, 120, TextField.ANY);//GEN-LINE:MVDGetInit6
            // Insert post-init code here
        }//GEN-BEGIN:MVDGetEnd6
        return textField;
    }//GEN-END:MVDGetEnd6

    /** This method returns instance for Command_send component and should be called instead of accessing Command_send field directly.//GEN-BEGIN:MVDGetBegin7
     * @return Instance for Command_send component
     */
    public Command get_Command_send() {
        if (Command_send == null) {//GEN-END:MVDGetBegin7
            // Insert pre-init code here
            Command_send = new Command("Send", Command.OK, 1);//GEN-LINE:MVDGetInit7
            // Insert post-init code here
        }//GEN-BEGIN:MVDGetEnd7
        return Command_send;
    }//GEN-END:MVDGetEnd7

    /** This method returns instance for Command_clear component and should be called instead of accessing Command_clear field directly.//GEN-BEGIN:MVDGetBegin9
     * @return Instance for Command_clear component
     */
    public Command get_Command_clear() {
        if (Command_clear == null) {//GEN-END:MVDGetBegin9
            // Insert pre-init code here
            Command_clear = new Command("Clear", Command.OK, 2);//GEN-LINE:MVDGetInit9
            // Insert post-init code here
        }//GEN-BEGIN:MVDGetEnd9
        return Command_clear;
    }//GEN-END:MVDGetEnd9
    public synchronized void set_string(String s){
        this.textField.setString(s);
    }
    public void startApp() {
        initialize();
    }
    
    public void pauseApp() {
    }
    
    public void destroyApp(boolean unconditional) {
    }

    public void receiveData(byte[] b) {
        this.set_string("recieve:"+(new String(b,0,b.length)));
    }
    
}
