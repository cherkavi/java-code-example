package database.connector;
import org.hibernate.Session;

import java.sql.Connection;

/** класс, который производит соединение с базой данных, посредстом Hibernate и Connection */
public class Connector {
	private static IConnector connector=null;
	private static HibernateConnection hibernateConnection=null;
	
	private static String userName="SYSDBA";
	private static String password="masterkey";
	
	// INFO - место присоединения всех классов для активации отображения объектов базы данных на объекты программы
	private static Class<?>[] classOfDatabase=new Class[]{};
	
	static {
		try{
			String dataBaseName="D:\\eclipse_workspace\\BonPay\\DataBase\\bonpay.gdb";
			connector=new FirebirdConnection(null,dataBaseName,userName,password,20);
			hibernateConnection=new HibernateConnection(connector,
													    "org.hibernate.dialect.FirebirdDialect",
													    classOfDatabase);
			
		}catch(Exception ex){
			System.err.println("Connector is not created ");
		}
	}
	
	/** получить Hibernate Session */
	public static Session openSession(Connection connection){
		return hibernateConnection.openSession(connection);
	}
	
	/** получить соединение с базой данных */
	public static Connection getConnection(){
		return connector.getConnection();
	}
	
	/** закрыть Hibernate сессию */
/*	public static void closeSession(Session session){
		try{
			session.disconnect();
			session.close();
		}catch(Exception ex){
			System.out.println("Connector#closeSession Exception: "+ex.getMessage());
		};
	}
*/	
	public static void closeSession(Session session, Connection connection){
		try{
			session.disconnect();
		}catch(Exception ex){
			System.out.println("Connector#disconnectSession Exception: "+ex.getMessage());
		};
		try{
			session.close();
		}catch(Exception ex){
			System.out.println("Connector#closeSession Exception: "+ex.getMessage());
		};
		try{
			connection.close();
		}catch(Exception ex){
			System.out.println("Connector#closeConnection Exception: "+ex.getMessage());
		};
	}
	
}
