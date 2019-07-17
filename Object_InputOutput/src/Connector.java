import java.sql.*;

/** класс для создания и получения соединения с базой
 * используется во время отладки 
 * */
public class Connector {
	// Oracle parameter's

 	private static String field_path="jdbc:oracle:thin:@192.168.15.254:1521:demo";
	private static String field_login="bc_reports";
	private static String field_password="bc_reports";
	
	
	private java.sql.Connection field_connection;
	
	private void debug(String information){
		System.out.println("Connector: "+information);
	}
	private void error(String information){
		System.out.println("Connector:  "+information);
	}
	
	/** соединение со значениями по умолчанию*/
	public Connector(){
		this(field_path,
			 field_login,
			 field_password);
	}
	
	/**
	 * соединение с логином и паролем
	 * @param login логин
	 * @param password пароль
	 */
	public Connector(String login, String password){
		this(field_path,login,password);
	}
	/**
	 * соединение с базой данных, используя  
	 * @param path полный URL
	 * @param login логин
	 * @param password пароль
	 */
	public Connector(String path, String login, String password){
		
		try{
			debug("try connection with database");
			Class.forName("oracle.jdbc.driver.OracleDriver");
			this.field_connection=DriverManager.getConnection(path,login,password);
			debug("connection successful");
		}catch(SQLException ex){
			SQLException temp_exception=null;
			int exception_counter=0;
			error("Connection SQLException:");
			while((temp_exception=ex.getNextException())!=null){
				error((++exception_counter)+":"+temp_exception.getMessage());
			}
		}catch(Exception e){
			error("Connection Exception:"+e.getMessage());
		}
		debug("Constructor END");
	}
	
	/**
	 * get connection to DataBase
	 */
	public Connection getConnection(){
		return this.field_connection;
	}
}
