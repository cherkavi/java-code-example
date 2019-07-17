import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import org.savarese.rocksaw.net.RawSocket;



public class EnterPoint {
	public static void main(String[] args){
		System.out.println("begin");
		RawSocket socket=new RawSocket();
		RawSocket.getProtocolByName("ip");
		try{
			socket.bind(InetAddress.getByAddress(new byte[]{(byte)212,23,43,21}));
		}catch(Exception ex){
			System.err.println("Bind Error:"+ex.getMessage());
		}
		try{
			String httpPath="http://google.com.ua";
			HttpURLConnection connection=(HttpURLConnection)(new URL(httpPath)).openConnection();
			BufferedReader reader=new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String currentLine;
			while((currentLine=reader.readLine())!=null){
				System.out.println(currentLine);
			}
			reader.close();
			connection.disconnect();
		}catch(Exception ex){
			System.err.println("connection:");
		}
		System.out.println("end");
	}

}
