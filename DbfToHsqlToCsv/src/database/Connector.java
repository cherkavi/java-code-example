package database;
import java.sql.Connection;

public abstract class Connector {

	/** получить соединение с базой данных */
	public abstract Connection getConnection();
}
