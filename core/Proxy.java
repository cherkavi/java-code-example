import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;

public class Proxy {
	
	
	public static void main(String[] args){
		// System.out.println(parseIp(getMyIp("200.75.51.148", 8080)));
		// System.out.println(parseIp(getMyIp("177.103.242.72", 80)));
		// String rawData=getMyIp("218.4.236.117", 80);
		String rawData=getMyIp("127.0.0.1", 9050); // tor service should be started
		System.out.println(rawData);
	}

	
	private final static String URL_HOST="http://api.ipify.org/?format=json";
	
	private static String getMyIp(String proxyHost, Integer proxyPort) {
		URL server = null;
		try {
			server = new URL(URL_HOST);
		}
		catch (MalformedURLException e) {
			e.printStackTrace();
		}
//		System.setProperty("http.proxyHost", proxyHost);
//		System.setProperty("http.proxyPort", Integer.toString(proxyPort));
		HttpURLConnection connection = null;
		InputStream in = null;
		try {
			Proxy proxy=getProxySocks(proxyHost, proxyPort);
			URLConnection urlConnection = server.openConnection(proxy);
			connection = (HttpURLConnection) urlConnection;
			connection.connect();
			in = connection.getInputStream();
			return readInputStream(in);
		}catch (IOException e) {
			System.err.println("error: "+e.getMessage());
			return null;
		}finally{
			if(in!=null){
				try{
					in.close();
				}catch(IOException ex){
				}
			}
			if(connection!=null)
			connection.disconnect();
		}
	}

	private static Proxy getProxySocks(String hostAddr, Integer port) {
		return new Proxy(Proxy.Type.SOCKS, new InetSocketAddress(hostAddr, port));
	}

	private static String readInputStream(InputStream in) throws IOException {
		StringBuilder returnValue=new StringBuilder();
		try(BufferedReader reader=new BufferedReader(new InputStreamReader(in))){
			String nextLine = null;
			while( (nextLine=reader.readLine())!=null){
				returnValue.append(nextLine);
			}
		};
		return returnValue.toString();
	}
	
	
}
