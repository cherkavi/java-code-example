package database.connector;
import java.sql.Connection;

public interface IConnector {
	/** получить соединение с базой данных*/
	public Connection getConnection();
	/** получить новое соединение с базой данных */
	public Connection getNewConnection();
}
