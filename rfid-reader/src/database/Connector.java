package database;

import java.sql.Connection;

import org.apache.log4j.Logger;

public class Connector {
	private Logger logger=Logger.getLogger(this.getClass());
	private String url;
	private String user;
	private String password;
	
	
	public Connector(String url, String user, String password){
		this.url=url;
		this.user=user;
		this.password=password;
	}
	
	private Connection connection;
	
	public Connection getConnection(){
		try{
			if((connection==null)||connection.isClosed()){
				connection=this.createConnection();
			}
			return connection;
		}catch(Exception ex){
			logger.error("getConnection Exception: "+ex.getMessage());
			return null;
		}
	}
	
	
	
	private Connection createConnection(){
		Connection returnValue=null;
		// INFO место создания соединения с базой данных
		if(this.url.indexOf("mysql")>0){
			try{
				Class.forName("org.gjt.mm.mysql.Driver");
				String path=this.url+"?user="+this.user+"&password="+this.password+"&useUnicode=true&characterEncoding=Cp1251";
				returnValue=java.sql.DriverManager.getConnection(path);
				returnValue.setAutoCommit(false);
			}catch(Exception ex){
				logger.error("create Connection(MySQL): Exception:"+ex.getMessage());
			}
		}
		if(this.url.indexOf("firebirdsql")>0){
			try{
	            //System.out.println("Попытка загрузить драйвер");
	            Class.forName("org.firebirdsql.jdbc.FBDriver");
	            //System.out.println("Попытка соеднинения="+databaseURL);
	            returnValue=java.sql.DriverManager.getConnection(url,user,password);
	            returnValue.setAutoCommit(false);
			}catch(Exception ex){
				logger.error("create Connection(Firebird): Exception:"+ex.getMessage());
			}
		}
		return returnValue;
	}
}
