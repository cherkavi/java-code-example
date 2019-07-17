package datastream;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.junit.Assert;
import org.junit.Test;

public class ObjectStream {
	
	public static void main(String[] args){
		System.out.println("begin");
		String fileName="c:\\out_data.bin";
		writeObject(fileName, 5);
		System.out.println(readObject(fileName));
		System.out.println("-end-");
	}
	
	@Test	
	public void Test(){
		String fileName="c:\\test.bin";
		writeObject(fileName, 5.d);
		Assert.assertNotNull(readObject(fileName));
	}

	private static void writeObject(String fileName, Object value){
		FileOutputStream fos=null;
		try{
			fos=new FileOutputStream(fileName);
			ObjectOutputStream oos=new ObjectOutputStream(fos);
			oos.writeObject(value);
		}catch(Exception ex){
			System.err.println("WriteObject Exception:"+ex.getMessage());
		}finally{
			try{
				fos.close();
			}catch(Exception ex){};
		}
	}
	
	private static Object readObject(String fileName){
		FileInputStream fis=null;
		try{
			Object returnValue=null;
			fis=new FileInputStream(fileName);
			ObjectInputStream input=new ObjectInputStream(fis);
			returnValue=input.readObject();
			return returnValue;
		}catch(Exception ex){
			System.err.println("#readObject Exception:"+ex.getMessage());
			return null;
		}finally{
			try{
				
			}catch(Exception ex){};
		}
	}
}

class ObjectForWrite implements Serializable{
	private final static long serialVersionUID=1L;
	
	private String value;
	
	public ObjectForWrite(String value){
		this.value=value;
	}
	
	@Override
	public String toString(){
		return value;
	}
}
