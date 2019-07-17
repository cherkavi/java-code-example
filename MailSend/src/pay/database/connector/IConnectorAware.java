package pay.database.connector;

import java.sql.Connection;

/** интерфейс, ведающий соединениями с базой данных */
public interface IConnectorAware {
	/** получить соединение с базой данных */
	public Connection getConnection();
}
