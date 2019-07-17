
import java.beans.PropertyVetoException;
import java.sql.Connection;
import com.mchange.v2.c3p0.*;

/**
 * @author First
 *
 */
public class PoolConnect {
	private ComboPooledDataSource field_cpds;
	private String field_driver;
	private String field_path;
	private String field_user;
	private String field_password;
	
	/** Создать объект - DataSource соединения с базой данных 
	 * @param driverName полное имя класса драйвера ("org.postgresql.Driver" )
	 * @param databasePath полный путь к базе данных ( "jdbc:postgresql://localhost/testdb" )
	 * @param user имя пользователя
	 * @param password пароль 
	 * @throws PropertyVetoException
	 */
	public PoolConnect(String driverName,
					   String databasePath,
					   String user,
					   String password) throws PropertyVetoException{
		this.field_driver=driverName;
		this.field_path=databasePath;
		this.field_user=user;
		this.field_password=password;
		create();
	}
	
	private void create() throws PropertyVetoException{
		ComboPooledDataSource field_cpds = new ComboPooledDataSource();
		field_cpds.setDriverClass(this.field_driver);
		field_cpds.setJdbcUrl(this.field_path); 
		field_cpds.setUser(this.field_user); 
		field_cpds.setPassword(this.field_password);
		
		field_cpds.setMinPoolSize(1); 
		field_cpds.setAcquireIncrement(2); 
		field_cpds.setMaxPoolSize(3); 		
	}
	
	public Connection getConnection(){
		try{
			return this.field_cpds.getConnection();
		}catch(Exception ex){
			return null;
		}
		
	}
	
	public void destroy(){
		try{
			DataSources.destroy(field_cpds);
		}catch(Exception ex){
			
		}
		
	}
}
