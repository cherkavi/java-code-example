package database.connector;
import java.sql.Connection;

public interface IConnector {
	/** �������� ���������� � ����� ������*/
	public Connection getConnection();
	/** �������� ����� ���������� � ����� ������ */
	public Connection getNewConnection();
}
