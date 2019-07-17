package database;

import org.hibernate.Session;

import database.firebird.HibernateFirebirdConnect;
import database.wrap.ClientState;
import database.wrap.Clients;
import database.wrap.Satellite;

/** класс, который выдает Session */
public class Connector {
	private static HibernateFirebirdConnect connect=null;
	static{
		try{
			//Loader loader=new Loader("c:\\settings.xml");
			//connect=new HibernateFirebirdConnect(loader.getString("//SETTINGS/PATH_TO_DATABASE", "").trim(), "SYSDBA", "masterkey", 50);
			connect=new HibernateFirebirdConnect("D:\\eclipse_workspace\\Hibernate_Research\\simple_database\\BONPAY.GDB ", 
												 "SYSDBA", 
												 "masterkey", 
												 50,
												 Clients.class,
												 ClientState.class,
												 Satellite.class);
		}catch(Exception ex){
			System.out.println("File settings.xml is not found: "+ex.getMessage());
		}
	}
	
	/** Get Session from Pool */
	public static Session getSession(){
		return connect.getSession();
	}
	
	/** close Session */
	public static void closeSession(Session session){
		if(session!=null){
			try{
				session.close();
			}catch(Exception ex){
				
			}
		}
	}
	
	/** close Connector */
	public static void close(){
		connect.close();
	}
}
