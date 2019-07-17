package jdbc_migration.container;

import java.io.File;
import java.sql.Connection;

/** интерфейс, который содержит необходимые данные для экспорта и/или импорта */
public interface IExportImport {
	public Connection getImportConnection();
	public File getExportFile();
	public Connection getExportConnection();
}
