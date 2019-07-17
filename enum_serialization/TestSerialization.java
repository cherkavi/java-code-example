package enum_serialization;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class TestSerialization {
	public static void main(String[] args){
		System.out.println("begin");
		
		String pathToFile="c:\\temp_file.bin";
		ControlEnum value=ControlEnum.one;
		value.setString("this is temp string for save");
		value.addParameters("this is first parameter");
		writeObject(pathToFile,value);
		
		ControlEnum readedValue=(ControlEnum)readObject(pathToFile);
		System.out.println("Readed Value:"+readedValue.getString()+"   Parameter:"+readedValue.getParameter(0));
			
		System.out.println("-end-");
	}
	
	/** сохранить сериализованный объект */
	private static void writeObject(String pathToFile, Object object){
		ObjectOutputStream out=null;
		try{
			out=new ObjectOutputStream(new FileOutputStream(pathToFile));
			out.writeObject(object);
		}catch(Exception ex){
			System.out.println("writeObject: "+ex.getMessage());
		}finally{
			try{
				out.close();
			}catch(Exception ex){};		
		}
	}
	
	/** прочесть сериализованный объект */
	private static Object readObject(String pathToFile){
		Object returnValue=null;
		ObjectInputStream input=null;
		try{
			input=new ObjectInputStream(new FileInputStream(pathToFile));
			returnValue=input.readObject();
		}catch(Exception ex){
			System.out.println("readObject: "+ex.getMessage());
		}finally{
			try{
				input.close();
			}catch(Exception ex){};		
		}
		return returnValue;
	}
}
