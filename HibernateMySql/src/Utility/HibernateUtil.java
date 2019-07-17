package Utility;
import org.hibernate.*;
import org.hibernate.cfg.*;

import DataBase.*;

public class HibernateUtil {
	private static SessionFactory sessionFactory;
	  static {
	    try {
	    	/*
	    	 *	configuration = new Configuration()
				.addClass(mst.hrd.model.hibernate.hbmxml.T_Karyawan_Ms.class)
				.setProperty("hibernate.dialect","org.hibernate.dialect.MySQLInnoDBDialect")
				.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver")
				.setProperty("hibernate.connection.url", "jdbc:mysql://"+var.host+ "/"+var.dbGlobal+"")
				.setProperty("hibernate.connection.username", user)
				.setProperty("hibernate.connection.password", pwd);
				}  
	    	 */
	    	
	    	AnnotationConfiguration cfg=new AnnotationConfiguration();
	    	cfg.addAnnotatedClass(Table_two.class);
	    	cfg.addAnnotatedClass(Table_one.class);
	    	
	    	cfg.addPackage("DataBase");
	       sessionFactory=new Configuration().configure().buildSessionFactory();
	    } catch (Throwable ex) {
	       throw new ExceptionInInitializerError(ex);
	    }
	  }
	  public static SessionFactory getSessionFactory() {
	      // Alternatively, you could look up in JNDI here
	      return sessionFactory;
	  }
	  public static void shutdown() {
	      // Close caches and connection pools
	      getSessionFactory().close();
	  }
}
