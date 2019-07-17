package database;

import java.sql.Connection;
import java.sql.DriverManager;

public class Connector {
	public static String path="D:\\eclipse_workspace\\BatteryShop\\Database\\battery_shop.gdb";
	public static Connection getConnection(){
		try { // The newInstance() call is a work around for some // broken Java implementations 
			Class.forName("org.firebirdsql.jdbc.FBDriver").newInstance(); 
		} catch (Exception E) { 
			System.err.println("Unable to load driver."); E.printStackTrace(); 
		};
		try{
			return DriverManager.getConnection( "jdbc:firebirdsql://localhost:3050/"+path.replace('\\', '/').trim(), 
					   "SYSDBA", 
					   "masterkey"); 		
		}catch(Exception ex){
			return null;
		}
	};
}
