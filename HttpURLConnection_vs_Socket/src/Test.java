

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;

/**
 * Servlet implementation class Test
 */
public class Test extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
/*	public void service(ServletRequest request, ServletResponse response) throws Exception {
		
		
	}
	*/
	
	@Override
	public void service(ServletRequest request, ServletResponse response)
			throws ServletException, IOException {
		try{
			System.out.println("Addr: "+request.getRemoteAddr()+"   Port:"+request.getRemotePort());
			InputStream is=request.getInputStream();
			ByteArrayOutputStream baos=new ByteArrayOutputStream();
			byte[] buffer=new byte[1024];
			int readedByte=0;
			while((readedByte=is.read(buffer))>=0){
				baos.write(buffer,0,readedByte);
			}
			byte[] readedBytes=baos.toByteArray();
			printBytes(readedBytes);
			InputStream input=new ByteArrayInputStream(readedBytes);
			OutputStream os=response.getOutputStream();
			while((readedByte=input.read(buffer))>=0){
				os.write(buffer,0,readedByte);
			}
			os.flush();
			is.close();
			os.close();
		}catch(Exception ex){
			System.out.println("Exception ex:"+ex.getMessage());
		}
	}

	private void printBytes(byte[] array){
		for(int counter=0;counter<array.length;counter++){
			System.out.print(array[counter]);
			System.out.print("");
			System.out.println();
		}
	}
	
	
	public static void main(String[] args){
		//httpConnection();
		//socket();
/*		try{
			ServerSocket server=new ServerSocket(1080);
			while(true){
				Socket socket=server.accept();
				System.out.println("Connected: "+socket.getInetAddress().getHostAddress());
				InputStream is=socket.getInputStream();
				int value=0;
				while((value=is.read())>=0){
					System.out.println("ReadedValue: "+value);
				}
				OutputStream os=socket.getOutputStream();
				for(int counter=10;counter>=0;counter--){
					os.write(counter);
				}
				os.flush();
				os.close();
			}
			
		}catch(Exception ex){
			System.out.println("Exception ex:"+ex.getMessage());
		}
*/		
	}
	
	
}
