package write_users.data_source;

import java.sql.Connection;
import java.sql.DriverManager;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionFactoryBuilder {

	public static SessionFactory getSessionFactory(String configuration) throws Exception{
		Configuration config=new Configuration().configure(configuration);
		// config.setProperty("hibernate.dialect", "org.hibernate.dialect.OracleDialect");
//		if(mapping!=null){
//			for(String mapFile:mapping){
//				config.addResource(mapFile);
//			}
//		}
		Class.forName ("oracle.jdbc.OracleDriver");
		String url=(String)config.getProperties().get("hibernate.connection.url");
		String login=(String)config.getProperties().get("hibernate.connection.username");
		String password=(String)config.getProperties().get("hibernate.connection.password");
		
        Connection conn = DriverManager.getConnection(url, login, password );
        System.out.println("Connection:"+conn);
		return config.buildSessionFactory();
	}

}
