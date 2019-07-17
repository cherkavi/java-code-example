package jdbc_copy.connection;

import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Properties;

import org.apache.log4j.Logger;

/** ������, ������� ���������� ���������� � ����� ������ � ���� JDBC URL, ����� ��������, ��� �������� */
public class UserConnection implements IConnectionAware{
	private Logger logger=Logger.getLogger(this.getClass());
	private final static long serialVersionUID=1L;

	private Connection connection;
	private String login;
	private String password;
	private String url;
	private String driverClassName;
	
	public String getDriverClassName() {
		return driverClassName;
	}

	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}

	public enum Drivers{
		/** firebird driver */
		firebird("org.firebirdsql.jdbc.FBDriver"),
		/** MySQL driver  */
		mysql("org.gjt.mm.mysql.Driver"),
		/** Oracle Driver */
		oracle("oracle.jdbc.driver.OracleDriver");
		
		private String className;
		
		Drivers(String className){
			this.className=className;
		}
		
		public String getClassName(){
			return this.className;
		}
	}
	
	/** try to connect Connect */
	public void connect() throws Exception {
        logger.debug("---URLClassLoader:");
        Class<?> class_value=Class.forName(this.getDriverClassName());
        logger.debug("---classValue:"+class_value);
        Object d_object=class_value.newInstance();
        logger.debug("---object Driver:"+d_object);
        Driver driver = (Driver)class_value.newInstance();
        logger.debug("---to Driver:"+driver);
        DriverManager.registerDriver(driver);
        logger.debug("---registered Driver");
        Properties properties=new Properties();
        properties.put("user",this.getLogin());
        properties.put("password",this.getPassword());
        this.connection=driver.connect(this.getUrl(),properties);
	}
	
	/** break the connection  */
	public void disconnect(){
		try{
			this.connection.close();
			this.connection=null;
		}catch(Exception ex){};
	}

    /** �������� ������ �� ��������� ���� � Jar � ����� */
	@SuppressWarnings("unused")
	private Class<?> loadClass(String path_to_jar, String class_name){
        Class<?> return_value=null;
        try{
            URLClassLoader urlLoader = new URLClassLoader(new URL[]{new URL("file", null, path_to_jar)});
            return_value=urlLoader.loadClass(class_name);
            System.out.println("Loaded class:"+return_value.getName());
        }catch(Throwable ex){
            System.out.println(" error in load class"+ex.getMessage());
        };
        return return_value;
    }

	/** �������� ���������� � ����� ������
	 * @return null - ���� ������ ���������� �� ���� 
	 * */
	@Override
	public Connection getConnection(){
		return this.connection;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	
}
