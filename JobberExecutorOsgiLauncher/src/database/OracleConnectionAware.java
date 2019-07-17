package database;
import java.sql.*;

import org.apache.log4j.*;

/** класс для создания и получения соединения с базой
 * используется во время отладки 
 * */
public class OracleConnectionAware implements IConnectionAware{
	private static String field_path="jdbc:oracle:thin:@192.168.15.254:1521:demo";
	private static String field_login="bc_reports";
	private static String field_password="bc_reports";
	private static Logger field_logger;
	static{
		field_logger=Logger.getLogger("database");
		// field_logger.setLevel(Level.DEBUG);
		// configuration logger
		//org.apache.log4j.BasicConfigurator.configure();
	}
	/** соединение со значениями по умолчанию*/
	public OracleConnectionAware(){
		this(field_path,
			 field_login,
			 field_password);
	}
	/**
	 * соединение с логином и паролем
	 * @param login логин
	 * @param password пароль
	 */
	public OracleConnectionAware(String login, String password){
		this(field_path,login,password);
	}
	/**
	 * соединение с базой данных, используя  
	 * @param path полный URL
	 * @param login логин
	 * @param password пароль
	 */
	public OracleConnectionAware(String path, String login, String password){
		field_path=path;
		field_login=login;
		field_password=password;
		
		
	}
	
	@Override
	public Connection getConnection() {
		// System.out.println("Get new connection");
		return this.getNewConnection();
	}

	private Connection getNewConnection(){
		Connection returnValue=null;
		try{
			field_logger.debug("try connection with database");
			Class.forName("oracle.jdbc.driver.OracleDriver");
			returnValue=DriverManager.getConnection(field_path,field_login,field_password);
			field_logger.debug("connection successful");
		}catch(SQLException ex){
			SQLException temp_exception=null;
			int exception_counter=0;
			field_logger.error("Connection SQLException:");
			while((temp_exception=ex.getNextException())!=null){
				field_logger.error((++exception_counter)+":"+temp_exception.getMessage());
			}
		}catch(Exception e){
			field_logger.error("Connection Exception:"+e.getMessage());
		}
		return returnValue;
	}
	
}
