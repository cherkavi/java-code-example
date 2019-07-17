/*
 * HelloMidlet.java
 *
 * Created on 5 Èþëü 2007 ã., 14:16
 */

package hello;

import javax.microedition.io.Connector;
import javax.microedition.io.SocketConnection;
//import javax.microedition.io.StreamConnection;
import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import java.util.Enumeration;
import java.util.Vector;
/*import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;*/
import java.io.*;
/**
 *
 * @author root
 */
/*
 * class replaced StringTokenizer in java.util.* (J2SE)
 */
class StringTokenizer implements Enumeration{
    private Vector v=new Vector();
    private int counter=0;
    StringTokenizer(String text,String delimeter){
        int index=0;
        while((index=(text.indexOf(delimeter)))>0){
            v.addElement(text.substring(0,index));
            text=text.substring(index+delimeter.length());
        }
        if(text.trim().length()!=0){
        	v.addElement(text);
        }
    }

    public boolean hasMoreElements() {
        return (counter<v.size());
    }

    public Object nextElement() {
        return v.elementAt(counter++);
    }
    public int countTokens(){
        return v.size();
    }

    public boolean hasMoreTokens() {
        return (counter<v.size());
    }

    public String nextToken() {
        return (String)v.elementAt(counter++);
    }
}
/*
 * Package for input message
 */
class messages{
	String Return_path="";
	String Received="";
	String Message_Id="";
	String From="";
	String Bcc="";
	String Date="";
	String Text="";
	messages(String s){
		try{
			this.Return_path=s.substring(0,s.indexOf("Received:"));
			this.Received=s.substring(s.indexOf("Received:"),s.indexOf("Message-Id:"));
			this.Message_Id=s.substring(s.indexOf("Message-Id:"),s.indexOf("From:"));
			this.From=s.substring(s.indexOf("From:"),s.indexOf("Bcc:"));
			this.Bcc=s.substring(s.indexOf("Bcc:"),s.indexOf("Date:"));
			this.Date=s.substring(s.indexOf("Date:"));
			this.Text=this.Date.substring(this.Date.indexOf("\n"),this.Date.length());
			this.Date=this.Date.substring(0,this.Date.indexOf("\n"));
		}
		catch(Exception e){
			System.out.println("constructor messages Error "+e.getMessage());
		}
	}
	public String getText(){
		return this.Text;
	}
	public String getFrom(){
		return this.From;
	}
	public String getDate(){
		return this.getDate();
	}
	public String toString(){
		return this.Return_path+"\n"+this.Received+"\n"+this.Message_Id+"\n"
		+this.From+"\n"+this.Bcc+"\n"+this.Date+"\n";
	}
}
/*
 *package for boolean value
 */
class boolean_package{
	private boolean value;
	boolean_package(boolean value){
		this.value=value;
	}
	boolean getValue(){
		return this.value;
	}
	void setValue(boolean value){
		this.value=value;
	}
}
class string_package{
    private String value;
    string_package(String s){
        this.value=s;
    }
    String getValue(){
        return this.value;
    }
    void setValue(String value){
        this.value=value;
    }
}

class send_mail implements Runnable{
	private Thread t;
	private SocketConnection s;
        //private StreamConnection s;
	private OutputStream os;
	private InputStream is;
	private boolean_package result_send=new boolean_package(false);
	private String From;
	private String Recipient;
	private String text;
        private String address;
        private int port;
        private String history;
	private synchronized void write_to_stream(OutputStream os,String command) throws IOException{
		byte[] buffer_output=new byte[255];
		buffer_output=command.getBytes();
		//System.out.println("to os:"+command);
		os.write(buffer_output);
		os.flush();
		buffer_output=null;
	}
	private synchronized String read_from_stream(InputStream is) throws IOException{
		byte[] buffer_input=new byte[255];
		String result="";
		String temp_result="";
		int read_count=0;
		//read_count=is.read(buffer_input);
		
		byte read_byte=0; 
		while((read_byte!=13)&&(read_count<255)){
                    read_byte=(byte)is.read();
                    buffer_input[++read_count]=read_byte;
                    //System.out.print((char)read_byte);
                }
		temp_result=new String(buffer_input,0,read_count);
                System.out.println(temp_result);
		result+=temp_result;
		return result;
	}
	private void check_smtp_result(String s) throws Exception{
		boolean result=false;
		s=s.trim();
		if(s.startsWith("2")){
			result=true;
		}
		if(s.startsWith("3")){
			result=true;
		}
		if(s.startsWith("4")){
			result=true;
		}
		if(result==false){
			throw new Exception(s);
		};
	}

	send_mail(String address,int port,String From,String Recipient,String text,boolean_package result,string_package trace){	
		try{
			this.From=From;
			this.Recipient=Recipient;
			this.text=text;
                        this.address=address;
                        this.port=port;

			//System.out.println("begin create Thread");
			this.t=new Thread(this);
			//System.out.println("Run Thread");
			this.t.start();
			//System.out.println("wait Thread");
			this.t.join();
			//System.out.println("get result Thread");
			result.setValue(this.result_send.getValue());
                        trace.setValue(this.history);
		}
		catch(Exception e){
			System.out.println("Error in init <send mail>"+e.getMessage());
			result.setValue(false);
		}
	}
	public void run(){
		String command;
		String answer;
                String history;
		try{
			System.out.println("begin socket init");
                        this.s=(SocketConnection)Connector.open("socket://"+address+":"+port,Connector.READ_WRITE);
                        this.s.setSocketOption(SocketConnection.LINGER, 5);
                        //this.s=(StreamConnection)Connector.open("socket://"+address+":"+port,Connector.READ_WRITE);

                        System.out.println("begin get InputStream");
                        this.is=s.openInputStream();
			System.out.println("begin get OutputStream");
			this.os=s.openOutputStream();
                        history="";

                        command="EHLO 127.0.0.1\r\n";
			System.out.println(command);
                        this.write_to_stream(os, command);
			answer=this.read_from_stream(is);
			check_smtp_result(answer);
			//System.out.println(answer);
                        history=history+command+"\n"+answer+"\n";

                        command="AUTH LOGIN\r\n";
			System.out.println(command);
                        this.write_to_stream(os, command);
			answer=this.read_from_stream(is);
			check_smtp_result(answer);
			//System.out.println(answer);
                        history=history+command+"\n"+answer+"\n";

                        command="dGVjaG5pazdfam9i\r\n";
			System.out.println(command);
                        this.write_to_stream(os, command);
			answer=this.read_from_stream(is);
			check_smtp_result(answer);
			//System.out.println(answer);
                        history=history+command+"\n"+answer+"\n";

                        command="c29rb2w3\r\n";
			System.out.println(command);
                        this.write_to_stream(os, command);
			answer=this.read_from_stream(is);
			check_smtp_result(answer);
			//System.out.println(answer);
                        history=history+command+"\n"+answer+"\n";
        
			command="mail from:<"+From+">\n";
			System.out.println(command);
                        this.write_to_stream(os, command);
			answer=this.read_from_stream(is);
			check_smtp_result(answer);
			//System.out.println(answer);
                        history=history+command+"\n"+answer+"\n";

			command="rcpt to:<"+Recipient+">\n";
			System.out.println(command);
                        this.write_to_stream(os, command);
			answer=this.read_from_stream(is);
			check_smtp_result(answer);
			//System.out.println(answer);
                        history=history+command+"\n"+answer+"\n";

			command="data\n";
			System.out.println(command);
                        this.write_to_stream(os,command);
			answer=this.read_from_stream(is);
			check_smtp_result(answer);
			//System.out.println(answer);
                        history=history+command+"\n"+answer+"\n";

			command=text+"\n";
                        this.write_to_stream(os,command);
			System.out.println(command);
                        answer=this.read_from_stream(is);
			check_smtp_result(answer);
			//System.out.println(answer);
                        history=history+command+"\n"+answer+"\n";

			command=".\n";
			System.out.println(command);
                        this.write_to_stream(os,command);
			answer=this.read_from_stream(is);
			check_smtp_result(answer);
			//System.out.println(answer);
                        history=history+command+"\n"+answer+"\n";

			command="rset\n";
			System.out.println(command);
                        this.write_to_stream(os,command);
			answer=this.read_from_stream(is);
			check_smtp_result(answer);
			//System.out.println(answer);
                        history=history+command+"\n"+answer+"\n";

			command="quit\n";
			System.out.println(command);
                        this.write_to_stream(os,command);
			answer=this.read_from_stream(is);
			check_smtp_result(answer);
                        history=history+command+"\n"+answer+"\n";

                        //System.out.println(answer);
                        os.flush();
			is.close();
			os.close();
                        s.close();
			//System.out.println("mail sended");
			this.result_send.setValue(true);		
                        this.history=history;
		}
		catch(Exception e){
			System.out.println("Error send mail"+e.getMessage());
			try{
                            this.result_send.setValue(false);
                            if(is!=null){
                                is.close();
                            }
                            if(os!=null){
                                os.close();
                            }
                            if(s!=null){
                                s.close();
                            }
                        }
                        catch(Exception e_inner){
                            System.out.println("Error in closing stream");
                        }
		}
	}
}

public class HelloMidlet extends MIDlet implements CommandListener {
    
    /** Creates a new instance of HelloMidlet */
    public HelloMidlet() {
    }
    
    private Form helloForm;                     
    private Command exitCommand;
    private TextField textField_smtp;
    private TextField textField_port;
    private TextField textField_sender;
    private TextField textField_destination;
    private TextField textField_text;
    private Command okCommand_send_mail;
    private TextField textField_result;                   
    private String address;
    private int port;
    //private SocketConnection s;
                     

    /** This method initializes UI of the application.                        
     */
    private void initialize() {                      
        // Insert pre-init code here
        getDisplay().setCurrent(get_helloForm());                      
        // Insert post-init code here
    }                     
    
    /** Called by the system to indicate that a command has been invoked on a particular displayable.                      
     * @param command the Command that ws invoked
     * @param displayable the Displayable on which the command was invoked
     */
    public void commandAction(Command command, Displayable displayable) {                    
        // Insert global pre-action code here
        if (displayable == helloForm) {                     
            if (command == exitCommand) {                   
                // Insert pre-action code here
                exitMIDlet();                       
                // Insert post-action code here
            } else if (command == okCommand_send_mail) {                     
                // Insert pre-action code here
		boolean_package result_send_mail=new boolean_package(false);
                this.textField_result.setString("");
                string_package trace=new string_package("");
                new send_mail(this.textField_smtp.getString(), Integer.valueOf(this.textField_port.getString()).intValue(), this.textField_sender.getString() ,this.textField_destination.getString(), this.textField_text.getString(),result_send_mail,trace);
		if(result_send_mail.getValue()){
                    System.out.println("mail sended:\n"+trace.getValue());
                    this.textField_result.setString("sended");
                }
		else{
                    System.out.println("error sended mail");
                    this.textField_result.setString("error send");
		}
                // Do nothing                        
                // Insert post-action code here
            }                       
        }                     
        // Insert global post-action code here
}                   
    
    /**
     * This method should return an instance of the display.
     */
    public Display getDisplay() {                         
        return Display.getDisplay(this);
    }                        
    
    /**
     * This method should exit the midlet.
     */
    public void exitMIDlet() {                         
        getDisplay().setCurrent(null);
        destroyApp(true);
        notifyDestroyed();
    }                        
    
    /** This method returns instance for helloForm component and should be called instead of accessing helloForm field directly.                        
     * @return Instance for helloForm component
     */
    public Form get_helloForm() {
        if (helloForm == null) {                      
            // Insert pre-init code here
            helloForm = new Form(null, new Item[] {                       
                get_textField_smtp(),
                get_textField_port(),
                get_textField_sender(),
                get_textField_destination(),
                get_textField_text(),
                get_textField_result()
            });
            helloForm.addCommand(get_exitCommand());
            helloForm.addCommand(get_okCommand_send_mail());
            helloForm.setCommandListener(this);                     
            // Insert post-init code here
        }                      
        return helloForm;
    }                    
    
    
    /** This method returns instance for exitCommand component and should be called instead of accessing exitCommand field directly.                        
     * @return Instance for exitCommand component
     */
    public Command get_exitCommand() {
        if (exitCommand == null) {                      
            // Insert pre-init code here
            exitCommand = new Command("Exit", Command.EXIT, 1);                      
            // Insert post-init code here
        }                      
        return exitCommand;
    }                    

    /** This method returns instance for textField_smtp component and should be called instead of accessing textField_smtp field directly.                        
     * @return Instance for textField_smtp component
     */
    public TextField get_textField_smtp() {
        if (textField_smtp == null) {                      
            // Insert pre-init code here
            textField_smtp = new TextField("smtp server", "smtp.mail.ru", 120, TextField.ANY);                      
            // Insert post-init code here
        }                      
        return textField_smtp;
    }                    

    /** This method returns instance for textField_port component and should be called instead of accessing textField_port field directly.                        
     * @return Instance for textField_port component
     */
    public TextField get_textField_port() {
        if (textField_port == null) {                      
            // Insert pre-init code here
            textField_port = new TextField("\u041F\u043E\u0440\u0442", "25", 120, TextField.ANY);                      
            // Insert post-init code here
        }                      
        return textField_port;
    }                    

    /** This method returns instance for textField_sender component and should be called instead of accessing textField_sender field directly.                        
     * @return Instance for textField_sender component
     */
    public TextField get_textField_sender() {
        if (textField_sender == null) {                      
            // Insert pre-init code here
            textField_sender = new TextField("Sender address", "technik7_job@mail.ru", 120, TextField.ANY);                      
            // Insert post-init code here
        }                      
        return textField_sender;
    }                    

    /** This method returns instance for textField_destination component and should be called instead of accessing textField_destination field directly.                        
     * @return Instance for textField_destination component
     */
    public TextField get_textField_destination() {
        if (textField_destination == null) {                      
            // Insert pre-init code here
            textField_destination = new TextField("Destination address", "technik7@rambler.ru", 120, TextField.ANY);                      
            // Insert post-init code here
        }                      
        return textField_destination;
    }                    

    /** This method returns instance for textField_text component and should be called instead of accessing textField_text field directly.                         
     * @return Instance for textField_text component
     */
    public TextField get_textField_text() {
        if (textField_text == null) {                       
            // Insert pre-init code here
            textField_text = new TextField("Text for send", "Hello from Siemens", 120, TextField.ANY);                       
            // Insert post-init code here
        }                       
        return textField_text;
    }                     

    /** This method returns instance for okCommand_send_mail component and should be called instead of accessing okCommand_send_mail field directly.                         
     * @return Instance for okCommand_send_mail component
     */
    public Command get_okCommand_send_mail() {
        if (okCommand_send_mail == null) {                       
            // Insert pre-init code here
            okCommand_send_mail = new Command("Send mail", Command.OK, 1);                       
            // Insert post-init code here
        }                       
        return okCommand_send_mail;
    }                     

    /** This method returns instance for textField_result component and should be called instead of accessing textField_result field directly.                         
     * @return Instance for textField_result component
     */
    public TextField get_textField_result() {
        if (textField_result == null) {                       
            // Insert pre-init code here
            textField_result = new TextField("Result field:", null, 120, TextField.ANY);                       
            // Insert post-init code here
        }                       
        return textField_result;
    }                     
    
    public void startApp() {
        initialize();
    }
    
    public void pauseApp() {
    }
    
    public void destroyApp(boolean unconditional) {
    }
    
}
