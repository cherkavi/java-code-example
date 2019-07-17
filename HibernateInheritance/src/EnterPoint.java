import java.util.List;

import org.apache.log4j.BasicConfigurator;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import database.Boy;
import database.Girl;
import database.Parent;

public class EnterPoint {
	public static void main(String[] args) throws Exception{
		BasicConfigurator.configure();
		
		Configuration config=new Configuration();
		config.configure("hibernate.cfg.xml");
		SessionFactory sessions=config.buildSessionFactory();
		Session session=sessions.openSession();
		
		Girl girl=new Girl();
		girl.setId(1);
		girl.setName("Olga");
		girl.setGirlSkills("nitting");
		
		Boy boy=new Boy();
		boy.setId(1);
		boy.setName("Kolya");
		boy.setBoySkills("struglle");
		
		session.beginTransaction();
		session.save(boy);
		session.save(girl);
		session.getTransaction().commit();
		
		session.close();
		
		session=sessions.openSession();
		List<Parent> parentList=session.createCriteria(Parent.class).list();
		for(Parent value:parentList){
			System.out.println("Value:"+value.toString());
		}
		session.close();
		sessions.close();
	}
}
