package bc.data_terminal.editor.database;
import java.sql.*;

import bc.data_terminal.editor.database.oracle.HibernateOracleConnect;



/** Singelton for HibernateOracleConnect*/
public class Connector {
	private static HibernateOracleConnect field_hibernate=null;
	
	private static void getHibernateOracleConnect(){
		if(field_hibernate==null){
			field_hibernate=new HibernateOracleConnect("jdbc:oracle:thin:@192.168.15.254:1521:demo",
					 "bc_reports",
					 "bc_reports",
					 1);
		}
	}
	
	public static Connection getConnection(){
		if(field_hibernate==null){
			getHibernateOracleConnect();
		}
		return field_hibernate.getConnection();
	}

	public static void closeConnection(Connection connection){
		if(field_hibernate!=null){
			field_hibernate.closeConnection(connection);
		}
	}
	
}
