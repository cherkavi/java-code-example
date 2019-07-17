/*
 * Main.java
 *
 * Created on 1 травня 2007, 14:59
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package javaapplication8;
//import com.sun.org.apache.bcel.internal.classfile.JavaClass;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.*;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

/**
 *
 * @author Техник
 */

class connect_to_firebird{
    public static Class loadClass(String path_to_jar, String class_name){
        Class return_value=null;
        try{
            URLClassLoader urlLoader = new URLClassLoader(new URL[]{new URL("file", null, path_to_jar)});
            return_value=urlLoader.loadClass(class_name);
            System.out.println("Loaded class:"+return_value.getName());
        }catch(Throwable ex){
            System.out.println(" error in load class"+ex.getMessage());
        };
        return return_value;
    }
    
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
        String driver_name = "org.firebirdsql.jdbc.FBDriver";

        try{
            System.out.println("--- try to connect driver");
            //System.loadLibrary("C:\\jaybird-full-2.1.1.jar");
            
            // простое подключение, которое требует в директории LIB жестко привязанного JAR
            //Class.forName(class_name);

            // Динамическое подключение:
            // 1:
            //driver=new org.firebirdsql.jdbc.FBDriver();
            // 2:
            /*
            Class current_class=loadClass("C:\\jaybird-full-2.1.1.jar","org.firebirdsql.jdbc.FBDriver");
            System.out.println("--- Class:"+current_class.toString());
            Constructor[] constr=current_class.getConstructors();
            for(int counter=0;counter<constr.length;counter++){
                System.out.println(counter+" : "+constr[counter].getName());
            }
            Object object=current_class.newInstance();
            driver=(java.sql.Driver)current_class.newInstance();
            System.out.println("--- Driver:"+driver.toString());
            java.sql.DriverManager.registerDriver(driver);
            */
            
            // 3:
            /*
            URL url=new URL("jar:file:C:/jaybird-full-2.1.1.jar!/");
            URLClassLoader class_loader=new URLClassLoader(new URL[]{url});
            System.out.println("URLClassLoader:"+class_loader);
            Class.forName(class_name,true,class_loader);
            System.out.println("loaded class");
            */
            // 4:
            //URL url = new URL("jar:file:/c:/jaybird-full-2.1.1.jar!/");
            URL url=new URL("file", null, "C:\\jaybird-full-2.1.1.jar");
            URLClassLoader ucl = new URLClassLoader(new URL[]{url},null);
            System.out.println("---URLClassLoader:"+ucl);
            Class class_value=Class.forName(driver_name,true,ucl);
            System.out.println("---classValue:"+class_value);
            Object d_object=class_value.newInstance();
            System.out.println("---object Driver:"+d_object);
            driver = (Driver)class_value.newInstance();
            System.out.println("---to Driver:"+driver);
            DriverManager.registerDriver(driver);
            System.out.println("---register Driver");
            Properties properties=new Properties();
            properties.put("user",user);
            properties.put("password",password);
            
            
            //org.firebirdsql.pool.FBWrappingDataSource dataSource=new org.firebirdsql.pool.FBWrappingDataSource();
            //connection=java.sql.DriverManager.getConnection(databaseURL,user,password);
            connection=driver.connect(databaseURL, properties);
            
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
        }catch(Exception e){
            System.out.println(">>>Error "+e.getMessage());
        }catch(Throwable ex){
            System.out.println(">>>Throwable: "+ex.getMessage());
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
            // попытка подключения к базе данных
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
            System.out.println(">>> Error in thread main:"+e.getMessage());
        }
    }
    
    private static URLClassLoader getURLClassLoader(URL jarURL) {
        return new URLClassLoader(new URL[]{jarURL});
    }
    
    
    public static void addJAR(String path_to_file,String class_name){
        try{
            URLClassLoader urlLoader = getURLClassLoader(new URL("file", null, path_to_file));
            Class class_value=urlLoader.loadClass(class_name);
            System.out.println("Loaded class:"+class_value.getName());
            JarInputStream jis = new JarInputStream(new FileInputStream(path_to_file));
            JarEntry entry = jis.getNextJarEntry();
            int loadedCount = 0, totalCount = 0;
        }catch(Throwable ex){
            System.out.println(" error in load class"+ex.getMessage());
        };
    }
    
    public static void addURL(URL u) throws IOException {
        URLClassLoader sysLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
        URL urls[] = sysLoader.getURLs();
        for (int i = 0; i < urls.length; i++) {
            if (urls[i].toString().equalsIgnoreCase(u.toString())) {
                System.out.println("URL " + u + " is already in the CLASSPATH");
                return;
            }
        }
        /*Class sysclass = URLClassLoader.class;
        try {
            Method method = sysclass.getDeclaredMethod("addURL", parameters);
            method.setAccessible(true);
            method.invoke(sysLoader, new Object[]{u});
        } catch (Throwable t) {
            t.printStackTrace();
            throw new IOException("Error, could not add URL to system classloader");
        }*/
    }    
    
}
