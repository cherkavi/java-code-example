package temp_package;

import java.net.*;
import java.io.*;
class Server implements Runnable{
	boolean flag_run=true;
	int port=0;
	Thread t=null;
	Server(int port){
		this.port=port;
		(new Thread(this)).start();
	}

	public void run() {
		boolean flag_wait=true;
		// внешний блок на случай когда клиент отключился и ожидаем другого клиента
		outer_block:while(flag_wait){
		try{
			// создаем новое соединение для клиента
			java.net.ServerSocket s=new java.net.ServerSocket(this.port);
			// получаем Socket для нового клиента
			Socket socket=s.accept();
			// получаем InputStream и OutputStream для Socket
			InputStream is=socket.getInputStream();
			OutputStream os=socket.getOutputStream();
			byte[] buffer=new byte[255];
			try{
				// пока работаем с одним клиентом, то есть от него не получен "close", возвращаем ему присланные строки
				while (this.flag_run==true){
					String result_string=new String("");
					int pos=0;
					while((buffer[pos++]=(byte)is.read())!='\n'){
					}
					result_string+=new String(buffer,0,pos);
					// проверка на приход строки "close" - команда на закрытие
					if(result_string.startsWith("close")){
						this.flag_run=false;
						System.out.println("Server Get command close");
						flag_wait=false;
						break outer_block;
					}
					// отправка строки обратно
					else {
						System.out.println("Server Get string:"+result_string);
						result_string=result_string.toUpperCase();
						buffer=result_string.getBytes();
						os.write(buffer, 0, result_string.length());
						os.flush();
						System.out.println("Server send:"+result_string);
					}
				}
			}
			catch(Exception e){
				// перехват Exception при отсоединении клиента
				if(!e.getMessage().trim().equals("6")){
					throw new Exception(e.getMessage());
				}
			}
			os.close();
			is.close();
			socket.close();
			s.close();
		}
		catch(Exception e){
			flag_run=false;
			System.out.println("ServerSocket Error \n"+e.getMessage());
		}
	}
	}
}
class Client{
	Client(int port,String text){
		try{
			Socket s=new Socket(InetAddress.getLocalHost(),port);
			InputStream is=s.getInputStream();
			OutputStream os=s.getOutputStream();
			text+="\n";
			byte[] buffer=text.getBytes();
			os.write(buffer);
			os.flush();
			System.out.println("Client write to socket"+text);
			String result_string="";
			int pos=0;
			while((buffer[pos++]=(byte)is.read())!='\n'){
			}
			result_string+=new String(buffer,0,pos);
			System.out.println("Client read from Socket:"+result_string);
			os.close();
			is.close();
			s.close();
		}
		catch(Exception e){
			System.out.println("Error in client\n"+e.getMessage());
		}
	}
}
public class temp_class {
	public static void main(String args[]){
		// java temp_package/temp_class server 2000
		// java temp_package/temp_class client 2000 close
		if(args.length==3){
			if(args[0].equalsIgnoreCase("client")){
				int port=Integer.parseInt(args[1]);
				System.out.println("client run");
				new Client(port,args[2]);
			}
		}
		if(args.length==2){
			if(args[0].equalsIgnoreCase("server")){
				int port=Integer.parseInt(args[1]);
				System.out.println("server run");
				new Server(port);
			}
		}
	}
	
}
