package temp_package;

import java.util.*;
import java.io.*;
import java.net.*;
class net{
	public void sample_read_socket_to_console(String address,String path){
		try{
			Socket s=new Socket(address,80);
			InputStream is=s.getInputStream();
			OutputStream os=s.getOutputStream();
			String out_string;
			if(path.length()==0){
				out_string="GET "+"\n\n";
			}
			else {
				out_string="GET /"+path+"\n\n";
			}
			os.write(out_string.getBytes());
			int read_bytes=0;
			byte[] buffer=new byte[512];
			while((read_bytes=is.read(buffer))!=-1){
				String in_string=new String(buffer,0,read_bytes);
				System.out.println(in_string);
			}
			os.close();
			is.close();
		}
		catch(Exception e){
			System.out.println("Error in open socket\n"+e.getMessage());
		}
	}
}

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
class mail{
	private void write_to_stream(OutputStream os,String command) throws IOException{
		byte[] buffer_output=new byte[255];
		buffer_output=command.getBytes();
		//System.out.println("to os:"+command);
		os.write(buffer_output);
		os.flush();
		buffer_output=null;
	}
	private String read_from_stream(InputStream is) throws IOException{
		byte[] buffer_input=new byte[255];
		String result="";
		String temp_result="";
		int read_count=0;
		read_count=is.read(buffer_input);
		/*
		  byte read_byte=0; 
		  while((read_byte!=13)&&(read_count<255)){
			read_byte=(byte)is.read();
			buffer_input[++read_count]=read_byte;
			System.out.print((char)read_byte);
		}*/
		temp_result=new String(buffer_input,0,read_count);
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
			new Exception(s);
		}
	}
	private void check_pop3_result(String s) throws Exception{
		s=s.trim();
		if(s.startsWith("+OK")){
			// result is OK
		}
		else {
			new Exception(s);
		}
	}
	private int pop3_get_count_messages(String s){
		String temp_string;
		//+OK 1 messages (1024 octets)
		//+OK technik7_job@mail.ru maildrop has 1 messages (1024 octets)
		int messages_begin=s.lastIndexOf(" messages");
		temp_string=s.substring(0,messages_begin);
		messages_begin=temp_string.lastIndexOf(" ");
		temp_string=temp_string.substring(messages_begin);
		return (new Integer(temp_string.trim())).intValue();
	}
	private String read_from_stream_to_dot(InputStream is) throws IOException{
		int read_before_int=0;
		int read_int=0;
		String result="";
		while_main:
		while(true){
			read_before_int=read_int;
			read_int=is.read();
			if(read_int==(-1)){
				break while_main;
			}
			if((read_int==(".".toCharArray())[0])&&(read_before_int==("\n".toCharArray())[0])){
				break while_main;
			}
			result+=(char)read_int;
		}
		return result;
	}
	private byte[] pop3_get_message_id(String s){
		//System.out.println(">>"+s);
		s=s.substring(s.indexOf("\n"));
		//System.out.println(">>"+s);
		s=s.substring(1);
		//System.out.println(">>"+s+"<<");
		StringTokenizer st=new StringTokenizer(s,"\n");
		byte[] result=new byte[st.countTokens()];
		//System.out.println("count:"+st.countTokens());
		int i=0;
		while(st.hasMoreTokens()){
			i++;
			String temp_string=st.nextToken();
			//System.out.println(i+":--"+temp_string+"--");
			result[i-1]=(byte)(new Integer(temp_string.substring(0,temp_string.indexOf(" ")).trim())).intValue();
		}
		return result;
	}
	public boolean send_mail(String address,int port,String Recipient,String text){
		boolean result=false;
		try{
			Socket s=new Socket(address,port);
			InetAddress my_address=InetAddress.getLocalHost();
			System.out.println("My IP:"+my_address.getHostAddress());
			InputStream is=s.getInputStream();
			OutputStream os=s.getOutputStream();
			String command;
			String answer;
			command="helo "+my_address.getHostAddress()+"\n";
			this.write_to_stream(os, command);
			answer=this.read_from_stream(is);
			check_smtp_result(answer);

			this.write_to_stream(os, "mail from:<technik7_job@mail.ru>\n");
			answer=this.read_from_stream(is);
			check_smtp_result(answer);

			this.write_to_stream(os, "rcpt to:<"+Recipient+">\n");
			answer=this.read_from_stream(is);
			check_smtp_result(answer);

			this.write_to_stream(os,"data\n");
			answer=this.read_from_stream(is);
			check_smtp_result(answer);

			this.write_to_stream(os,text+"\n");
			answer=this.read_from_stream(is);
			check_smtp_result(answer);

			this.write_to_stream(os,".\n");
			answer=this.read_from_stream(is);
			check_smtp_result(answer);

			this.write_to_stream(os,"rset\n");
			answer=this.read_from_stream(is);
			check_smtp_result(answer);

			this.write_to_stream(os,"quit\n");
			answer=this.read_from_stream(is);
			check_smtp_result(answer);
			is.close();
			os.close();
			result=true;
		}
		catch(Exception e){
			result=false;
			System.out.println("Error \n"+e.getMessage());
		}
		return result;
	}
	public messages[] receive_mail(String address,int port,String user_name,String password,boolean delete_flag){
		messages[] list_of_message=null;
		try{
			Socket s=new Socket(address,port);
			InputStream is=s.getInputStream();
			OutputStream os=s.getOutputStream();
			String answer=this.read_from_stream(is);
			check_pop3_result(answer);

			this.write_to_stream(os,"user "+user_name+"\n");
			answer=this.read_from_stream(is);
			check_pop3_result(answer);

			this.write_to_stream(os, "pass "+password+"\n");
			answer=this.read_from_stream(is);
			check_pop3_result(answer);

			if(this.pop3_get_count_messages(answer)>0){
				// read messages;
				this.write_to_stream(os, "list\n");
				answer=this.read_from_stream_to_dot(is);
				byte[] message_id=this.pop3_get_message_id(answer);
				list_of_message=new messages[message_id.length];
				for(int i=0;i<message_id.length;i++){
					//System.out.println("message:"+message_id[i]);
					this.write_to_stream(os, "retr "+message_id[i]+"\n");
					answer=this.read_from_stream_to_dot(is);
					this.check_pop3_result(answer);
					//System.out.println(answer);
					list_of_message[i]=new messages(answer);
					//System.out.println("Created message:"+i+":"+list_of_message[i].getText());
					if(delete_flag){
						this.write_to_stream(os, "dele "+message_id[i]+"\n");
						answer=this.read_from_stream(is);
						this.check_pop3_result(answer);
					}
				}
			}
			this.write_to_stream(os, "quit"+"\n");
			is.close();
			os.close();
		}
		catch(Exception e){
			System.out.println("Error \n"+e.getMessage());
		}
		return list_of_message;
	}
}
public class temp_class {
	public static void main(String args[]){
		//(new net()).sample_read_socket_to_console("ya.ru","");
		System.out.println("Begin work");
		mail temp=new mail();
		/*if(temp.send_mail("smtp.mail.ru", 25, "technik7_job@mail.ru", "hello from JAVA\nhello from Eclipse")){
			System.out.println("Mail sended");
		}*/
		messages[] list_of_message=temp.receive_mail("pop3.mail.ru", 110, "technik7_job", "sokol7",true);			
		if(list_of_message!=null){
			System.out.println("Recieved and read "+list_of_message.length+" messages");
		}
		else{
			System.out.println("No message");
		}
		System.out.println("End work");
	}
}
