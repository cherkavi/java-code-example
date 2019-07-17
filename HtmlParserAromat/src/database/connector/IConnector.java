package database.connector;
import java.sql.Connection;

public interface IConnector {
	/** получить соединение с базой данных*/
	public Connection getConnection();
	
	/** закрыть все соединения с базой данных */
	public void closeAllConnection();
}
