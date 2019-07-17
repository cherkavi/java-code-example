import java.sql.DriverManager;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.PoolingDataSource;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolingDriver;



/** пример использования в качестве пула соединений с базой данных ApacheDPСP Framework */
public class ApacheDbCp {
	public static void main(String args[]) throws Exception {
		System.out.println("begin");
		DataSource simpleDataSource=getBasicDataSource();
		System.out.println("Simple connection with DataBase: "+simpleDataSource.getConnection());
		
		DataSource poolDataSource=getPoolDataSource(getDatabaseUrl(),"SYSDBA","masterkey");
		for(int counter=0;counter<15;counter++){
			System.out.println("get next connection:"+poolDataSource.getConnection());
		}
		shutdownDriver();
		System.out.println("end");
	}
	
	
	private static DataSource getBasicDataSource(){
		BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName("org.firebirdsql.jdbc.FBDriver");
        ds.setUsername("SYSDBA");
        ds.setPassword("masterkey");
        ds.setUrl(getDatabaseUrl());
        return ds;
	}
	
	private static String getDatabaseUrl(){
		return "jdbc:firebirdsql://localhost:3050/D:/eclipse_workspace/BonPay/DataBase/bonpay.gdb?sql_dialect=3";
	}
	
	
	public static DataSource getPoolDataSource(String connectURI, String user, String password) {
        //
        // First, we'll need a ObjectPool that serves as the
        // actual pool of connections.
        //
        // We'll use a GenericObjectPool instance, although
        // any ObjectPool implementation will suffice.
        //
        ObjectPool connectionPool = new GenericObjectPool(null,20);
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
        PoolableConnectionFactory poolableConnectionFactory = new PoolableConnectionFactory(connectionFactory,
        																				    connectionPool,
        																				    null,
        																				    null,
        																				    false,// readOnly
        																				    true// autocommit
        																				    );

        //
        // Finally, we create the PoolingDriver itself,
        // passing in the object pool we created.
        //
        PoolingDataSource dataSource = new PoolingDataSource(connectionPool);

        // For close/shutdown driver
        try{
            PoolingDriver driver = (PoolingDriver) DriverManager.getDriver("org.firebirdsql.jdbc.FBDriver");
            driver.registerPool("",connectionPool);
        }catch(Exception ex){
        };
        return dataSource;
    }
	
	
	public static void shutdownDriver(){
        try{
    		PoolingDriver driver = (PoolingDriver) DriverManager.getDriver("org.firebirdsql.jdbc.FBDriver");
            driver.closePool("");
        }catch(Exception ex){
        	System.err.println("closeAllConnection:"+ex.getMessage());
        }
    }
	
	
}
