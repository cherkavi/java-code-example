package database;
import org.hibernate.Session;

import database.hibernate.HibernateConnection;
import database.pool.IConnector;
import database.pool.PoolConnection;

import java.sql.Connection;

/** �����, ������� ���������� ���������� � ����� ������, ���������� Hibernate � Connection 
 * */
public class Connector {
	/** ���������� � ��������� ����� ������ */
	private HibernateConnection hibernateConnection=null;
	
	private static Connector connector=null;
	
	public static void init(EConnectionType connectionType,
							String host,
							int port,
							String databaseName,
							String userName, 
							String password, 
							int poolSize,
							Class<?>[] classOfDatabase) throws Exception{
			connector=new Connector(connectionType, 
									 host, 
									 port, 
									 databaseName, 
									 userName, 
									 password, 
									 poolSize, 
									 classOfDatabase);
			
	}
	/*public static ReportConnector getInstance() throws Exception{
		return new ReportConnector();
	}*/
	public static Connector getInstance(){
		return connector;
	}
	

/*	public static Connector getInstance(EConnectionType connectionType,
										String userName, 
										String password, 
										int poolSize,
			Class<?>[] classOfDatabase) throws Exception{
	return new Connector(connectionType, 
						 "", 
						 0, 
						 "", 
						 userName, 
						 password, 
						 poolSize, 
						 classOfDatabase);
	}
*/
	/*private ReportConnector() throws Exception{
		//this("D:\\eclipse_workspace\\OfficePrivate\\DataBase\\office_private.gdb","SYSDBA","masterkey",10);
		this("messenger","SYSDBA","masterkey",10);
	}*/

	
	/**  
	 * @param pathToDataBase - ���� � ���� ������ (jdbc:oracle:thin:@91.195.53.27:1521:demo) 
	 * @param userName - ��� ������������ 
	 * @param password - ������ 
	 * @param poolSize - ������ ���� 
	 * @throws Exception - ���� �� ������� ������� Connector  
	 */
	private Connector(EConnectionType connectionType,
					  String host, 
					  int port, 
					  String databaseName,
					  String userName, 
					  String password, 
					  int poolSize,
					  Class<?>[] classOfDatabase) throws Exception {

		IConnector connector=new PoolConnection(connectionType,host,port,databaseName, userName,password,poolSize);
		
		hibernateConnection=new HibernateConnection(connector,
												    connectionType.getDriverName(),
												    connectionType.getHibernateDialect(),
												    classOfDatabase);
		// IConnector connector=new FirebirdConnection(null, pathToDataBase,userName,password,poolSize);
		/*hibernateConnection=new HibernateConnection(connector,
													"org.firebirdsql.jdbc.FBDriver",
												    "org.hibernate.dialect.FirebirdDialect",
												    classOfDatabase);
		*/
	}
	
	
	/** �������� Hibernate Session */
	public Session openSession(Connection connection){
		return hibernateConnection.openSession(connection);
	}
	
	/** �������� ���������� � ����� ������ */
	public Connection getConnection(){
		return hibernateConnection.getConnection(); 
	}
	
	/** ������� Hibernate ������ */
/*	public static void closeSession(Session session){
		try{
			session.disconnect();
			session.close();
		}catch(Exception ex){
			System.out.println("Connector#closeSession Exception: "+ex.getMessage());
		};
	}
*/	
	public void closeSession(Session session, Connection connection){
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
