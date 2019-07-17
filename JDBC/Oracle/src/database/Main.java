package database;

import java.sql.Connection;
import java.sql.ResultSet;

public class Main {
	public static void main(String[] args){
		System.out.println("begin");
		Connection connection=null;
		try{
			connection=OracleConnection.getConnection("127.0.0.1",1521,"XE","technik","technik");
			ResultSet rs=connection.createStatement().executeQuery("select * from one");
			int columnCount=rs.getMetaData().getColumnCount();
			while(rs.next()){
				for(int counter=1;counter<=columnCount;counter++){
					System.out.print(counter+" : "+rs.getString(counter));
				}
				System.out.println();
			}
			System.out.println("");
		}catch(Exception ex){
			System.err.println("Exception: "+ex.getMessage());
		}finally{
			try{
				connection.close();
			}catch(Exception ex){};
		}
		System.out.println("-end-");
	}
}
