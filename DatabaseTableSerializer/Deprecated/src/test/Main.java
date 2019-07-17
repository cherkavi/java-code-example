package test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;

import database.connector.FirebirdConnection;
import database.connector.IConnector;
import database.serializer.DatabaseColumn;
import database.serializer.RecordFactory;
import database.serializer.RecordWrap;

public class Main {
	public static void main(String[] args){
		System.out.println("begin");
		IConnector connector=new FirebirdConnection(null, "messenger", "SYSDBA","masterkey");
		//RecordFactory factory=new RecordFactory("USER_DATA_GROUP","ID",new DatabaseColumn("ID"),new DatabaseColumn("NAME"));
		RecordFactory factory=new RecordFactory("USER_DATA_GROUP","ID",new DatabaseColumn("NAME"));
		
		RecordWrap recordWrap=factory.getRecords(connector.getConnection(),4,5,6,7);
		String fileName="c:\\temp.bin";
		FileOutputStream output=null;
		try{
			output=new FileOutputStream(fileName);
			factory.writeToStream(output, recordWrap);
		}catch(Exception ex){
			System.err.println("Exception ex:"+ex.getMessage());
		}finally{
			try{
				output.close();
			}catch(Exception ex){};
		}
		getObjectFromFile(fileName);
		System.out.println("end");
	}
	
	
	private static void getObjectFromFile(String pathToFile){
		FileInputStream fis=null;
		try{
			fis=new FileInputStream(pathToFile);
			ObjectInputStream input=new ObjectInputStream(fis);
			RecordWrap record=(RecordWrap)input.readObject();
			int rowCount=record.getRowCount();
			for(int rowCounter=0;rowCounter<rowCount;rowCounter++){
				for(int counter=0;counter<record.getColumnSize();counter++){
					System.out.print(record.getFieldNames()[counter]+": "+record.getObjects(rowCounter)[counter]+"    ");
				}
				System.out.println();
			}
		}catch(Exception ex){
			System.out.println("Error ");
		}finally{
			try{
				fis.close();
			}catch(Exception ex){};
		}
		
	}
}
