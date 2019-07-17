import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;

import object.Parent;
import java.sql.*;

import oracle.jdbc.driver.OracleCallableStatement;
import oracle.jdbc.driver.OracleConnection;
import oracle.jdbc.driver.OracleTypes;
import oracle.sql.BLOB;


public class Utility {
	
	private static void debug(String information){
		System.out.println("Utility: "+information);
	}
	private static void error(String information){
		System.out.println("Utility:  "+information);
	}
	
	/** <b>записать объект в byte[] и вернуть его</b> 
	 * @param object - объект, который нужно записать
	 * @return возвращает массив из байт, который содержит данный объект
	 * */
	public static byte[] writeObject_to_array(Object object){
		byte[] return_value=null;
		try{
			ByteArrayOutputStream byte_array_output_stream=new ByteArrayOutputStream();
			ObjectOutputStream oos=new ObjectOutputStream(byte_array_output_stream);
			oos.writeObject(object);
			return_value=byte_array_output_stream.toByteArray();
			System.out.println("Object write to byte_array is OK");
		}catch(Exception ex){
			System.out.println("ObjectOutputStream Error:"+ex.getMessage());
			return_value=null;
		}
		return return_value;
	}
	
	/** <b>прочитать объект из byte[] и вернуть объект</b>
	 * @param массив из байт, который содержит объект 
	 * @return прочитанный из данной последовательности объект
	 * */
	public static Object readObject_from_array(byte[] array_of_byte){
		Object return_value=null;
		try{
			debug("ByteInputStream");
			if(array_of_byte==null){
				error("readObject_from_array parameter is null ");
			}
			ByteArrayInputStream byte_array_input_stream=new ByteArrayInputStream(array_of_byte);
			debug("ObjectInputStream");
			ObjectInputStream ois=new ObjectInputStream(byte_array_input_stream);
			debug("readObject");
			return_value=ois.readObject();
			System.out.println("Object read from byte_array is OK");
		}catch(Exception ex){
			System.out.println("ObjectInputStream Error:"+ex.getMessage());
		}
		return return_value;
	}
	
	/** <b>записать объект в файл</b> 
	 * @param path_to_file - путь к файлу, куда нужно записать данный объект
	 * @param parent - объект, который должен быть записан 
	 * */
	public static void writeObject_to_file(String path_to_file,Parent parent){
		try{
			System.out.println("writeObject: create file");
			File f=new File(path_to_file);
			System.out.println("writeObject: create FileOutputStream");
			FileOutputStream fos=new FileOutputStream(f);
			System.out.println("writeObject: create ObjectOutputStream");
			ObjectOutputStream oos=new ObjectOutputStream(fos);
			System.out.println("writeObject: write Object");
			oos.writeObject(parent);
			System.out.println("writeObject: ObjectOutputStream close");
			oos.close();
			System.out.println("writeObject: FileOuputStream close");
			fos.close();
		}catch(Exception ex){
			System.out.println("writeObject: Exception:"+ex.getMessage());
		}
	}
	
	/** <b>прочитать объект из файла</b>
	 * @param path_to_file - путь к файлу, в котором записан объект
	 * @return прочитанный объект 
	 * */
	public static Parent readObject_from_file(String path_to_file){
		Parent return_value=null;
		try{
			System.out.println("readObject: create file");
			File f=new File(path_to_file);
			System.out.println("readObject: create FileInputStream");
			FileInputStream fos=new FileInputStream(f);
			System.out.println("readObject: create ObjectInputStream");
			ObjectInputStream oos=new ObjectInputStream(fos);
			System.out.println("readObject: read Object");
			return_value=(Parent)oos.readObject();
			System.out.println("readObject: ObjectInputStream close");
			oos.close();
			System.out.println("readObject: FileInputStream close");
			fos.close();
		}catch(Exception ex){
			System.out.println("readObject: Exception:"+ex.getMessage());
		}
		return return_value;
	}
	

	public static String write_to_database(byte[] value){
		debug("write to database: "+hexDump(value));
		// TODO: запись в базу 
		String return_value="";
		// параметр, уникальный для базы данных, точнее для созданных процедур
		String session_id="003";
		String param_name="OBJECT";
		try{
			debug("get connection ");
			// ORACLE
			
			Connector connector=new Connector();
			Connection connection=connector.getConnection();
			debug("create statement");
			CallableStatement statement = connection.prepareCall("{?= call PACK_BC_APPLET.set_param_value(?, ?, ?, ?)}");
			debug("set parameters");
            
			statement.registerOutParameter(1, Types.VARCHAR);
            statement.setString(2,session_id);
            statement.setString(3,param_name);
            statement.setBytes(4,value);
            //statement.setObject(4,new byte[]{});
            //statement.setBytes(4, value);
            /*
            java.sql.Blob blob=connection.createBlob();
            blob.setBytes(value.length,value);
            statement.setBlob(4, blob);
            */
            statement.registerOutParameter(5, Types.VARCHAR);
            
            
			debug("execute procedure ");
			statement.executeUpdate();
			debug("get result");
            return_value=statement.getString(1);
            debug("result: "+return_value);
            statement.close();
            connection.close();
		
/*
            // Firebird
			ConnectorFirebird connector=new ConnectorFirebird("c:\\blob_example");
			Connection connection=connector.getConnection();
			debug("create statement");
			PreparedStatement statement=connection.prepareStatement("INSERT INTO applet_user_parameters(KOD,SESSION_ID,PARAMETER_NAME,PARAMETER_VALUE) VALUES(?,?,?,?)");
			debug("set parameters");
			statement.setInt(1,0);
			statement.setString(2, "this is session_id");
			statement.setString(3, "this is parameter_name");
			statement.setBytes(4, value);
			debug("execute procedure ");
            statement.executeUpdate();
            debug("get result");
            //return_value=statement.getString(1);
            debug("result: "+return_value);
            statement.close();
            connection.close();
*/
            
		}catch(Exception ex){
			error(" Exception:"+ex.getMessage());
		}
		return return_value;
	}
	
	public static byte[] read_from_database(){
		// TODO чтение из базы
		byte[] return_value=null;
		// параметр, уникальный для базы данных, точнее для созданных процедур
		String session_id="003";
		String param_name="OBJECT";
		 //photo = ((OracleResultSet)rslt).getBLOB(1);
		try{
			// ORACLE

 			debug("get connection ");
			Connector connector=new Connector();
			Connection connection=connector.getConnection();
			debug("create statement");

/*
			CallableStatement statement = connection.prepareCall("{? = call PACK_BC_APPLET.get_param_value(?, ?, ?,?)}");
            //CallableStatement statement = connection.prepareCall("{? = call PACK_BC_ARM.get_cards_count(?)}");
			debug("set parameters");
				debug("1");
            statement.registerOutParameter(1, Types.VARCHAR);
            	debug("2");
            statement.setString(2,session_id);
            	debug("3");
            statement.setString(3,param_name);
            	debug("4");
            statement.registerOutParameter(4,OracleTypes.CLOB);
            	debug("5");
            statement.registerOutParameter(5, Types.VARCHAR);
			debug("execute procedure ");
            if(statement.execute()){
            	debug("Execute TRUE");
            }else{
            	debug("Execute FALSE");
            }
            debug("get result");
            //debug("===>>>"+statement.getString(4));
            //oracle.sql.BLOB blob=(oracle.sql.BLOB)statement.getBlob(4);
            oracle.sql.CLOB blob=(oracle.sql.CLOB)statement.getClob(4);
            debug("return_value");
            //return_value=blob.getBytes();
*/
			//String query="declare l_res number; begin l_res := PACK_BC_APPLET.get_param_value(?, ?, ?,?); end;";
			String query="{? = call PACK_BC_APPLET.get_param_value(?, ?, ?,?)}";
			OracleCallableStatement statement=(OracleCallableStatement)connection.prepareCall(query);
			debug("set parameters");
				//debug("1");
			statement.registerOutParameter(1, Types.VARCHAR);
        		//debug("2");
        	statement.setString(2,session_id);
        		//debug("3");
        	statement.setString(3,param_name);
        		//debug("4");
        	statement.registerOutParameter(4,Types.BINARY);
        		//debug("5");
        	statement.registerOutParameter(5, Types.VARCHAR);
        	debug("execute procedure ");
        	if(statement.execute()){
        		debug("Execute TRUE");
        	}else{
        		debug("Execute FALSE");
        	}
        	/*oracle.sql.BLOB blob=(oracle.sql.BLOB)statement.getBlob(4);
        	return_value=blob.getBytes();
        	return_value=statement.getBytes(4);
        	*/
        	//debug(">>>>>"+statement.getString(4));
        	return_value=statement.getBytes(4);
			
/*			debug("Execute");
			ResultSet result_set=connection.createStatement().executeQuery("SELECT * FROM bc_sec.BC_APPLET_PARAMETERS");
			debug("Open resultset");
			if(result_set.next()){
				debug("trying get");
				//return_value=result_set.getBytes(3);
				result_set.getBlob(3).get
				debug("OK");
			}
*/
            
            
/*
			// Firebird
			debug("get connection ");
			ConnectorFirebird connector=new ConnectorFirebird("c:\\blob_example");
			Connection connection=connector.getConnection();
			debug("create statement");
			ResultSet rs=connection.createStatement().executeQuery("Select * from applet_user_parameters");
			debug("get result");
			while(rs.next()){
				debug("get blob");
				java.sql.Blob result=rs.getBlob(4);
				debug("get bytes from blob");
				if(result==null){
					error("blob is null");
				}else{
					debug("OK:");
					//return_value=result.getBytes(0, (int)result.length());
					return_value=rs.getBytes(4);
				}
				
				break;
			}
            rs.close();
            rs.getStatement().close();
*/
			
		}catch(Exception ex){
			error(" Exception:"+ex.getMessage());
		}
		debug("read from database: "+hexDump(return_value));
		return return_value;
	}

	
	
	public final static String hexChars[] = { "0", "1", "2", "3", "4", "5",
		"6", "7", "8", "9", "A", "B", "C", "D", "E", "F" };

	public static int unsignedInt(int a) {
		if (a < 0) {
			return a + 256;
		}
		return a;
	}

	public static int min(int a, int b) {
		if (a < b) {
			return a;
		}
		return b;
	}

	
public static byte stringCharToBCDByte(String data, int pos) {
	return (byte) (Integer.parseInt(data.substring(pos, pos + 2), 16));
}

@SuppressWarnings("unused")
private final static String nullString = "null";

/** получить в виде HEX текста */
public static String hexDump(BigInteger big) {
	return hexDump(big.toByteArray());
}
/** получить в виде HEX текста */
public static String hexDump(byte[] data) {
	return hexDump(data, 0, data.length);
}
/** получить в виде HEX текста */
public static String hexDump(byte[] data, int length) {
	return hexDump(data, 0, length);
}
/** получить в виде HEX текста */
public static String hexDump(byte[] data, int offset, int length) {
	String result = "";
	String part = "";
	for (int i = 0; i < min(data.length, length); i++) {
		part = ""
				+ hexChars[(byte) (unsignedInt(data[offset + i]) / 16)]
				+ hexChars[(byte) (unsignedInt(data[offset + i]) % 16)];
		result = result + part;
	}
	return result;
}






final static public int tagLen = 1;

public static int getTagIdentifier(byte[] data, int pos) {
	return data[pos];
}

public static int getDataLen(byte[] data, int pos) {
	int i = 0;
	int len = 0;
	while (unsignedInt(data[pos + i]) == 255) {
		len = len * 255 + 255;
		i++;
	}
	len += unsignedInt(data[pos + i]);
	return len;
}

public static int getLenLen(byte[] data, int pos) {
	int i = 0;
	while (unsignedInt(data[pos + i]) == 255) {
		i++;
	}
	return i + 1;
}

public static String getString(byte[] data, int pos, int len)
		throws Exception{
	return new String(data, pos, len);
}

public static byte[] selectBytes(byte[] data, int pos, int len)
		throws Exception {
	byte[] res = new byte[len];
	for (int i = 0; i < len; i++)
		res[i] = data[pos + i];
	return res;
}
// -- Вспомогательные методы
// формирует строку hex определенной длины, добавлят слева нулями
public static String intToHexStringF(int data, int len) {
	String Indata = Integer.toHexString(data).toUpperCase();
	String Outdata = "";
	for (int i = 0; i < (len - Indata.length()); i++) {
		Outdata = Outdata + "0";
	}
	Outdata = Outdata + Indata;
	return Outdata;
};

	
}
