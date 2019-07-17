import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.Set;
import java.util.SortedMap;


/** this is New Input Output Example */
public class EnterPoint {
	
	/** get all avaiable charsets */
	@SuppressWarnings("unused")
	private static void printAviableCharset(){
		// get all charset's
		SortedMap<String, Charset> charset=Charset.availableCharsets();
		Set<String> set=charset.keySet();
		// print all charset's
		for(String key:set){
			System.out.println("Key:"+key+"     Value:"+charset.get(key));
		}
	}
	
	/** write data into file 
	 * @param path to file
	 * @param value for write
	 * */
	private static boolean writeToFile(String path, 
								    String value){
		try{
			// get output stream 
			FileOutputStream fos=new FileOutputStream(path);
			// get input stream
			FileChannel channelOut=fos.getChannel();
			// get charset
			Charset charset=Charset.forName("UTF-8");
			// get encoder 
			CharsetEncoder encoder=charset.newEncoder();

			// create CharBuffer
			//CharBuffer charBuffer=CharBuffer.wrap(value);
			
			// create CharBuffer 
			CharBuffer charBuffer=CharBuffer.allocate(100);
				// put CharBuffer
			charBuffer.put(value);
				// IMPORTANT: buffer position to zero 
			charBuffer.flip();
			System.out.println(charBuffer.toString());
			
			// get ByteBuffer from CharBuffer enjoy Encoder
			ByteBuffer buffer=encoder.encode(charBuffer);
			// write to Channel out ByteBuffer
			channelOut.write(buffer);
			// close Channel - not necessarily
			//channelOut.close();
			// close File - not necessarily
			//fos.close();
			return true;
		}catch(IOException ex){
			System.out.println("writeToFile Exception:"+ex.getMessage());
			return false;
		}
	}
	
	/** read data from file 
	 * @param path to file for read data
	 * @return null, if Exception 
	 * */
	private static String readFromFile(String path){
		String returnValue=null;
		try{
			// get FileInputStream
			FileInputStream fis=new FileInputStream(path);
			// get Channel
			FileChannel channel=fis.getChannel();
			
			// create ByteBuffer
			ByteBuffer byteBuffer=ByteBuffer.allocate(100);
			// create Charset
			Charset charset=Charset.forName("UTF-8");
			// create Decoder for Charset
			CharsetDecoder decoder=charset.newDecoder();
			
			// read from Channel to ByteBuffer
			channel.read(byteBuffer);
			// IMPORTANT position of buffer to zero
			byteBuffer.flip();
			// create CharBuffer
			CharBuffer charBuffer=decoder.decode(byteBuffer);
			// get String from CharBuffer
			returnValue=charBuffer.toString();
		}catch(Exception ex){
			System.out.println("readFromFile Exception:"+ex.getMessage());
			returnValue=null;
		}
		return returnValue;
	}
	
	
	/** copy data 
	 * @param pathSource path to Source file 
	 * @param pathDestination path to Destination file 
	 * */
	private static boolean copyData(String pathSource, 
									String pathDestination){
		try{
			// get FileInputStream
			FileInputStream fis=new FileInputStream(pathSource);
			// get FileOutputStream 
			FileOutputStream fos=new FileOutputStream(pathDestination);
			// get Channel Input
			FileChannel channelInput=fis.getChannel();
			// get Channel Output
			FileChannel channelOutput=fos.getChannel();
			
			// create ByteBuffer
			ByteBuffer buffer=ByteBuffer.allocate(100);
			// read from Channel to Buffer
			while(channelInput.read(buffer)>0){
				// IMPORTANT: set buffer position to 0
				buffer.flip();
				// write buffer to Channel Output
				channelOutput.write(buffer);
				// IMPORTANT: clear Buffer
				buffer.clear();
			}
			return true;
		}catch(Exception ex){
			return false;
		}
	}
	
	
	public static void main(String[] args){
		String stringForWrite="this is test string for show NIO package: это тест для отображения работы NIO пакета ";
		String pathSource="c:\\nio_example.txt";
		String pathDestination="c:\\nio_example_copy.txt";
		
		//printAviableCharset();
		
		if(writeToFile(pathSource,stringForWrite)){
			System.out.println("Write OK");
		}else{
			System.out.println("Write Error");
		}
		
		String readString=readFromFile(pathSource);
		System.out.println("Data from file:"+readString);
		
		if(copyData(pathSource, pathDestination)){
			System.out.println("Copy OK");
		}else{
			System.out.println("Copy Error");
		}
	}
}
