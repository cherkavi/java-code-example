import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;


public class TempNioReadFile {
	public static void main(String[] args){
		readFromFile("c:\\temp.xml");
	}
	
	private static boolean readFromFile(String pathToFile){
		boolean returnValue=false;
		try{
			FileInputStream fis=new FileInputStream(pathToFile);
			FileChannel channel=fis.getChannel();
			ByteBuffer buffer=ByteBuffer.allocate(50);
			CharsetEncoder encoder=Charset.defaultCharset().newEncoder();
			CharsetDecoder decoder=Charset.defaultCharset().newDecoder();
			while(channel.read(buffer)>0){
				buffer.flip();
				CharBuffer charBuffer=decoder.decode(buffer);
				System.out.println(charBuffer.toString());
				buffer.clear();
			}
			returnValue=true;
		}catch(Exception ex){
			System.out.println("readFromFile: "+ex.getMessage());
			returnValue=false;
		}
		return returnValue;
	}
}
