package bonpay.osgi.launcher.settings.storage;

import java.sql.CallableStatement;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import bonpay.osgi.launcher.settings.storage.database_type.DatabaseTypeConverter;
import bonpay.osgi.launcher.settings.storage.database_type.DateConverter;
import bonpay.osgi.launcher.settings.storage.database_type.FloatConverter;
import bonpay.osgi.launcher.settings.storage.database_type.IntegerConverter;


import database.FirebirdConnectionAware;
import database.IConnectionAware;

/** объект - хранилище на основании базы данных, и заранее предопределенной структуры вида:
 * <br>
 * CREATE TABLE JOBBER_SETTINGS (<br> 
	ID          INTEGER NOT NULL,<br>
	NAME        VARCHAR(50),<br>
	VALUE_TYPE  VARCHAR(50),<br>
	CURRENT_VALUE     VARCHAR(255));<br>
	где:
	<table border=1>
		<tr>
			<th>field name</th> <th> field descripton</th>
		</tr>
		<tr>
			<td>NAME </td> <td> наименование </td>
		</tr>
		<tr>
			<td>VALUE_TYPE</td> <td>тип:<br>
									F - Float<br>
									I - Integer<br>
									D - date (D yyyy.MM.dd HH:mm:ss) <br>
								</td>
		</tr>
		<tr>
			<td>CURRENT_VALUE</td> <td> текущее значение в текстовом виде</td>
		</tr>
	</table>
 * */
public class DatabaseStorage implements IStorage{
	private HashMap<String, Object> storage=new HashMap<String,Object>();
	private Logger logger=Logger.getLogger(this.getClass());
	private ArrayList<DatabaseTypeConverter> listOfConverter=new ArrayList<DatabaseTypeConverter>();
	
	/** объект, который ведает соединениями с базой данных  */
	private IConnectionAware connectionAware;
	
	/** объект - хранилище на основании базы данных, и заранее предопределенной структуры вида:
	 * <br>
	 * CREATE TABLE JOBBER_SETTINGS (<br> 
    	ID          INTEGER NOT NULL,<br>
    	NAME        VARCHAR(50),<br>
    	VALUE_TYPE  VARCHAR(50),<br>
    	CURRENT_VALUE     VARCHAR(255));<br>
    	где:
    	<table border=1>
    		<tr>
    			<th>field name</th> <th> field descripton</th>
    		</tr>
    		<tr>
    			<td>NAME </td> <td> наименование </td>
    		</tr>
    		<tr>
    			<td>VALUE_TYPE</td> <td>тип:<br>
										F - Float<br>
										I - Integer<br>
										D - date (D yyyy.MM.dd HH:mm:ss) <br>
									</td>
    		</tr>
    		<tr>
    			<td>CURRENT_VALUE</td> <td> текущее значение в текстовом виде</td>
    		</tr>
    	</table>
    	@param connectionAware - объект, который ведает соединениями с базой данных  
	 * */
	public DatabaseStorage(IConnectionAware connectionAware){
		this.connectionAware=connectionAware;
		this.listOfConverter.add(new DateConverter());
		this.listOfConverter.add(new FloatConverter());
		this.listOfConverter.add(new IntegerConverter());
	}
	
	@Override
	public Object getRecord(String name) {
		return this.storage.get(name);
	}

	@Override
	public boolean loadAllRecords() {
		boolean returnValue=false;
		Connection connection=this.connectionAware.getConnection();
		try{
			ResultSet rs=connection.createStatement().executeQuery("select * from bc_admin.vc_ext_job_settings_all");
			while(rs.next()){
				this.loadRecordFromResultSet(rs);
			}
			rs.getStatement().close();
			returnValue=true;
		}catch(Exception ex){
			logger.error("loadAllRecords Exception: "+ex.getMessage());
		}finally{
			try{
				connection.close();
			}catch(Exception ex){};
		}
		return returnValue;
	}

	private void loadRecordFromResultSet(ResultSet rs) throws Exception {
		String name=rs.getString("NAME_PARAM");
		String type=rs.getString("VALUE_TYPE");
		String currentValue=rs.getString("CURRENT_VALUE");
		this.storage.put(name, this.convertStringToObject(type, currentValue));
	}
	
	/** конвертировать строку в объект */
	private Object convertStringToObject(String type, String currentValue){
		for(int counter=0;counter<this.listOfConverter.size();counter++){
			if(this.listOfConverter.get(counter).typeEquals(type)){
				try{
					return this.listOfConverter.get(counter).getObjectFromString(currentValue);
				}catch(Exception ex){
					logger.error("convertStringToObject Exception:"+ex.getMessage());
					return null;
				}
			}
		}
		return null;
	}
	
	@Override
	public boolean putRecord(String name, Object value) {
		this.storage.put(name, value);
		return true;
	}

	@Override
	public void removeRecord(String name) {
		this.storage.remove(name);
	}

	@Override
	public boolean replaceRecord(String name, Object value) {
		this.storage.put(name, value);
		return true;
	}

	@Override
	public boolean saveAllRecords() {
		boolean returnValue=false;
		Connection connection=this.connectionAware.getConnection();
		try{
			Iterator<String> iterator=this.storage.keySet().iterator();
			while(iterator.hasNext()){
				String key=iterator.next();
				Object value=this.storage.get(key);
				this.saveOrUpdate(connection, key,value);
			}
			returnValue=true;
		}catch(Exception ex){
			logger.error("saveAllRecord Exception:"+ex.getMessage());
		}finally{
			try{
				connection.close();
			}catch(Exception ex){};
		}
		return returnValue;
	}

	
	/** записать параметр (обновить или вставить )
	 * @param connection - соединение с базой данных 
	 * @param key - ключ
	 * @param value - значение 
	 * @throws Exception
	 */
	private void saveOrUpdate(Connection updateConnection, String key, Object value) throws Exception {
		String type=this.getTypeFromObject(value);
		String stringValue=this.getStringFromObject(type, value);
		if(type!=null){
			/*
			// проверить существует ли ключ
			if(isFieldExists(connection, "JOBBER_SETTINGS","NAME",key)){
				// ключ существует - обновить
				String query=("update jobber_settings set current_value=?,  value_type=? where name=?");
				PreparedStatement ps=connection.prepareStatement(query);
				ps.setString(1,stringValue);
				ps.setString(2,type);
				ps.setString(3,key);
				ps.executeUpdate();
				connection.commit();
			}else{
				// ключ не существует - не обновлять
				String query=("insert into jobber_settings(name, value_type, current_value) values (?,?,?)");
				PreparedStatement ps=connection.prepareStatement(query);
				ps.setString(1,key);
				ps.setString(2,type);
				ps.setString(3,stringValue);
				ps.executeUpdate();
				connection.commit();
			}*/
			/*
			 * FUNCTION set_setting(
     			p_name_param IN VARCHAR2
    			,p_value_type IN VARCHAR2
    			,p_current_value IN VARCHAR2
    			,p_result_msg OUT VARCHAR2
				) RETURN VARCHAR2*/
			CallableStatement statement = updateConnection.prepareCall("{?= call BC_ADMIN.PACK_UI_EXT_JOB.set_setting(?,?,?,?)}");
			statement.registerOutParameter(1, Types.VARCHAR);
			statement.setString(2,key);
			statement.setString(3,type);
			statement.setString(4,stringValue);
			statement.registerOutParameter(5, Types.VARCHAR);
            statement.executeUpdate();
			if(statement.getString(1).equals("0")){
				//returnValue=true;
			}else{
				System.err.println("saveOrUpdate Exception: "+statement.getString(4));
				//returnValue=false;
			}
            statement.close();
		}else{
			throw new Exception("type is not recognized "+value);
		}
	}

	/** получить объект в виде строки, на основании типа и значения */
	private String getStringFromObject(String type, Object value){
		for(int counter=0;counter<this.listOfConverter.size();counter++){
			if(this.listOfConverter.get(counter).typeEquals(type)){
				try{
					return this.listOfConverter.get(counter).getStringFromObject(value);
				}catch(Exception ex){
					logger.error("getStringFromObject Exception:"+ex.getMessage());
					return null;
				}
			}
		}
		return null;
	}
	
	
	/** получить тип объекта */
	private String getTypeFromObject(Object value){
		for(int counter=0;counter<this.listOfConverter.size();counter++){
			String currentType=this.listOfConverter.get(counter).getTypeFromObject(value);
			if(currentType!=null){
				return currentType;
			}
		}
		return null;
	}
	
	/** проверить, существует ли поле в таблице */
/*	private boolean isFieldExists(Connection connection, String table, String fieldName, String key) {
		boolean returnValue=false;
		try{
			PreparedStatement ps=connection.prepareStatement(" select * from "+table+" where "+fieldName+"=?");
			ps.setString(1, key);
			ResultSet rs=ps.executeQuery();
			returnValue=rs.next();
			rs.getStatement().close();
		}catch(Exception ex){
			logger.error("isFieldCreate Exception: "+ex.getMessage());
		}
		return returnValue;
	}
*/	
	
	public static void main(String[] args){
		Logger.getLogger("bonpay.jobber").setLevel(Level.DEBUG);
		Logger.getLogger("bonpay.jobber").addAppender(new ConsoleAppender(new PatternLayout()));
		
		System.out.println("begin");
		IStorage storage=new DatabaseStorage(new FirebirdConnectionAware("","jobber","SYSDBA","masterkey"));
		if(storage.loadAllRecords()){
			System.out.println("load OK");
			Date date=(Date)storage.getRecord("current_date");
			SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");
			if(date!=null){
				System.out.println("Date: "+sdf.format(date));
			}
			Integer integerValue=(Integer)storage.getRecord("integer value");
			System.out.println("IntegerValue: "+integerValue);
			Float floatValue=(Float)storage.getRecord("float");
			System.out.println("FloatValue: "+floatValue);
		}else{
			System.out.println("load Error ");
		};
		storage.putRecord("current_date", new Date());
		storage.putRecord("integer value", new Integer(10));
		storage.putRecord("float", new Float(2.3f));
		if(storage.saveAllRecords()){
			System.out.println("save OK");
		}else{
			System.out.println("save ERROR");
		}
		System.out.println("end");
	}
	
}
