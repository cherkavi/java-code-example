package jdbc_migration.connection;

import java.sql.Connection;

/** ���������, ������� ������� �������� Connection � ����� ��� ������ �� ����������*/
public interface IConnectionAware {
	public Connection getConnection();
}
