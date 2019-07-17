import java.sql.*;

/** класс для создания и получения соединения с базой
 * используется во время отладки 
 * */
public class ConnectorFirebird {
	    Connection field_connection=null;
		private void debug(String information){
			System.out.println("Connector: "+information);
		}
		private void error(String information){
			System.out.println("Connector:  "+information);
		}
	    
	    
	    public ConnectorFirebird(String path_to_database) throws SQLException{
	        debug("try connecting to base");
	        if(connect_to_database(path_to_database)==false){
	            error("error in open database");
	            throw new SQLException("connecting Error");
	        }
	    }
	    public ResultSet getResultSet(String query) throws SQLException {
	        return this.field_connection.createStatement().executeQuery(query);
	    }
	    
	    public Connection getConnection(){
	        return this.field_connection;
	    }
	    
	    public boolean connect_to_database(String path_to_database){
	        boolean return_value=false;
	        String driverName = "org.firebirdsql.jdbc.FBDriver";
	        String database_protocol="jdbc:firebirdsql://";
	        String database_dialect="?sql_dialect=3";
	        String database_server="localhost";
	        String database_port="3050";
	        //String databaseURL = "jdbc:firebirdsql://localhost:3050/d:/work/sadik/sadik.gdb?sql_dialect=3";
	        String databaseURL=database_protocol+database_server+":"+database_port+"/"+path_to_database+database_dialect;

	        try{
	            debug("attempt to load class for Firebird");
	            Class.forName(driverName);
	            debug("attempt to connect to Firebird"+databaseURL);
	            this.field_connection=java.sql.DriverManager.getConnection(databaseURL,"SYSDBA","masterkey");
	        }catch(SQLException sqlexception){
	            error("SQL Exception: "+sqlexception.getMessage());
	        }catch(ClassNotFoundException classnotfoundexception){
	            error("ClassNotFoundException:"+classnotfoundexception);
	        }
	        return_value=true;
	        return return_value;
	    }
	}
