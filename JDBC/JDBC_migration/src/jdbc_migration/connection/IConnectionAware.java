package jdbc_migration.connection;

import java.sql.Connection;

/** интерфейс, который владеет объектом Connection и может его выдать по требованию*/
public interface IConnectionAware {
	public Connection getConnection();
}
