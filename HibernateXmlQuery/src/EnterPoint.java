import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.BasicConfigurator;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


import database.TableOne;


public class EnterPoint {
	@SuppressWarnings("unchecked")
	public static void main(String[] args){
		BasicConfigurator.configure();
		debug("button_connect:begin");
		try{
			Configuration config=new Configuration();
			config.configure("hibernate.cfg.xml");
			SessionFactory sessions=config.buildSessionFactory();
			Session session=sessions.openSession();
			// print named parameters 
			System.out.println(ArrayUtils.toString(session.getNamedQuery("test_query").getNamedParameters()));
			// get list of values 
			
			List<Object[]> list=session.getNamedQuery("test_query")
										.setString("field_two_condition", "one")
										.list();
			for(Object[] value:list){
				System.out.println(Arrays.toString(value));
			}
			
			List<TableOne> list2=(List<TableOne>)session.getNamedQuery("test_query_two")
									  .setString(0, "three")
									  .list();
			for(TableOne value:list2){
				System.out.println(value);
			}
			
			session.close();
			sessions.close();
		}catch(Exception ex){
			error("IOException: "+ex.getMessage());
		}
	}
	
	private static void debug(String message){
		System.out.println(message);
	}

	private static void error(String message){
		System.err.println(message);
	}
}
