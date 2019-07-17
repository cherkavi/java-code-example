package database.pool;

import java.sql.Connection;

import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDataSource;
import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.impl.GenericObjectPool;

import database.EConnectionType;

/** реализация интерфейся {@link IConnector} */
public class PoolConnection implements IConnector{
	//private FirebirdPoolConnect poolConnector;
	private PoolingDataSource dataSource;
	
	/** объект, который возвращает Connection к базе данных Firebird
	 * @param pathToServer - путь к серверу 
	 * @param pathToDatabase - путь к базе данных 
	 * @param userName - имя пользователя 
	 * @param password - пароль
	 * @param maxActiveConnection - кол-во соединений, который допустимы
	 */
	public PoolConnection(EConnectionType connectionType,
						  String host, 
						  int port, 
						  String databaseName,
						  String userName, 
						  String password,
						  int maxActiveConnection){
		this.dataSource=this.getPoolDataSource(connectionType.getDriverName(),
											   connectionType.getConnectionString(host, port, databaseName), 
											   userName, 
											   password, 
											   maxActiveConnection);
		/*this.poolConnector=new FirebirdPoolConnect(this.getDatabaseUrl(),
												 this.userName, 
												 this.password, 
												 10);*/
	}
	
	@Override
	public Connection getConnection(){
		try{
			return this.dataSource.getConnection();
		}catch(SQLException ex){
			System.err.println("FirebirdConnection#getConnection: "+ex.getMessage());
			return null;
		}
		 
	}
	
	
	@Override
	public void closeAllConnection() {
		//this.poolConnector.close();
		/*try{
	        PoolingDriver driver = (PoolingDriver) DriverManager.getDriver("jdbc:apache:commons:dbcp:");
	        driver.closePool("BonPay");
		}catch(Exception ex){
			System.err.println("FirebridConnection#closeAllConnection:"+ex.getMessage());
		}*/
	}

	
	private PoolingDataSource getPoolDataSource(String driverClassName,
												String connectURI, 
												String user, 
												String password, 
												int maxActiveConnection) {
		try{
			Class.forName(driverClassName);
		}catch(Exception ex){
			System.err.println("Connection#getPoolDataSource Exception:"+ex.getMessage());
		}
		
        //
        // First, we'll need a ObjectPool that serves as the
        // actual pool of connections.
        //
        // We'll use a GenericObjectPool instance, although
        // any ObjectPool implementation will suffice.
        //
        ObjectPool connectionPool = new GenericObjectPool(null,maxActiveConnection);
        /*
        				Integer.parseInt( props.getProperty(Environment.DBCP_MAXACTIVE) ), 
                        Byte.parseByte( props.getProperty(Environment.DBCP_WHENEXHAUSTED) ),
                        Long.parseLong( props.getProperty(Environment.DBCP_MAXWAIT) ),
                        Integer.parseInt( props.getProperty(Environment.DBCP_MAXIDLE) )
		*/
        //
        // Next, we'll create a ConnectionFactory that the
        // pool will use to create Connections.
        // We'll use the DriverManagerConnectionFactory,
        // using the connect string passed in the command line
        // arguments.
        //
        Properties properties=new Properties();
        properties.put("user", user);
        properties.put("password", password);
        
        ConnectionFactory connectionFactory = new DriverManagerConnectionFactory(connectURI,
        																		 properties);
        // ConnectionFactory connectionFactory = new DriverManagerConnectionFactory(connectURI,
        //																		    userName,
        //																		    password,
		//		 																	null);

        //
        // Now we'll create the PoolableConnectionFactory, which wraps
        // the "real" Connections created by the ConnectionFactory with
        // the classes that implement the pooling functionality.
        //
        @SuppressWarnings("unused")
		PoolableConnectionFactory poolableConnectionFactory = new PoolableConnectionFactory(connectionFactory,
        																				    connectionPool,
        																				    null,
        																				    null,
        																				    false,// readOnly
        																				    false// autocommit
        																				    );

        //
        // Finally, we create the PoolingDriver itself,
        // passing in the object pool we created.
        //
        PoolingDataSource dataSource = new PoolingDataSource(connectionPool);

        // For close
        /*
        try{
            PoolingDriver driver = (PoolingDriver) DriverManager.getDriver("jdbc:apache:commons:dbcp:");
            driver.registerPool("BonPay",connectionPool);
        }catch(Exception ex){};
		*/
        
        return dataSource;
    }

	
/*	public static void main(String[] args) throws Exception{
		String dataBaseName="D:/eclipse_workspace/BonPay/DataBase/bonpay.gdb";
		Class.forName("org.firebirdsql.jdbc.FBDriver");
		FirebirdConnection connector=new FirebirdConnection(null,dataBaseName,"SYSDBA","masterkey",20);
		connector.getConnection();
		System.out.println(connector.getConnection());
	}
*/
}



/*	
private Connection getConnectionToDatabase() {
	java.sql.Connection connection=null;
	String databaseURL=this.getDatabaseUrl();
	try{
    	//System.out.println("Попытка загрузить драйвер");
    	Class.forName(driverName);
    	//System.out.println("Попытка соеднинения="+databaseURL);
    	connection=java.sql.DriverManager.getConnection(databaseURL,userName,password);
    	connection.setAutoCommit(false);
	}catch(SQLException sqlexception){
    	System.err.println("не удалось подключиться к базе данных");
	}catch(ClassNotFoundException classnotfoundexception){
    	System.err.println("не найден класс");
	}
	return connection;
}
*/
