package bc.connection;

import java.io.PrintStream;
import java.sql.*;


public class Connector {
	private static SessionPoolConnector poolConnector;
	static{
		// TODO set realize class here
		//poolConnector=new SessionPoolConnector(FirebirdConnect.class,new Integer(10));
		poolConnector=new SessionPoolConnector(HibernateOracleConnect.class,new Integer(10));
	}
	/** Получить Connection из POOL по указанному сессионному идентификатору 
	 * @param sessionId - уникальный номер сессии, по которому нужно получить Connection
	 * */
	public synchronized static Connection getConnection(String sessionId){
		return poolConnector.getConnection(sessionId);
	}
	

	/** получить Connection на основании введенных данных 
	 * @param userName имя пользователя
	 * @param password пароль
	 * @param sessionId уникальный номер сессии 
	 * @return Connection 
	 * */
	public synchronized static Connection getConnection(String userName, 
										   String password, 
										   String sessionId){
		return poolConnector.getConnection(userName, 
										   password, 
										   sessionId);
	}

	/** вернуть Connection в POOL */
	public synchronized static void closeConnection(Connection connection){
		poolConnector.closeConnection(connection);
	}
	
	/** удалить номер сессии из связки sessionId=UserName*/
	public static void removeSessionId(String sessionId){
		poolConnector.removeSessionId(sessionId);
	}
	
	/** вывести в PrintStream состояние всех соединений */
	public static void printAllConnectionCount(PrintStream out){
		poolConnector.printConnection(out);
	}
	
	/** удалить по указанному пользователю все соединения с базой*/
	public static boolean dropUserConnection(String userName){
		return poolConnector.removeSessionByUser(userName);
	}
}
