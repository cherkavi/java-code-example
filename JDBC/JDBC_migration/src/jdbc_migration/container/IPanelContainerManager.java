package jdbc_migration.container;

import java.io.PrintWriter;
import java.sql.Connection;

/** ���������, ������� �������� ����������� ������ ��� PanelElement */
public interface IPanelContainerManager {
	/** ������� �� ���������� ��������� ������� */
	public void removeElement(PanelElement element);
	/** ������ ������ ������� �������, � ������� ����� ��������� ������� ������ */
	public PrintWriter getExportWriter();
	/** �������� ���������� � ����� ������, �� ������� �������������� �������������� */
	public Connection getImportConnection();
	
	/** �������� � ������������� ��������� Export Writer*/
	public PrintWriter getExportWriterNew();
	
	/** �������� � ������������� �������� Export Writer */
	public void closeExportWriter();
	/** ���������� � ����� ������, � ������� ���������� ������ */
	public Connection getExportConnection();
}
