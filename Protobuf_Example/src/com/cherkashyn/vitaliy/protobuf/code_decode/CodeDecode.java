package com.cherkashyn.vitaliy.protobuf.code_decode;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.cherkashyn.vitaliy.protobuf.exchange.ElementWorker.Position;
import com.google.protobuf.CodedOutputStream;

public class CodeDecode {
	public static void main(String[] args){
		String pathToFile="/home/technik/temp/test.bin";

		writeToFile(pathToFile, getPosition());
		
		printPosition(readFromFile(pathToFile));
	}
	
	/**
	 * read object from file 
	 * @param pathToFile - path to file with object
	 * @return {@link Position}
	 */
	private static Position readFromFile(String pathToFile) {
		InputStream inputStream=null;
		try{
			return Position.newBuilder()
					.mergeFrom( (inputStream=new FileInputStream(pathToFile)) )
						.build();
		}catch(IOException ex){
			System.err.println("readFromFile Exception:"+ex.getMessage());
			return null;
		}finally{
			try{
				inputStream.close();
			}catch(Exception ex){};
		}
	}

	/** 
	 * print result 
	 * @param position
	 */
	private static void printPosition(Position position){
		if(position==null){
			System.out.println("Position: null");
		}else{
			System.out.println(position.getId()+" : "+position.getPositionName()+"  :  "+position.getSalary());
		}
	}
	
	/**
	 * write the Position to OutputStream 
	 * @param pathToFile - path to file 
	 * @param position - object for save 
	 */
	private static void writeToFile(String pathToFile, 
									Position position)  {
		FileOutputStream fileOutputStream=null;
		try {
			CodedOutputStream outputStream = CodedOutputStream.newInstance( (fileOutputStream=new FileOutputStream(pathToFile)) );
			position.writeTo(outputStream);
			outputStream.flush();
		}catch (IOException ex) {
			System.err.println("writeToFile Exception:"+ex.getMessage());
		}finally{
			try{
				fileOutputStream.close();
			}catch(Exception ex){};
		}
	}
	
	/**
	 * create the Position by Builder
	 * @return
	 */
	private static Position getPosition(){
		return 
				Position.newBuilder()
				.setId(1)
				.setSalary(1250)
				.setPositionName("designer")
				.build();
	}
}
