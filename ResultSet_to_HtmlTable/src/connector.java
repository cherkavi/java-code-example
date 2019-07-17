/*
 * connector.java
 *
 * Created on 8 Январь 2008 г., 15:25
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

import java.sql.*;
/**
 *
 * @author Виталик
 */
public class connector {
    /** Creates a new instance of connector */
    public connector() {
    }
    /**
     * 
     */
    public static java.sql.Connection get_connection_to_firebird(String path_to_server,
                                                          String path_to_database,
                                                          Integer port,
                                                          String user,
                                                          String password){
        //java.sql.Driver driver=null;
        java.sql.Connection connection=null;

        String driverName = "org.firebirdsql.jdbc.FBDriver";
        String database_protocol="jdbc:firebirdsql://";
        String database_dialect="?sql_dialect=3";
        String database_server=null;
        String database_port=null;
        //String databaseURL = "jdbc:firebirdsql://localhost:3050/d:/work/sadik/sadik.gdb?sql_dialect=3";
        if((path_to_server=="")||(path_to_server==null)){
            database_server="localhost";
        }else{
            database_server=path_to_server;
        }
        if(port==0){
            database_port="3050";
        }else{
            database_port=Integer.toString(port);
        }
        String databaseURL=database_protocol+database_server+":"+database_port+"/"+path_to_database+database_dialect;
        
        try{
            //System.out.println("Попытка загрузить драйвер");
            Class.forName(driverName);
            //System.out.println("Попытка соеднинения="+databaseURL);
            connection=java.sql.DriverManager.getConnection(databaseURL,user,password);
        }catch(SQLException sqlexception){
            System.out.println("не удалось подключиться к базе данных:"+sqlexception);
            System.out.println("Database URL: "+databaseURL);
        }catch(ClassNotFoundException classnotfoundexception){
            System.out.println("не найден класс");
        }
        return connection;
    }
}
