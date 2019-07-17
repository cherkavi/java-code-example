package database.connector;

import java.sql.DriverManager;

abstract class Connector {
	/** получить имя класса-драйвера */
	protected abstract String getFullClassName();
	/** получить полный URL к файлу  */
	protected abstract String getUrl();
	/** получить Login*/
	protected abstract String getLogin();
	/** получить Password*/
	protected abstract String getPassword();
	
	private java.sql.Connection currentConnection=null;
	
	/** получить соединение с базой данных, на основании переопределенных в потомках методов
	 * получения имени класса и полного URL
	 * */
	public java.sql.Connection getConnection() throws Exception {
		if((currentConnection==null)||(currentConnection.isClosed())){
			Class.forName(this.getFullClassName());
			currentConnection=DriverManager.getConnection(this.getUrl(),this.getLogin(),this.getPassword());
			currentConnection.setAutoCommit(false);
		}
		return currentConnection;
	}
}
