package database.pool;
import java.sql.Connection;

/** ���������, ������� ��������� �������� ���������� �� ���� */
public interface IConnector {
	/** �������� ���������� � ����� ������*/
	public Connection getConnection();
	
	/** ������� ��� ���������� � ����� ������ */
	public void closeAllConnection();
}
