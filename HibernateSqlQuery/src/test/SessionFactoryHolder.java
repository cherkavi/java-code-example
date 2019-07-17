package test;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionFactoryHolder {
	
	private static SessionFactory instance=null;
	
	/**
	 * get session Factory
	 * @return
	 */
	public static SessionFactory getSessionFactory(){
		if(instance==null){
			Configuration config=new Configuration();
			config.addResource("hibernate.cfg.xml");
			config.configure();
			instance=config.buildSessionFactory();
		}
		return instance;
	}
}
