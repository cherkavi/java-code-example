package datastream;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class DataStreamTest {

	public static void main(String[] args){
		System.out.println("begin");
		String fileName="c:\\temp.out";
		writeSomeData(fileName);
		readSomeData(fileName);
		System.out.println("-end-");
	}
	
	/** write byte, int, long to the stream */
	private static boolean writeSomeData(String fileName){
		DataOutputStream file=null;
		try{
			file=new DataOutputStream(new FileOutputStream(fileName));
			System.out.println("Write Byte:1");
			file.writeByte(1);
			System.out.println("Write Int:2");
			file.writeInt(2);
			System.out.println("Write Long:3");
			file.writeLong(3);
			return true;
		}catch(Exception ex){
			return false;
		}finally{
			try{
				file.close();
			}catch(Exception ex){};
		}
	}
	
	/** write byte, int, long to the stream */
	private static boolean readSomeData(String fileName){
		DataInputStream dis=null;
		try{
			dis=new DataInputStream(new FileInputStream(fileName));
			System.out.println("Read Byte:"+dis.readByte());
			System.out.println("Read Int:"+dis.readInt());
			System.out.println("Read Long:"+dis.readLong());
			return true;
		}catch(Exception ex){
			System.out.println("#readSomeData Exception:"+ex.getMessage());
			return false;
		}finally{
			try{
				dis.close();
			}catch(Exception ex){
			}
		}
	}
}
