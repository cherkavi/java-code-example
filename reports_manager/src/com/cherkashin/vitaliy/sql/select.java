/*
  * Created on 30 січня 2008, 8:07
 *
 */

package com.cherkashin.vitaliy.sql;
import java.sql.*;
/**
 * для выборки данных из базы
 */
public class select {
    /** получение ResultSet из таблицы базы данных на основании ее имени
     *@result возвращает набор данных согласно заданным парметрам
     *@param connection соединение с базой данных
     *@param table_name имя таблицы базы данных
     */
    public static java.sql.ResultSet fromTable(Connection connection,
                                               String table_name){
        ResultSet return_value=null;
        Statement statement=null;
        try {
            statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            return_value=statement.executeQuery("SELECT * FROM "+table_name);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return return_value;
    }
    /** получение ResultSet из таблицы базы данных на основании ее имени и полей 
     *@result возвращает набор данных согласно заданным парметрам
     *@param connection соединение с базой данных
     *@param table_name имя таблицы базы данных
     *@param fields поля из базы данных, которые нужно вернуть в наборе данных
     */
    public static java.sql.ResultSet fromTable(Connection connection,
                                               String table_name,
                                               String[] fields){
        return fromTable(connection,table_name,fields,"");
    }
    
    /** получение ResultSet из таблицы базы данных на основании ее имени и полей c условием
     *@result возвращает набор данных согласно заданным парметрам
     *@param connection соединение с базой данных
     *@param table_name имя таблицы базы данных
     *@param fields поля из базы данных, которые нужно вернуть в наборе данных
     *@param where_string строка, которая будет вставлена в запрос, в блок "WHERE ..."
     */
    public static java.sql.ResultSet fromTable(Connection connection,
                                               String table_name,
                                               String[] fields,
                                               String where_string){
        ResultSet return_value=null;
        Statement statement=null;
        StringBuffer temp_string=new StringBuffer("");
        try{
            statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            for(int counter=0;counter<fields.length;counter++){
                temp_string.append(fields[counter]);
                if(counter!=(fields.length-1)){
                    temp_string.append(", ");
                }
            }
            if(where_string.trim()!=""){
                return_value=statement.executeQuery("SELECT "+temp_string+" FROM "+table_name+" WHERE "+where_string);
            }else{
                return_value=statement.executeQuery("SELECT "+temp_string+" FROM "+table_name);
            }
        } catch(SQLException ex){
            System.out.println("Error in get resultset from connection");
            ex.printStackTrace();
        }
        
        return return_value;
    }
   /**  получение ResultSet на основании заданного текста запроса 
    *@result набор данных на основании SQL запроса к базе данных
    *@param connection соединение с базой данных
    *@param sql_text SQL запрос, который нужно отработать 
    */
    public static java.sql.ResultSet fromQuery(Connection connection,String sql_text){
        ResultSet return_value=null;
        Statement statement=null;
        try{
            statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            return_value=statement.executeQuery(sql_text);
        }catch (SQLException e){
            System.out.println("Error in execute Query");
            e.printStackTrace();
        }
        return return_value;
    }
}




















