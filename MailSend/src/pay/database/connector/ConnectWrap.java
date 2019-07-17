package pay.database.connector;

import java.sql.Connection;

import org.hibernate.Session;

/** обертка для соединения с базой данных */
public class ConnectWrap {
	 private Connection connection;
	 private Session session;
	 
	 /** обертка для соединения с базой данных */
	 public ConnectWrap(Connection connection, Session session){
		 this.connection=connection;
		 this.session=session;
	 }

	 /** закрыть соединение с базой данных */
	 public void close(){
		 try{
			 session.close();
		 }catch(Exception ex){};
		 try{
			 connection.close();
		 }catch(Exception ex){};
	 }
	 
	/** получить Connection */
	public Connection getConnection() {
		return connection;
	}

	/** @return получить Session */
	public Session getSession() {
		return session;
	}

}
