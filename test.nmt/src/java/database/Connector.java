/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package database;
import java.sql.*;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
/**
 *
 * @author First
 */
public class Connector {
    Connection field_connection=null;
    Logger field_logger;
    
    
    public Connector(String path_to_database) throws SQLException{
        field_logger=Logger.getRootLogger();
        field_logger.setLevel(Level.DEBUG);
        field_logger.debug("try connecting to base");
        if(connect_to_database(path_to_database)==false){
            field_logger.error("error in open database");
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
            field_logger.debug("Попытка загрузить драйвер");
            Class.forName(driverName);
            field_logger.debug("Попытка соеднинения="+databaseURL);
            this.field_connection=java.sql.DriverManager.getConnection(databaseURL,"SYSDBA","masterkey");
        }catch(SQLException sqlexception){
            field_logger.error("не удалось подключиться к базе данных");
        }catch(ClassNotFoundException classnotfoundexception){
            field_logger.error("не найден класс");
        }
        return_value=true;
        return return_value;
    }
}
