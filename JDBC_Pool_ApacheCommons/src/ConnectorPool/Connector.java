package ConnectorPool;
import java.sql.*;

/** Singelton for Connection to DataBase*/
public class Connector {
	private static ConnectorPool connector;
	private static String driverName="org.firebirdsql.jdbc.FBDriver";
	private static String fullPath="jdbc:firebirdsql://localhost:3050/D:/eclipse_workspace/InternetShop/Information/DataBase/server_data.GDB?sql_dialect=3";
	
	private static void outError(Object information){
		System.out.print("Connector");
		System.out.print(" ERROR " );
		System.out.println(information);
	}
	
	public static Connection getConnection(){
		try{
			return connector.getConnection();
		}catch(Exception ex){
			outError("Connector: "+ex.getMessage());
			return null;
		}
	}

	public static Connection getConnection(String userName, String password){
		if(connector==null){
			try{
				connector=new ConnectorPool(driverName, fullPath, userName, password);
			}catch(Exception ex){
				outError("getConnection: "+ex.getMessage());
			}
		}
		return getConnection();
	}
}
