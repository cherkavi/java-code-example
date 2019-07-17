import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;


public class EnterPoint {
	public static void main(String[] args){
		System.out.println("Connect to database ");
		Connection connection=connector.get_connection_to_firebird("","D:/Computer_shop/Program/Delphi7/Server/DataBase/server_data.gdb", 3050, "SYSDBA", "masterkey");
		System.out.println("Connection:"+connection);
		System.out.println("Get ResultSet ");
		ResultSet rs=getResultSet(connection,"select * from people");
		System.out.println("Result:");
		try{
			writeTableToWriterFromResultSet(rs,new PrintWriter(System.out));
		}catch(IOException ioex){
			System.err.println("IOException: "+ioex.getMessage());
		}catch(SQLException sqlex){
			System.err.println("IOException: "+sqlex.getMessage());
		}
		
	}
	
	/** 
	 * @param rs - ResultSet data 
	 * @param out - for output data
	 * */
	private static void writeTableToWriterFromResultSet(ResultSet rs, Writer out) throws SQLException, IOException{
		// вывести заголовок
		ResultSetMetaData metaData=rs.getMetaData();
		out.write("<table>");
		out.write("	<thead>");
		out.write("<tr>");
		int columnCount=metaData.getColumnCount();
		for(int counter=0;counter<columnCount;counter++){
			out.write("<th>");
			out.write(metaData.getColumnLabel(counter+1));
			out.write("</th>");
		}
		out.write("</tr>");
		out.flush();
		out.write("	</thead>");
		out.write("	<tbody>");
		while(rs.next()){
			out.write("<tr>");
			for(int counter=0;counter<columnCount;counter++){
				out.write("<td>");
				try{
					out.write(rs.getString(counter+1));
				}catch(Exception ex){
					out.write("");
				}
				out.write("</td>");
			}
			out.write("</tr>");
			out.flush();
		}
		out.write("	</tbody>");
		out.write("</table>");
		metaData.getColumnCount();
		// вывести данные
		
	}
	
	private static ResultSet getResultSet(Connection connection,String query){
		ResultSet returnValue=null;
		try{
			returnValue=connection.createStatement().executeQuery(query);
		}catch(SQLException sqle){
			System.err.println("getResultSet Exception:"+sqle.getMessage());
		}
		return returnValue;
	}
}
