import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;


public class Shoot {
	public static void main(String[] args){
		//httpConnection();
		socket();
	}
	
	private static void socket(){
		try{
			Socket socket=new Socket("192.168.15.119",8080);
			//Socket socket=new Socket("google.com.ua",80);
			OutputStream out=socket.getOutputStream();
			out.write("POST /TestConnect/Test HTTP/1.1\r\n".getBytes());
			out.write("Cache-Control: no-cache\r\n".getBytes());
			out.write("Pragma: no-cache\r\n".getBytes());
			out.write("User-Agent: Java/1.6.0_10\r\n".getBytes());
			out.write("Host: 192.168.15.119:8080\r\n".getBytes());
			out.write("Accept: text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2\r\n".getBytes());
			out.write("Connection: keep-alive\r\n".getBytes());
			out.write("Content-type: application/x-www-form-urlencoded\r\n".getBytes());
			out.write("Content-Length: 5\r\n".getBytes());
			String sendString="12345";
			System.out.println("sendLength: "+sendString.getBytes().length);
			out.write(sendString.getBytes());
			out.write("\r\n".getBytes());
			out.flush();
			int readedValue=0;
			out.close();
			InputStream is=socket.getInputStream();
			BufferedReader reader=new BufferedReader(new InputStreamReader(is));
			String currentLine=null;
			
//			while((readedValue=is.read())>=0){
//				System.out.println("readedValue: "+readedValue);
//			}

			while( (currentLine=reader.readLine())!=null){
				System.out.println("Answer: "+currentLine);
			}
			socket.close();
		}catch(Exception ex){
			System.out.println("Socket Exception: "+ex.getMessage());
		}
		System.out.println("End: ");
	}
	
	private static void httpConnection(){
		try {
			URL url = new URL("http://192.168.15.119:8080/TestConnect/Test");
			// URL url=new URL("http://127.0.0.1:8080/TestConnect/Test");
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setUseCaches(false);
			connection.connect();
			OutputStream os = connection.getOutputStream();
			for (int counter = 0; counter < 10; counter++) {
				os.write(counter + 100);
			}
			os.close();
			int readedValue = 0;
			InputStream is = connection.getInputStream();
			while ((readedValue = is.read()) >= 0) {
				System.out.println("readedValue: " + readedValue);
			}

		} catch (Exception ex) {
			System.err.println("Exception: " + ex.getMessage());
		}
		
	}
	
}
