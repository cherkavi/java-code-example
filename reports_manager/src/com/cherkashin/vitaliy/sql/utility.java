/*
 * utility.java
 *
 * Created on 9 лютого 2008, 9:32
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.cherkashin.vitaliy.sql;
import java.sql.*;
import java.net.*;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.ParsePosition;
import javax.swing.JComboBox;
/**
 * класс для вызова различных вспомогательных процедур для базы данных
 */
public class utility {
    /** Creates a new instance of utility */
    public utility() {
    }
        /**
         * получить из таблицы по коду поля его наименование 
         *(или другими словами, имея одно поле получить другое(первое из списка))
         * @result возвращается полученное значение
         * @param connection - соединение с базой, к которому нужно обращаться
         * @param table_name - имя таблицы в базе данных, с которой нужно будет работать
         * @param field_name_source - имя поля по которому будет производиться поиск
         * @param value - значение, по которому будет производиться поиск
         * @param field_name_destination - имя поля, которое будет возвращено
         */
   public static String get_name_on_code(java.sql.Connection connection,
                                         String table_name,
                                         String field_name_source,
                                         String value,
                                         String field_name_destination){
        String return_value="";
        try{
            if((value!=null)&&(!value.trim().equals(""))){
                Statement statement=connection.createStatement();
                StringBuffer sql_text=new StringBuffer();
                sql_text.append("SELECT "+field_name_destination+" FROM "+table_name+" WHERE RUPPER("+field_name_source+")=");
                sql_text.append('\''+value.toUpperCase()+'\'');
                //System.out.println("text:"+sql_text.toString());
                ResultSet resultset=statement.executeQuery(sql_text.toString());
                if(resultset!=null){
                    // переход на первую запись
                    if(resultset.next()){
                        return_value=resultset.getString(1);
                    }else{
                        return_value="";
                    }
                }else{
                    // нет данных для отображения
                }
                statement.close();
            }
        }catch(SQLException ex){
            System.out.println("utility.get_name_on_code SQL ошибка при выполнении запроса:"+ex.getMessage());
            while(ex.getNextException()!=null){
                System.out.println("utility.get_name_on_code SQL ошибка при выполнении запроса:"+ex.getMessage());
            }
        }
        catch(Exception e){
            
            System.out.println("utility.get_name_on_code общая ошибка при выполнении запроса:"+e.getMessage());
        } 
        return return_value;
   };
   /**
    * получить максимальное значение поля 
    * @result возвращает 0 в случае, если произошла непредвиденная ситуация или если значений нет, другое значение, если значения есть
    * @param connection соединение с базой данных
    * @param table_name имя таблицы, в которой будет выбираться максимальное значение по полю
    * @param field_name имя поля в таблице и выборка максимального значения из этого поля
    */
    public static int get_max_int_field(Connection connection,String table_name,String field_name){
        int return_value=0;
        try{
            ResultSet resultset=(connection.createStatement()).executeQuery("SELECT MAX("+field_name+") FROM "+table_name);
            if(resultset!=null){
                resultset.next();
                return_value=resultset.getInt(1);
            }else{
                System.out.println("utility.get_max_int_field  запрос не вернул результата");
            }
        }catch(SQLException ex){
            System.out.println("utility.get_max_int_field ошибка выполнения запроса "+ex.getMessage());
        }catch(Exception e){
            System.out.println("utility.get_max_int_field другая ошибка "+e.getMessage());
        }
        return return_value;
   }
   /**
    * заполнить JComboBox из таблицы из поля
    * @param connection текущее соединение с базой данных
    * @param combobox JComboBox в который нужно наполнить данными
    * @param table_name имя таблицы в базе данных
    * @param table_field имя поля в таблице базы данных
    * @param order_by_field блок сортировки в запросе
    */
    public static boolean fill_combobox_from_table_from_field(java.sql.Connection connection,
                                                              JComboBox combobox,
                                                              String table_name, 
                                                              String table_field,
                                                              String order_by_field){
        boolean return_value=false;
        // очистка Combobox
        combobox.removeAllItems();
        try{
            // отработка запроса к базе данных
            java.sql.Statement statement=connection.createStatement();
            ResultSet resultset;
            if(!order_by_field.trim().equals("")){
                resultset=statement.executeQuery("SELECT DISTINCT "+table_field+" FROM "+table_name+" ORDER BY "+order_by_field);
            }else{
                resultset=statement.executeQuery("SELECT DISTINCT "+table_field+" FROM "+table_name);
            }
            // заполнение данными JComboBox
            while(resultset.next()){
                combobox.addItem(resultset.getString(1));
            }
            return_value=true;
        }catch(Exception e){
            System.out.println("fill_combobox_from_table_from_field ERROR");
        }
        return return_value;
    }
   /**
    * заполнить JComboBox из таблицы из поля и добавить пустую строку
    * @param connection текущее соединение с базой данных
    * @param combobox JComboBox в который нужно наполнить данными
    * @param table_name имя таблицы в базе данных
    * @param table_field имя поля в таблице базы данных
    * @param order_by_field блок сортировки в запросе
    */
    public static boolean fill_combobox_from_table_from_field_with_empty(java.sql.Connection connection,
                                                              JComboBox combobox,
                                                              String table_name, 
                                                              String table_field,
                                                              String order_by_field){
        boolean return_value=false;
        // очистка Combobox
        combobox.removeAllItems();
        try{
            // отработка запроса к базе данных
            java.sql.Statement statement=connection.createStatement();
            ResultSet resultset;
            if(!order_by_field.trim().equals("")){
                resultset=statement.executeQuery("SELECT DISTINCT "+table_field+" FROM "+table_name+" ORDER BY "+order_by_field);
            }else{
                resultset=statement.executeQuery("SELECT DISTINCT "+table_field+" FROM "+table_name);
            }
            // заполнение данными JComboBox
            combobox.addItem("");
            while(resultset.next()){
                if(! (resultset.getString(1).trim().equals("")) ){
                    combobox.addItem(resultset.getString(1));
                }
            }
            return_value=true;
        }catch(Exception e){
            System.out.println("fill_combobox_from_table_from_field ERROR:"+e.getMessage());
        }
        return return_value;
    }
    
   /**
    * заполнить JComboBox из таблицы из поля
    * @param connection текущее соединение с базой данных
    * @param combobox JComboBox в который нужно наполнить данными
    * @param table_name имя таблицы в базе данных
    * @param table_field имя поля в таблице базы данных
    */
    public static boolean fill_combobox_from_table_from_field(java.sql.Connection connection,
                                                              JComboBox combobox,
                                                              String table_name, 
                                                              String table_field){
        return fill_combobox_from_table_from_field(connection,combobox,table_name,table_field,"");
    }
   /**
    * заполнить JComboBox из таблицы из поля и добавить пустую строку
    * @param connection текущее соединение с базой данных
    * @param combobox JComboBox в который нужно наполнить данными
    * @param table_name имя таблицы в базе данных
    * @param table_field имя поля в таблице базы данных
    */
    public static boolean fill_combobox_from_table_from_field_with_empty(java.sql.Connection connection,
                                                              JComboBox combobox,
                                                              String table_name, 
                                                              String table_field){
        return fill_combobox_from_table_from_field_with_empty(connection,combobox,table_name,table_field,"");
    }
    
   /**
    * заполнить JComboBox из таблицы из поля
    * @param connection текущее соединение с базой данных
    * @param combobox JComboBox в который нужно наполнить данными
    * @param table_name имя таблицы в базе данных
    * @param table_field имя поля в таблице базы данных
    * @param not_in_list массив из String, которые не должны входить в выборку
    */
    public static boolean fill_combobox_from_table_from_field(java.sql.Connection connection,
                                                              JComboBox combobox,
                                                              String table_name, 
                                                              String table_field,
                                                              String order_by_field,
                                                              String[] not_in_list){
        boolean return_value=false;
        // очистка Combobox
        combobox.removeAllItems();
        try{
            // отработка запроса к базе данных
            java.sql.PreparedStatement statement;
            ResultSet resultset;
            StringBuffer where_string=new StringBuffer();
            where_string.append("");
            // создать строку исключений
            if((not_in_list!=null)&&(not_in_list.length>0)){
                where_string.append(" WHERE RUPPER("+table_field+") NOT IN(\n");
                for(int counter=0;counter<not_in_list.length;counter++){
                    if(counter!=(not_in_list.length-1)){
                        where_string.append("?,\n");
                    }else{
                        where_string.append("?");
                    }
                }
                where_string.append(") ");
            }
            // отработка запроса
            if(!order_by_field.trim().equals("")){
                if(where_string.toString().equals("")){
                    //System.out.println("Where string:"+where_string.toString());
                    statement=connection.prepareStatement("SELECT DISTINCT "+table_field+" FROM "+table_name+" ORDER BY "+order_by_field);
                }else{
                    statement=connection.prepareStatement("SELECT DISTINCT "+table_field+" FROM "+table_name+where_string.toString()+" ORDER BY "+order_by_field);
                    //System.out.println("Where string:"+where_string.toString());
                    for(int counter=0;counter<not_in_list.length;counter++){
                        //System.out.println(counter+"  : "+not_in_list[counter]);
                        statement.setString(counter+1,not_in_list[counter].toUpperCase());
                    }
                }
            }else{
                if(where_string.toString().equals("")){
                    statement=connection.prepareStatement("SELECT DISTINCT "+table_field+" FROM "+table_name);
                }else{
                    statement=connection.prepareStatement("SELECT DISTINCT "+table_field+" FROM "+table_name+where_string.toString());
                    for(int counter=0;counter<not_in_list.length;counter++){
                        System.out.println(counter+"  : "+not_in_list[counter]);
                        statement.setString(counter+1,not_in_list[counter].toUpperCase());
                    }
                }
            };
            resultset=statement.executeQuery();
            // заполнение данными JComboBox
            while(resultset.next()){
                combobox.addItem(resultset.getString(1));
            }
            return_value=true;
        }catch(Exception e){
            System.out.println("fill_combobox_from_table_from_field ERROR "+e.getMessage());
        }
        return return_value;
    }

   /**
    * заполнить JComboBox из таблицы из поля
    * @param connection текущее соединение с базой данных
    * @param combobox JComboBox в который нужно наполнить данными
    * @param table_name имя таблицы в базе данных
    * @param table_field имя поля в таблице базы данных которое будет занесенов в JComboBox
    * @param table_field_list имя поля в таблице базы данных, по которому будут отбираться значения из списка
    * @param in_list массив из String которые должны входить в данную выборку
    * @param order_by_field строка для упорядочивания массива
    */
    public static boolean fill_combobox_from_table_from_field(java.sql.Connection connection,
                                                              JComboBox combobox,
                                                              String table_name, 
                                                              String table_field,
                                                              String table_field_list,
                                                              String[] in_list,
                                                              String order_by_field){
        boolean return_value=false;
        // очистка Combobox
        combobox.removeAllItems();
        try{
            // отработка запроса к базе данных
            java.sql.PreparedStatement statement;
            ResultSet resultset;
            StringBuffer where_string=new StringBuffer();
            where_string.append("");
            // создать строку исключений
            if((in_list!=null)&&(in_list.length>0)){
                where_string.append(" WHERE RUPPER("+table_field_list+") IN(\n");
                for(int counter=0;counter<in_list.length;counter++){
                    if(counter!=(in_list.length-1)){
                        where_string.append("?,\n");
                    }else{
                        where_string.append("?");
                    }
                }
                where_string.append(") ");
            }
            // отработка запроса
            if(!order_by_field.trim().equals("")){
                if(where_string.toString().equals("")){
                    //System.out.println("Where string:"+where_string.toString());
                    statement=connection.prepareStatement("SELECT DISTINCT "+table_field+" FROM "+table_name+" ORDER BY "+order_by_field);
                }else{
                    statement=connection.prepareStatement("SELECT DISTINCT "+table_field+" FROM "+table_name+where_string.toString()+" ORDER BY "+order_by_field);
                    //System.out.println("Where string:"+where_string.toString());
                    for(int counter=0;counter<in_list.length;counter++){
                        //System.out.println(counter+"  : "+in_list[counter]);
                        statement.setString(counter+1,in_list[counter].toUpperCase());
                    }
                }
            }else{
                if(where_string.toString().equals("")){
                    statement=connection.prepareStatement("SELECT DISTINCT "+table_field+" FROM "+table_name);
                }else{
                    statement=connection.prepareStatement("SELECT DISTINCT "+table_field+" FROM "+table_name+where_string.toString());
                    for(int counter=0;counter<in_list.length;counter++){
                        //System.out.println(counter+"  : "+in_list[counter]);
                        statement.setString(counter+1,in_list[counter].toUpperCase());
                    }
                }
            };
            resultset=statement.executeQuery();
            // заполнение данными JComboBox
            while(resultset.next()){
                combobox.addItem(resultset.getString(1));
            }
            return_value=true;
        }catch(Exception e){
            System.out.println("fill_combobox_from_table_from_field ERROR "+e.getMessage());
        }
        return return_value;
    }


    /**
    * заполнить JComboBox из таблицы из поля
    * @param connection текущее соединение с базой данных
    * @param combobox JComboBox в который нужно наполнить данными
    * @param table_name имя таблицы в базе данных
    * @param table_field имя поля в таблице базы данных
    * @param not_in_list массив из String, которые не должны входить в выборку
    */
    public static boolean fill_combobox_from_table_from_field_with_empty(java.sql.Connection connection,
                                                              JComboBox combobox,
                                                              String table_name, 
                                                              String table_field,
                                                              String order_by_field,
                                                              String[] not_in_list){
        boolean return_value=false;
        // очистка Combobox
        combobox.removeAllItems();
        try{
            // отработка запроса к базе данных
            java.sql.PreparedStatement statement;
            ResultSet resultset;
            StringBuffer where_string=new StringBuffer();
            where_string.append("");
            // создать строку исключений
            if((not_in_list!=null)&&(not_in_list.length>0)){
                where_string.append(" WHERE RUPPER(NAME) NOT IN(\n");
                for(int counter=0;counter<not_in_list.length;counter++){
                    if(counter!=(not_in_list.length-1)){
                        where_string.append("?,\n");
                    }else{
                        where_string.append("?");
                    }
                }
                where_string.append(") ");
            }
            // отработка запроса
            if(!order_by_field.trim().equals("")){
                if(where_string.toString().equals("")){
                    //System.out.println("Where string:"+where_string.toString());
                    statement=connection.prepareStatement("SELECT DISTINCT "+table_field+" FROM "+table_name+" ORDER BY "+order_by_field);
                }else{
                    statement=connection.prepareStatement("SELECT DISTINCT "+table_field+" FROM "+table_name+where_string.toString()+" ORDER BY "+order_by_field);
                    //System.out.println("Where string:"+where_string.toString());
                    for(int counter=0;counter<not_in_list.length;counter++){
                        //System.out.println(counter+"  : "+not_in_list[counter]);
                        statement.setString(counter+1,not_in_list[counter].toUpperCase());
                    }
                }
            }else{
                if(where_string.toString().equals("")){
                    statement=connection.prepareStatement("SELECT DISTINCT "+table_field+" FROM "+table_name);
                }else{
                    statement=connection.prepareStatement("SELECT DISTINCT "+table_field+" FROM "+table_name+where_string.toString());
                    for(int counter=0;counter<not_in_list.length;counter++){
                        System.out.println(counter+"  : "+not_in_list[counter]);
                        statement.setString(counter+1,not_in_list[counter].toUpperCase());
                    }
                }
            };
            resultset=statement.executeQuery();
            // заполнение данными JComboBox
            combobox.addItem("");
            while(resultset.next()){
                if(!(resultset.getString(1).trim().equals(""))){
                    combobox.addItem(resultset.getString(1));
                }
            }
            return_value=true;
        }catch(Exception e){
            System.out.println("fill_combobox_from_table_from_field ERROR "+e.getMessage());
        }
        return return_value;
    }
    
    /**
     * удаление из базы данных информации на основании предоставленных значений
     * @param connection соединение с базой данных
     * @param table_name имя таблицы в базе данных
     * @param field_name имя параметра в базе данных
     * @param value значение параметра в базе данных
     * @param autocommit нужно ли защелкнуть транзакцию
     */
    public static boolean delete_on_table_on_field(java.sql.Connection connection,
                                                   String table_name,
                                                   String field_name,
                                                   String value,
                                                   boolean autocommit){
        boolean return_value=false;
        String query="DELETE FROM "+table_name+" WHERE RUPPER("+field_name+")=?";
        try{
            java.sql.PreparedStatement statement=connection.prepareStatement(query);
            statement.setString(1,value.toUpperCase());
            statement.executeUpdate();
            if(autocommit==true){
                connection.commit();
            }
            return_value=true;
        }catch(SQLException ex){
            System.out.println("utility.delete_on_table_on_field   SQL Exception "+ex.getMessage());
        }
        return return_value;
    }
    /**
     * удаление из базы данных информации на основании предоставленных значений
     * @param connection соединение с базой данных
     * @param table_name имя таблицы в базе данных
     * @param field_name имя параметра в базе данных
     * @param value значение параметра в базе данных
     * @param autocommit нужно ли защелкнуть транзакцию
     */
    public static boolean delete_on_table_on_field(java.sql.Connection connection,
                                                   String table_name,
                                                   String field_name,
                                                   int value,
                                                   boolean autocommit){
        boolean return_value=false;
        String query="DELETE FROM "+table_name+" WHERE "+field_name+"=?";
        try{
            java.sql.PreparedStatement statement=connection.prepareStatement(query);
            statement.setInt(1,value);
            statement.executeUpdate();
            if(autocommit==true){
                connection.commit();
            }
            return_value=true;
        }catch(SQLException ex){
            System.out.println("utility.delete_on_table_on_field   SQL Exception "+ex.getMessage());
        }
        return return_value;
    }
    /**
     * метод, который отрабатывает запрос INSERT для строк и для целых чисел
     * @param connection текущее соединение с базой данных
     * @param table_name задает имя таблицы
     * @param fields_name задает соответствующие имена полей в таблице
     * @param values данные, которые нужно вставить в массив (Byte,Integer,Long,Float,String,Date)
     */
    public static boolean insert(java.sql.Connection connection,
                                 String table_name,
                                 String[] fields_name,
                                 Object[] values,
                                 boolean autocommit) throws Exception{
        boolean return_value=false;
        StringBuffer text_query=new StringBuffer();
        if(values.length==fields_name.length){
            // подготовка текста запроса
            text_query.append("INSERT INTO "+table_name+" (\n");
            for(int counter=0;counter<fields_name.length;counter++){
                if(counter==(fields_name.length-1)){
                    text_query.append(fields_name[counter]+")\n");
                }else{
                    text_query.append(fields_name[counter]+",\n");
                }
            }
            text_query.append("VALUES(\n");
            for(int counter=0;counter<fields_name.length;counter++){
                if(counter==(fields_name.length-1)){
                    text_query.append("?)\n");
                }else{
                    text_query.append("?,\n");
                }
            }
            // создание PreparedStatement
            java.sql.PreparedStatement preparedstatement=connection.prepareStatement(text_query.toString());
            // задание значений для переменных
            for(int counter=0;counter<fields_name.length;counter++){
                preparedstatement.setObject(counter+1,values[counter]);
            }
            //System.out.println("utility.insert: \n"+text_query.toString()+"\n");            
            preparedstatement.executeUpdate();
            // защелкиваем транзакцию, если это необходимо
            if(autocommit){
                connection.commit();
            }
            return_value=true;
        }else{
            throw new Exception("error in array dimension");
        }
        return return_value;
    }
    /**
     * метод, который отрабатывает запрос UPDATE для строки и целых чисел
     * 
     */
    public static boolean update(java.sql.Connection connection,
                                 String table_name,
                                 String[] unique_fields_name,
                                 Object[] unique_values,
                                 String[] fields_name,
                                 Object[] values,
                                 boolean autocommit) throws Exception{
        boolean return_value=false;
        // подготовка текста запроса
        if((unique_fields_name.length==unique_values.length)&&(fields_name.length==values.length)){
            StringBuffer text_query=new StringBuffer();
            text_query.append("UPDATE "+table_name+"\n SET ");
            for(int counter=0;counter<fields_name.length;counter++){
                if(counter==(fields_name.length-1)){
                    text_query.append(fields_name[counter]+"=? \n");
                }else{
                    text_query.append(fields_name[counter]+"=?, \n");
                }
            }
            text_query.append("WHERE \n");
            for(int counter=0;counter<unique_fields_name.length;counter++){
                if(counter==(unique_fields_name.length-1)){
                    text_query.append(unique_fields_name[counter]+"=? \n");
                }else{
                    text_query.append(unique_fields_name[counter]+"=? AND \n");
                }
            }
            //System.out.println("UPDATE text query:"+text_query.toString());
            // подготовка PreparedStatement
            PreparedStatement prepared_statement=connection.prepareStatement(text_query.toString());
            // присваивание переменным
            for(int counter=0;counter<fields_name.length;counter++){
                prepared_statement.setObject(counter+1,values[counter]);
                //System.out.println((counter+1)+":"+values[counter]);
            }
            
            for(int counter=0;counter<unique_fields_name.length;counter++){
                prepared_statement.setObject(fields_name.length+counter+1,unique_values[counter]);
                //System.out.println((unique_fields_name.length+counter+1)+":"+unique_values[counter]);
            }
            // отработка запроса
            if(prepared_statement.executeUpdate()>0){
                if(autocommit){
                    connection.commit();
                }
                return_value=true;
            }else{
                return_value=false;
            }
        }else{
            // неправильная передача параметров 
            throw new Exception("parameters Error");
        }
        return return_value;
    }
    /**
     * преобразование даты из java.util.Date в дату java.sql.Date
     */
    public static java.sql.Date convert_to_sql_date(java.util.Date value){
        java.text.SimpleDateFormat sql_date_format=new java.text.SimpleDateFormat("yyyy-MM-dd");
        try{
            return java.sql.Date.valueOf(sql_date_format.format(value));
        }catch(Exception e){
            return null;
        }
    }
    
    /**
     * преобразование даты из java.sql.Date в дату java.util.Date
     */
    public static java.util.Date convert_to_util_date(java.sql.Date value){
        java.text.SimpleDateFormat sql_date_format=new java.text.SimpleDateFormat("yyyy-MM-dd");
        try {
            return sql_date_format.parse(value.toString());
        } catch (ParseException ex) {
            return null;
        }
    }
    /**
     * преобразование даты из java.util.Date во временной штамп java.sql.TimeStamp
     */
    public static java.sql.Timestamp convert_to_sql_timestamp(java.util.Date value){
        java.text.SimpleDateFormat sql_date_format=new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        try{
            return java.sql.Timestamp.valueOf(sql_date_format.format(value));
        }catch(Exception e){
            return null;
        }
    }
    /**
     * преобразование даты из java.sql.TimeStamp в дату java.util.Date
     */
    public static java.util.Date convert_to_util_date(java.sql.Timestamp value){
        java.text.SimpleDateFormat sql_date_format=new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        try {
            return sql_date_format.parse(value.toString());
        } catch (ParseException ex) {
            return null;
        }
    }

    
}













