package test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.sql.Connection;
import database.connector.FirebirdConnection;
import database.connector.IConnector;
import database.serializer.common.RecordWrap;
import database.serializer.pack.PackRecordFactory;
import database.serializer.pack.column.DatabaseColumn;
import database.serializer.unpack.UnpackRecordFactory;
import database.serializer.unpack.interceptors.TablePartColumns;
import database.serializer.unpack.interceptors.TablePartCopy;
import database.serializer.unpack.interceptors.columns.ColumnInterceptor;
import database.serializer.unpack.interceptors.columns.FirebirdIdGenerator;
import database.serializer.unpack.interceptors.columns.LoggerInterceptor;

public class Main {
	public static void main(String[] args){
		System.out.println("begin");
		IConnector connector=new FirebirdConnection(null, "V:\\Database\\PARKING.GDB", "SYSDBA","masterkey");
		//RecordFactory factory=new RecordFactory("USER_DATA_GROUP","ID",new DatabaseColumn("ID"),new DatabaseColumn("NAME"));
		// получить данные из базы данных и сохранить их в
		
		// создать фабрику для упаковки данных в объекты 
		PackRecordFactory factory=new PackRecordFactory("EVENT",
												"ID",
												new DatabaseColumn("ID"),
												new DatabaseColumn("FILENAME"),
												new DatabaseColumn("DATE_SENSOR")
												);
		RecordWrap recordWrap=factory.getRecords(connector.getConnection(),1,2,3);
		// отпрвить объект в OutputStream 
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
		
//--------------------------------		
		// прочесть объект из InputStream
		IConnector serverConnector=new FirebirdConnection(null, "V:\\Database\\ParkingServer.gdb", "SYSDBA","masterkey");
//		UnpackRecordFactory unpack=new UnpackRecordFactory(new TableFullCopy("EVENT","EVENT1"));
		UnpackRecordFactory unpack=new UnpackRecordFactory(new TablePartCopy("EVENT",
																			 new TablePartColumns("EVENT1",
																					 			  new String[]{
																					 						   "DATE_SENSOR"},
																					 			  new ColumnInterceptor[]{
																					 									  new FirebirdIdGenerator("ID","GEN_EVENT1_ID"),
																					 									  new LoggerInterceptor("FILENAME","c:\\temp\\") 
																					 									  }
																			 					  )
																			 )
														   );
		
		Connection connection=serverConnector.getConnection();
		FileInputStream fis=null;
		try{
			fis=new FileInputStream(fileName);
			ObjectInputStream input=new ObjectInputStream(fis);
			Object object=input.readObject();
			unpack.processPackage(new RecordWrap[]{(RecordWrap)object} , connection);
			connection.commit();
		}catch(Exception ex){
			System.err.println("Read from stream Exception: "+ex.getMessage());
		}finally{
			try{
				fis.close();
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
