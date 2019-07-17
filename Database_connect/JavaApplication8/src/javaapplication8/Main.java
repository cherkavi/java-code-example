/*
 * Main.java
 *
 * Created on 1 травн€ 2007, 14:59
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package javaapplication8;
//import com.sun.org.apache.bcel.internal.classfile.JavaClass;
import java.sql.*;

/**
 *
 * @author “ехник
 */

class connect_to_firebird{
    connect_to_firebird(){
        java.sql.Driver driver=null;
        java.sql.Connection connection=null;
        java.sql.Statement statement=null;
        java.sql.ResultSet rs=null;
        java.sql.ResultSetMetaData rs_metadata=null;
        String query;
        //String databaseURL = "jdbc:firebirdsql:local:d:/work/sadik/sadik.gdb?sql_dialect=3";
        String databaseURL = "jdbc:firebirdsql://localhost:3050/d:/work/sadik/sadik.gdb?sql_dialect=3";
        String user = "SYSDBA";
        String password = "masterkey";
        String driverName = "org.firebirdsql.jdbc.FBDriver";

        try{
            System.out.println("try to connect driver");
            Class.forName("org.firebirdsql.jdbc.FBDriver");
            //org.firebirdsql.pool.FBWrappingDataSource dataSource=new org.firebirdsql.pool.FBWrappingDataSource();
            connection=java.sql.DriverManager.getConnection(databaseURL,user,password);
            
            System.out.println("firebird connected");
            statement=connection.createStatement();

            query="SELECT KOD_1NF FROM S_DDZ";
            if(statement.execute(query)==true){
                System.out.println("Query OK");
                // вывод результата
                rs=statement.getResultSet();
                    // вывод заголовков
                rs_metadata=rs.getMetaData();
                for(int i=0;i<rs_metadata.getColumnCount();i++){
                    System.out.print(" >"+rs_metadata.getColumnLabel(i+1)+"<  ");
                }
                System.out.println("\n");
                    // вывод данных
                while(rs.next()){
                    for(int i=0;i<rs_metadata.getColumnCount();i++){
                        System.out.print(" "+rs.getInt(i+1)+"  ");
                    }
                    System.out.println("\n");
                }
                System.out.println("end of MySQL");
            }
        }
        catch(Exception e){
            System.out.println("Error \n"+e.getMessage());
        }
    }
}

class connect_to_mysql{
    String driver_name="org.gjt.mm.mysql.Driver";
    java.sql.Connection connection=null;
    java.sql.Driver driver=null;
    java.sql.Statement statement=null;
    java.sql.ResultSet rs=null;
    java.sql.ResultSetMetaData rs_metadata=null;
    String query;
    
    //String driver="org.gjt.mm.mysql.Driver";
    
    connect_to_mysql(){
        try{
            // попытка подключени€ к базе данных
            Class.forName(driver_name);
            connection=java.sql.DriverManager.getConnection("jdbc:mysql://localhost:3306/db_temp","root","");
            System.out.println("connected");
            
            statement=connection.createStatement();
            query="SELECT * FROM TABLE_1";
            if(statement.execute(query)==true){
                System.out.println("Query OK");
                // вывод результата
                rs=statement.getResultSet();
                    // вывод заголовков
                rs_metadata=rs.getMetaData();
                for(int i=0;i<rs_metadata.getColumnCount();i++){
                    System.out.print(" >"+rs_metadata.getColumnLabel(i+1)+"<  ");
                }
                System.out.println("\n");
                    // вывод данных
                while(rs.next()){
                    for(int i=0;i<rs_metadata.getColumnCount();i++){
                        System.out.print(" "+rs.getString(i+1)+"  ");
                    }
                    System.out.println("\n");
                }
                System.out.println("end of MySQL");
            }
            else {
                System.out.println("Error in query");
            }

        }
        catch(Exception e){
            System.out.println("Error in connect to MySQl \n"+e.getMessage());
        }
    }
}
public class Main {
    
    /** Creates a new instance of Main */
    public Main() {
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        try{
            //new connect_to_mysql();
            new connect_to_firebird();
        }
        catch(java.lang.NoClassDefFoundError e){
            System.out.println("Error in thread main \n"+e.getMessage());
        }
    }
    
}
