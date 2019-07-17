/*
 * HelloMidlet.java
 *
 * Created on 26 Èþíü 2007 ã., 13:34
 */

package hello;

import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import java.io.*;
import javax.microedition.io.*;
/**
 *
 * @author root
 */
class OutputStreamListener implements Runnable{
    private OutputStream os=null;
    private boolean flag_thread_continue=true;
    private Thread t=null;
    private TextField textField;
    byte[] buffer=new byte[255];
    private String output_text=null;
    OutputStreamListener(OutputStream os,String s,TextField textField){
        this.os=os;
        this.output_text=s;
        this.textField=textField;
        t=(new Thread(this));
        t.start();
    }
    public void run(){
        buffer=this.output_text.getBytes();
        try{
            os.write(buffer,0,this.output_text.length());
            os.flush();
            this.textField.setString(this.output_text);
        }
        catch(Exception e){
            this.textField.setString("Error send data:"+e.getMessage());
        }
    }
}
class InputStreamListener implements Runnable{
    private InputStream is=null;
    private boolean flag_thread_continue=true;
    private Thread t=null;
    private TextField textField;
    byte[] buffer=new byte[255];
    private OutputStream os=null;
    HelloMidlet midlet;
    InputStreamListener(InputStream is,HelloMidlet midlet,OutputStream os){
        this.is=is;
        this.os=os;
        this.midlet=midlet;
        t=(new Thread(this));
        t.start();
    }
    
    public void stop_thread(){
        this.flag_thread_continue=false;
    }
    public void run() {
        int counter=0;
        int read_byte=0;
        try{
            while(this.flag_thread_continue==true){
                if(is.available()>0){
                    read_byte=is.read();
                    if(read_byte=='\n'){
                        buffer[counter++]=(byte)read_byte;
                        String s=(new String(buffer,0,counter-1)).toLowerCase();
                        os.write(s.toLowerCase().getBytes());
                        midlet.set_textField(s.toLowerCase());
                        counter=0;
                    }
                    else{
                        buffer[counter++]=(byte)read_byte;
                    }
                }
            }
        }
        catch(Exception e){
            this.textField.setString("Error read:"+e.getMessage());
        }
    }
    
}
public class HelloMidlet extends MIDlet implements CommandListener {
    private javax.microedition.io.CommConnection com_port=null;
    private InputStream is=null;
    private OutputStream os=null;
    private InputStreamListener inputStreamListener=null;
    /** Creates a new instance of HelloMidlet */
    public HelloMidlet() {
    }
    
    private Form helloForm;//GEN-BEGIN:MVDFields
    private Command exitCommand;
    private Command okCommand_info;
    private Command okCommand_send;
    private TextField textField;
    private Command okCommand_clear;
    private Command okCommand_mirror;//GEN-END:MVDFields
    
//GEN-LINE:MVDMethods

    /** This method initializes UI of the application.//GEN-BEGIN:MVDInitBegin
     */
    private void initialize() {//GEN-END:MVDInitBegin
        // Insert pre-init code here
        getDisplay().setCurrent(get_helloForm());//GEN-LINE:MVDInitInit
        // Insert post-init code here
       try{
           com_port=(CommConnection)Connector.open("comm:com0;baudrate=9600");
           os=com_port.openOutputStream();
           is=com_port.openInputStream();
           inputStreamListener=new InputStreamListener(is,this,os);
       }
       catch(Exception e){
           this.textField.setString("Error init com:"+e.getMessage());
       }
    }//GEN-LINE:MVDInitEnd
    public synchronized void  set_textField(String s){
        this.textField.setString(s);
    }
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
            } else if (command == okCommand_info) {//GEN-LINE:MVDCACase3
                // Insert pre-action code here
                // Do nothing//GEN-LINE:MVDCAAction7
                // Insert post-action code here
                try{
                    String ports=System.getProperty("microedition.commports");
                    this.textField.setString(ports);
                }
                catch(Exception e){
                    this.textField.setString("Error:"+e.getMessage());
                }
            } else if (command == okCommand_send) {//GEN-LINE:MVDCACase7
                // Insert pre-action code here
                new OutputStreamListener(os,"Com Activate\n",this.textField);
                // Do nothing//GEN-LINE:MVDCAAction9
                // Insert post-action code here
            } else if (command == okCommand_clear) {//GEN-LINE:MVDCACase9
                // Insert pre-action code here
                this.textField.setString("");
                // Do nothing//GEN-LINE:MVDCAAction12
                // Insert post-action code here
            }//GEN-BEGIN:MVDCACase12
        }//GEN-END:MVDCACase12
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
            helloForm.addCommand(get_okCommand_info());
            helloForm.addCommand(get_okCommand_send());
            helloForm.addCommand(get_okCommand_clear());
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

    /** This method returns instance for okCommand_info component and should be called instead of accessing okCommand_info field directly.//GEN-BEGIN:MVDGetBegin6
     * @return Instance for okCommand_info component
     */
    public Command get_okCommand_info() {
        if (okCommand_info == null) {//GEN-END:MVDGetBegin6
            // Insert pre-init code here
            okCommand_info = new Command("Get info", Command.OK, 2);//GEN-LINE:MVDGetInit6
            // Insert post-init code here
        }//GEN-BEGIN:MVDGetEnd6
        return okCommand_info;
    }//GEN-END:MVDGetEnd6

    /** This method returns instance for okCommand_send component and should be called instead of accessing okCommand_send field directly.//GEN-BEGIN:MVDGetBegin8
     * @return Instance for okCommand_send component
     */
    public Command get_okCommand_send() {
        if (okCommand_send == null) {//GEN-END:MVDGetBegin8
            // Insert pre-init code here
            okCommand_send = new Command("Send to COM1", Command.OK, 1);//GEN-LINE:MVDGetInit8
            // Insert post-init code here
        }//GEN-BEGIN:MVDGetEnd8
        return okCommand_send;
    }//GEN-END:MVDGetEnd8

    /** This method returns instance for textField component and should be called instead of accessing textField field directly.//GEN-BEGIN:MVDGetBegin10
     * @return Instance for textField component
     */
    public TextField get_textField() {
        if (textField == null) {//GEN-END:MVDGetBegin10
            // Insert pre-init code here
            textField = new TextField("COM-Port", null, 120, TextField.ANY);//GEN-LINE:MVDGetInit10
            // Insert post-init code here
        }//GEN-BEGIN:MVDGetEnd10
        return textField;
    }//GEN-END:MVDGetEnd10

    /** This method returns instance for okCommand_clear component and should be called instead of accessing okCommand_clear field directly.//GEN-BEGIN:MVDGetBegin11
     * @return Instance for okCommand_clear component
     */
    public Command get_okCommand_clear() {
        if (okCommand_clear == null) {//GEN-END:MVDGetBegin11
            // Insert pre-init code here
            okCommand_clear = new Command("Clear", Command.OK, 3);//GEN-LINE:MVDGetInit11
            // Insert post-init code here
        }//GEN-BEGIN:MVDGetEnd11
        return okCommand_clear;
    }//GEN-END:MVDGetEnd11

    /** This method returns instance for okCommand_mirror component and should be called instead of accessing okCommand_mirror field directly.//GEN-BEGIN:MVDGetBegin13
     * @return Instance for okCommand_mirror component
     */
    public Command get_okCommand_mirror() {
        if (okCommand_mirror == null) {//GEN-END:MVDGetBegin13
            // Insert pre-init code here
            okCommand_mirror = new Command("Mirror", Command.OK, 4);//GEN-LINE:MVDGetInit13
            // Insert post-init code here
        }//GEN-BEGIN:MVDGetEnd13
        return okCommand_mirror;
    }//GEN-END:MVDGetEnd13
    
    public void startApp() {
        initialize();
    }
    
    public void pauseApp() {
    }
    
    public void destroyApp(boolean unconditional) {
        try{
            this.is.close();
            this.os.close();
            this.com_port.close();
        }
        catch(Exception e){
            System.out.println("Error on COM:"+e.getMessage());
        }
    }
    
}
