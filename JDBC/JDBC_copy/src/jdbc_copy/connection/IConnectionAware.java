package jdbc_copy.connection;

import java.sql.Connection;

/** ���������, ������� ������� �������� Connection � ����� ��� ������ �� ����������*/
public interface IConnectionAware {
	public Connection getConnection();
}
