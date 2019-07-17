import it.sauronsoftware.ftp4j.FTPClient;
import it.sauronsoftware.ftp4j.FTPFile;


import java.io.File;
import java.io.IOException;

import org.jibble.simpleftp.SimpleFTP;


public class StoreToFtp {
	public static void main(String[] args){
		System.out.println("begin");
		// System.out.println("Upload: "+uploadToFtp("D:\\headphones.txt ", "91.211.119.222", 21, "ftp-tomcat", "llooggSSTOM"));
		/*System.out.println("Upload: "+uploadToFtp4J("D:\\headphones.txt", 
													"91.211.119.222", 
													21, 
													"ftp-tomcat", 
													"llooggSSTOM"));*/
		printDirectory("192.168.0.2",21, "technik", "technik","shops");
		System.out.println("-end-");
	}
	
	/*
URL url = 
    new URL("ftp://username:password@ftp.whatever.com/file.zip;type=i");
URLConnection con = url.openConnection();
BufferedInputStream in = 
    new BufferedInputStream(con.getInputStream());
FileOutputStream out = 
    new FileOutputStream("C:\\file.zip");

int i = 0;
byte[] bytesIn = new byte[1024];
while ((i = in.read(bytesIn)) >= 0) {
	out.write(bytesIn, 0, i);
}
out.close();
in.close();	 */
	
	private static void printDirectory(String ftpServer, int port, String userName, String password, String directoryOnServer){
		try{
			FTPClient client=new FTPClient();
			client.connect(ftpServer, port);
			client.login(userName, password);
			client.changeDirectory(directoryOnServer);
			FTPFile[] fileList=client.list();
			for(int counter=0;counter<fileList.length;counter++){
				System.out.println(counter+" : "+fileList[counter].getName());
			}
			client.disconnect(true);
		}catch(Exception ex){
			System.err.println("printDirectory Exception:"+ex.getMessage());
		}
	}
	
	private static boolean uploadToFtp4J(String pathToFile, String ftpServer, int port, String userName, String password){
		try{
			FTPClient client = new FTPClient();
			client.connect(ftpServer, port);
			client.login(userName, password);
			client.upload(new File(pathToFile));
			client.disconnect(true);
			return true;
		}catch(Exception ex){
			System.err.println("Ftp4J Exception");
			return false;
		}
	}
	
	private static boolean uploadToFtp(String pathToFile, String ftpServer, int port, String userName, String password){
		boolean returnValue=false;
		try {
		    SimpleFTP ftp = new SimpleFTP();
		    
		    // Connect to an FTP server on port 21.
		    ftp.connect(ftpServer, port, userName, password);
		    
		    // Set binary mode.
		    ftp.bin();
		    // Change to a new working directory on the FTP server.
		    // ftp.cwd("web");
		    
		    // Upload some files.
		    returnValue=ftp.stor(new File(pathToFile));
		    
		    // You can also upload from an InputStream, e.g.
		    // ftp.stor(new FileInputStream(new File("test.png")), "test.png");
		    // ftp.stor(someSocket.getInputStream(), "blah.dat");
		    
		    // Quit from the FTP server.
		    ftp.disconnect();
		    return returnValue;
		}
		catch (IOException e) {
		    // Jibble.
			return false;
		}		
	}
}
