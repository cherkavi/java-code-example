import java.sql.*;

import database.HsqldbConnection;

public class EnterPoint {
	public static void main(String agrs[]){
		try{
			// получить Connection 
			Connection connection=(new HsqldbConnection("data")).getConnection();
			connection.setAutoCommit(true);
			// создать таблицу
			createTable(connection);
			// добавить данные в таблицу
			addRecord(connection);
			// получить данные из таблицы
			ResultSet rs=getResultSet(connection);
			while(rs.next()){
				for(int counter=1;counter<=rs.getMetaData().getColumnCount();counter++){
					System.out.print(rs.getObject(counter)+"   ");
				}
				System.out.println();
			}
		}catch(Exception ex){
			System.out.println("Exception: "+ex.getMessage());
		}
	}
	
	private static void createTable(Connection connection) throws SQLException {
		Statement statement=connection.createStatement();
		statement.executeUpdate("create table table_temp( kod integer, text_value varchar(50));");
		statement.close();
	}
	
	private static void addRecord(Connection connection)throws SQLException {
		Statement statement=connection.createStatement();
		statement.executeUpdate("insert into table_temp (kod, text_value) values(1,'value_1');");
		statement.executeUpdate("	insert into table_temp (kod, text_value) values(1,'value_1');	");
		statement.executeUpdate("	insert into table_temp (kod, text_value) values(2,'value_2');	");
		statement.executeUpdate("	insert into table_temp (kod, text_value) values(3,'value_3');	");
		statement.executeUpdate("	insert into table_temp (kod, text_value) values(4,'value_4');	");
		statement.executeUpdate("	insert into table_temp (kod, text_value) values(5,'value_5');	");
		statement.executeUpdate("	insert into table_temp (kod, text_value) values(6,'value_6');	");
		statement.executeUpdate("	insert into table_temp (kod, text_value) values(7,'value_7');	");
		statement.executeUpdate("	insert into table_temp (kod, text_value) values(8,'value_8');	");
		statement.executeUpdate("	insert into table_temp (kod, text_value) values(9,'value_9');	");
		statement.executeUpdate("	insert into table_temp (kod, text_value) values(10,'value_10');	");
		statement.executeUpdate("	insert into table_temp (kod, text_value) values(11,'value_11');	");
		statement.executeUpdate("	insert into table_temp (kod, text_value) values(12,'value_12');	");
		statement.executeUpdate("	insert into table_temp (kod, text_value) values(13,'value_13');	");
		statement.executeUpdate("	insert into table_temp (kod, text_value) values(14,'value_14');	");
		statement.executeUpdate("	insert into table_temp (kod, text_value) values(15,'value_15');	");
		statement.executeUpdate("	insert into table_temp (kod, text_value) values(16,'value_16');	");
		statement.close();
	}
	
	private static ResultSet getResultSet(Connection connection) throws SQLException {
		return connection.createStatement().executeQuery("select * from table_temp");
	}
}
