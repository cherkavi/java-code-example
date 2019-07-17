package oracle_connection;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Types;

public class Connect {
	public static void main(String[] args){
		System.out.println("begin");
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
		    // Connection connection=DriverManager.getConnection("jdbc:oracle:thin:@91.195.53.27:1521:demo","bc_sms", "bc_sms");			
			// Connection connection=DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:XE","technik", "technik");
			Connection connection=DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:XE","system", "1");
			System.out.println("Connection OK: "+connection);
			CallableStatement statement=connection.prepareCall("{? = call get_partner_by_name(?)}");
			statement.registerOutParameter(1, Types.INTEGER);
			statement.setString(2, "localhost");
			statement.executeUpdate();
			Integer value=statement.getInt(1);
			System.out.println("Value:"+value);
			connection.close();
		}catch(Exception ex){
			System.err.println("Exception: "+ex.getMessage());
		}
		System.out.println("-end-");
	}
}
