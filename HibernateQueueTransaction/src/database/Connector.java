package database;

import org.hibernate.Session;

/** Singelton */
public class Connector {
	private static HibernateFirebirdConnect connect=null;
	
	/** get Session from connection */
	public static Session getSession(){
		if(connect==null){
			connect=new HibernateFirebirdConnect("D:\\eclipse_workspace\\HibernateQueueTransaction\\DataBase\\test.gdb",
												"SYSDBA",
												"masterkey",
											     5, 
											     Action.class, 
											     Event.class, 
											     EventType.class);
		}
		try{
			return connect.getSession();
		}catch(Exception ex){
			System.out.println("Null Pointer Exception: "+ex.getMessage());
			return null;
		}
	}
	
	
}
