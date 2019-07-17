package executor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import mapping.TableOne;

import org.apache.log4j.BasicConfigurator;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
public class EnterPoint {
	@SuppressWarnings("unchecked")
	public static void main(String[] args){
		BasicConfigurator.configure();
		
		System.out.println("begin");
		SessionFactory sessionFactory=new Configuration().configure().buildSessionFactory();
		Session session=sessionFactory.openSession();
		List<TableOne> listOfTables=(List<TableOne>)session.createCriteria(TableOne.class).add(Restrictions.eq("description", "descr_3")).list();
		// List<TableOneInnerJoinTableTwo> listOfTables=(List<TableOneInnerJoinTableTwo>)session.createCriteria(TableOneInnerJoinTableTwo.class).list();
		System.out.println("Table size: "+listOfTables.size());
		for(TableOne table:listOfTables){
			System.out.println(table);
		}
		/*
		session.beginTransaction();
		TableOne value=new TableOne();
		value.setId_table_two(3);
		String timestamp=new SimpleDateFormat("HH:mm:ss").format(new Date());
		value.setDescription("this is temp description:"+timestamp);
		value.setName("temp name "+timestamp);
		session.merge(value);
		session.flush();
		session.getTransaction().commit();
		*/
		System.out.println("end");
	}
	
	
}
