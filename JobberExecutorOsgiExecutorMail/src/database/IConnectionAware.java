package database;

import java.sql.Connection;

/** выдача Connection к базе данных */
public interface IConnectionAware {
	/** получение соединения с базой данных */
	public Connection getConnection();
}
