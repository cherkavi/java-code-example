package database;

import java.sql.Connection;
import java.sql.SQLException;

import org.hsqldb.jdbc.jdbcDataSource;

public class HsqldbConnection extends Connector{
	/** уникальное имя базы данных */
	private String name;
	/** соединение с базой данных HsqlDb*/
	private jdbcDataSource dataSource;
	/** current connection */
	private Connection connection;
	
	/** создать соединение с базой данных на основании HSQLDB базы данных 
	 * @param уникальное имя для базы данных 
	 * @throws SQLException если не удалось создать соединение с базой данных по указанному типу  
	 * */
	public HsqldbConnection(String databaseName) throws SQLException {
		this.name=databaseName;
        dataSource = new jdbcDataSource();

        dataSource.setDatabase("jdbc:hsqldb:" + this.name);

	}
	
	
	@Override
	public Connection getConnection() {
		try{
			if((this.connection==null)||(this.connection.isClosed())){
				this.connection=this.dataSource.getConnection("sa","");
			}
		}catch(Exception ex){
			return this.connection;
		}
		return connection;
	}

}
