package excel_to_hsql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

import org.hsqldb.Server;

import excel_to_hsql.reader.Column;

public class HsqlDbWriter {
	private Connection connection;
	private Server hsqlServer = null;
	
	public static String generateUrlMemory(String dbName){
		return "jdbc:hsqldb:file:mem:testdb";
	}
	
	public static String generateUrlFile(String pathToFile){
		return "jdbc:hsqldb:file:"+pathToFile;
	}
	
	/**
	 * 
	 * @param url "jdbc:hsqldb:hsql://localhost/xdb"
	 */
	public HsqlDbWriter(String url) throws Exception{ 
		String login="sa";
		String password="";
		try{
			hsqlServer=new Server();
		    // HSQLDB prints out a lot of informations when
		    // starting and closing, which we don't need now.
		    // Normally you should point the setLogWriter
		    // to some Writer object that could store the logs.
		    // hsqlServer.setLogWriter(null);
		    // hsqlServer.setSilent(true);

		    // Start the database!
		    hsqlServer.start();

		    // Getting a connection to the newly started database
	        Class.forName("org.hsqldb.jdbcDriver");
	        // Default user of the HSQLDB is 'sa'
	        // with an empty password
	        connection = DriverManager.getConnection(url, login, password);
		}catch(Exception ex){
			throw new Exception("can't open the URL:"+url);
		}
		hsqlServer = new Server();
	}
	
	
	public void shutdown(){
		try{
			this.connection.close();
		}catch(Exception ex){
			this.hsqlServer.stop();
		}
	}

	public void createTable(String tableName, List<Column> columns) throws Exception {
		Statement statement=this.connection.createStatement();
		
		try{
			statement.executeUpdate("drop table "+tableName+";");
		}catch(Exception ex){}
		
		StringBuilder sql=new StringBuilder();
		sql.append(" create table "+tableName+"( ");
		for(int index=0;index<columns.size();index++){
			if(index!=0){
				sql.append(", ");
			}
			sql.append(columns.get(index).getColumnName()+" varchar(100) ");
			sql.append("\n");
		}
		sql.append(");");
		statement.executeUpdate(sql.toString());
		statement.close();
	}

	public void write(String tableName, String[] next) throws Exception {
		StringBuilder sql=new StringBuilder();
		sql.append("insert into "+tableName+"  values( ");
		for(int index=0;index<next.length;index++){
			if(index!=0){
				sql.append(", ");
			}
			sql.append("?\n");
		}
		sql.append(" )");
		PreparedStatement statement=this.connection.prepareStatement(sql.toString());
		// this.logger.debug(sql);
		for(int index=0;index<next.length;index++){
			statement.setString(index+1, next[index]);
		}
		statement.executeUpdate();
		statement.close();
		this.connection.commit();
	}

	public Connection getConnection() {
		return this.connection;
	}
	
	
	
}