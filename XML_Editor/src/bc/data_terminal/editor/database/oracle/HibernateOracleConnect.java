package bc.data_terminal.editor.database.oracle;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Properties;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

public class HibernateOracleConnect {
	private SessionFactory field_session_factory;

	/** получить Properties для конфигурирования AnnotationConfiguration 
	 * @param path URL к Oracle 
	 * @param login логин
	 * @param password пароль
	 * @param pool_count размер пула соединений 
	 * */
	private Properties getPropertiesConfiguration(String path,
												  String login, 
												  String password, 
												  Integer pool_count){
        Properties properties=new Properties();
        properties.setProperty("hibernate.connection.driver_class", "oracle.jdbc.driver.OracleDriver");
        
        properties.setProperty("hibernate.connection.url",path);
        properties.setProperty("hibernate.connection.username", login);
        properties.setProperty("hibernate.connection.password", password);
        properties.setProperty("hibernate.connection.pool_size", pool_count.toString());
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.OracleDialect");
        properties.setProperty("hibernate.show_sql", "true");
        properties.setProperty("hibernate.cache.provider_class", "org.hibernate.cache.NoCacheProvider");
        properties.setProperty("hbm2ddl.auto", "update");
        //properties.setProperty("current_session_context_class", "thread");
        return properties;
	}
	
	/** объект, который получает сессии
	 * @param path of connection (jdbc:oracle:thin:@192.168.15.254:1521:demo)
	 * @param login логин
	 * @param password пароль
	 * @param pool_size размер пула
	 * */
	public HibernateOracleConnect(String path,
									String login, 
									String password, 
									int pool_size){
        try{
            AnnotationConfiguration aconf = new AnnotationConfiguration();
            // можно создать Properties
            Integer poolSize;
            if(pool_size<0){
            	poolSize=new Integer(0);
            }else{
            	poolSize=new Integer(pool_size);
            }
            aconf.setProperties(getPropertiesConfiguration(path,
            											   login,
            											   password,
            											   poolSize));
            // добавить все POJO классы 
            aconf.addAnnotatedClass(TablePurchasesInt.class);
           
            this.field_session_factory=aconf.buildSessionFactory();
        }catch(Exception ex){
            System.out.println("Except:"+ex.getMessage());
        }
	}
	
	public Session getSession(){
		try{
			return this.field_session_factory.openSession();
		}catch(NullPointerException ex){
			return null;
		}
	}

	/** закрыть соединение с Hibernate*/
	public void close(){
		if(this.field_session_factory!=null){
			this.field_session_factory.close();
			this.field_session_factory=null;
		}
	}
	
	public void finalize(){
		if(this.field_session_factory!=null){
			this.field_session_factory.close();
		}
	}

	
	private HashMap<Connection,Session> field_session_connection=new HashMap<Connection,Session>();
	/** получить Connection из POOL */
	@SuppressWarnings("deprecation")
	public Connection getConnection(){
		Session session=this.getSession();
		Connection connection=session.connection();
		this.field_session_connection.put(connection, session);
		return connection;
	}
	
	/** закрыть Connection */
	public void closeConnection(Connection connection){
		Session session_for_close=this.field_session_connection.get(connection);
		try{
			connection.close();
		}catch(Exception ex){};
		session_for_close.close();
	}
}
