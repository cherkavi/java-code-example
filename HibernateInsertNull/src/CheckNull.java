import java.util.Date;

import org.hibernate.Session;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

import tables.Temp;


public class CheckNull {
	public static void main(String[] args){
		Configuration cfg = new AnnotationConfiguration()
			.addAnnotatedClass(Temp.class)
			.setProperty("hibernate.dialect", "org.hibernate.dialect.OracleDialect")
			.setProperty("hibernate.connection.driver_class", "oracle.jdbc.driver.OracleDriver")
			.setProperty("hibernate.connection.url", "jdbc:oracle:thin:@127.0.0.1:1521:XE")
			.setProperty("hibernate.connection.username", "technik")
			.setProperty("hibernate.connection.password", "technik")
			;		
		Session session=cfg.buildSessionFactory().openSession();
		
		// readAndSaveValue(session);
		createAndSaveValue(session);
	}

	private static void createAndSaveValue(Session session){
		Temp value=new Temp();
		// value.setId(10);
		value.setName("created name");
		value.setTimeStamp(new Date());
		System.out.println("Readed object:"+value);
		
		session.beginTransaction();
		session.save(value);
		value.setTimeStamp(new Date());
		session.getTransaction().commit();
		System.out.println("Saved (1) object:"+value);
		
		session.beginTransaction();
		value.setTimeStamp(null);
		session.update(value);
		session.getTransaction().commit();
		System.out.println("Saved (2) object:"+value);
		
		value=(Temp)session.createCriteria(Temp.class).add(Restrictions.eq("id", value.getId())).uniqueResult();
		System.out.println("Readed object:"+value);
		
		/*
		 * 
Readed object:Temp [id=null, name=created name, timeStamp=Wed Sep 28 22:22:04 EEST 2011]
Saved (1) object:Temp [id=200, name=created name, timeStamp=Wed Sep 28 22:22:04 EEST 2011]
Saved (2) object:Temp [id=200, name=created name, timeStamp=null]
Readed object:Temp [id=200, name=created name, timeStamp=null]		 */
	}
	
	
	private static void readAndSaveValue(Session session){
		Temp value=(Temp)session.createCriteria(Temp.class).add(Restrictions.eq("id", 4)).uniqueResult();
		System.out.println("Readed object:"+value);
		
		session.beginTransaction();
		value.setTimeStamp(new Date());
		session.getTransaction().commit();
		System.out.println("Saved (1) object:"+value);
		
		session.beginTransaction();
		value.setTimeStamp(null);
		session.merge(value);
		session.getTransaction().commit();
		System.out.println("Saved (2) object:"+value);
		
		value=(Temp)session.createCriteria(Temp.class).add(Restrictions.eq("id", 4)).uniqueResult();
		System.out.println("Readed object:"+value);
		
		/*
		 * 
Readed object:Temp [id=4, name=4:value, timeStamp=null]
Saved (1) object:Temp [id=4, name=4:value, timeStamp=Wed Sep 28 22:13:36 EEST 2011]
Saved (2) object:Temp [id=4, name=4:value, timeStamp=null]
Readed object:Temp [id=4, name=4:value, timeStamp=null]		 */
	}
}
