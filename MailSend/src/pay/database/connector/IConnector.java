package pay.database.connector;
import java.sql.Connection;

public interface IConnector {
	/** �������� ���������� � ����� ������*/
	public Connection getConnection();
	
	/** ������� ��� ���������� � ����� ������ */
	public void closeAllConnection();
}
