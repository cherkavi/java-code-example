package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.hibernate.Session;

/** POJO класс, который содержит Connection и Session, а так же успешно закрывает их */
public class ConnectWrap {
	/** префикс схемы, который нужно использовать  */
	public static String schemePrefix=null;
	
	private Connection connection;
	private Session session;
	
	
	@SuppressWarnings("unused")
	private void loadSchemePrefix(Connection connection) {
		Statement statement=null;
		try{
			statement=connection.createStatement();
			ResultSet rs=statement.executeQuery("SELECT SYS_CONTEXT('bc', 'general_db_scheme') FROM dual");
			rs.next();
			schemePrefix=rs.getString(1)+".";
		}catch(Exception ex){
			System.err.println("loadSchemePrefix Exception:"+ex.getMessage());
		}finally{
			try{
				statement.close();
			}catch(Exception ex){};
		}
	}
	
	/** POJO класс, который содержит Connection и Session, а так же успешно закрывает их */
	public ConnectWrap(Connector connector){
		/** получить соединение из Pool-а */
		connection=connector.getConnection();
		/** создать по данному соединению HibernateSession */
		session=connector.openSession(connection);

		/*if(schemePrefix==null){
			loadSchemePrefix(connection);
		}*/
		
	}
	
	public ConnectWrap(Connection connection, Session session){
		this.connection=connection;
		this.session=session;

		/* if(schemePrefix==null){
			loadSchemePrefix(connection);
		}*/
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}
	
	public void close(){
		try{
			session.close();
			session=null;
		}catch(Exception ex){
		}
		try{
			connection.close();
			connection=null;
		}catch(Exception ex){
		}
	}

	public void finalize(){
		if((session!=null)||(connection!=null)){
			this.close();
		}
	}
}
