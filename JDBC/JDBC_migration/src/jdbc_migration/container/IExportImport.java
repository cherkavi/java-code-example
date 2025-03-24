package jdbc_migration.container;

import java.io.File;
import java.sql.Connection;

/** ���������, ������� �������� ����������� ������ ��� �������� �/��� ������� */
public interface IExportImport {
	public Connection getImportConnection();
	public File getExportFile();
	public Connection getExportConnection();
}
