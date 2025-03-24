package jdbc_migration.container;

import java.io.PrintWriter;
import java.sql.Connection;

/** интерфейс, который содержит управляющие методы для PanelElement */
public interface IPanelContainerManager {
	/** удалить из контейнера указанный элемент */
	public void removeElement(PanelElement element);
	/** данный объект владеет потоком, в который нужно проводить экспорт данных */
	public PrintWriter getExportWriter();
	/** получить соединение с базой данных, из которой осуществляется импортирование */
	public Connection getImportConnection();
	
	/** сообщить о необходимости получения Export Writer*/
	public PrintWriter getExportWriterNew();
	
	/** сообщить о необходимости закрытия Export Writer */
	public void closeExportWriter();
	/** соединение с базой данных, в которое заливаются данные */
	public Connection getExportConnection();
}
