package database;
import java.sql.*;
import java.util.Calendar;

import javax.swing.JComboBox;

/** класс, который содержит static public method для работы с базой данных */
public class Utility {
	public static String[] monthName=new String[]{"Январь", "Февраль","Март","Апрель","Май","Июнь","Июль","Август","Сентябрь","Октябрь","Ноябрь","Декабрь"};
	
	public static java.sql.Date getSqlDate(int day, int month, int year){
		Calendar calendar=Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.MONTH, month-1);
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		java.util.Date date=calendar.getTime();
		return new java.sql.Date(date.getTime());
	}
	
	
    /** 
     * Заполнить JComboBox на основании ResultSet и имени поля из этого ResultSet
     * @param combobox визуальный элемент, который нужно заполнить
     * @param resultset набор данных должен находиться на точке начала данных (beforeFirst)
     * @param fieldname имя поля с данными из ResultSet, которыми будет заполнен Combobox
     * @param with_empty флаг, который говорит о необходимости добавления в начало пустой строки
     * @result TRUE если все прошло успешно, FALSE - заполнение прошло неудачно
     */
    public static boolean fill_combobox_from_resultset(JComboBox combobox,ResultSet resultset,String fieldname,boolean with_empty){
        boolean return_value=false;
        try{
            if(combobox!=null){
                combobox.removeAllItems();
                if((resultset!=null)&&(fieldname!=null)&&(!fieldname.trim().equals(""))){
                    if(with_empty){
                        combobox.addItem("");
                    }
                    while(resultset.next()){
                        combobox.addItem(resultset.getString(fieldname));
                    }
                }else{
                    // resultset==null or fielname==null or fieldname.equals("")
                }
            }else{
                // combobox is null
            }
        }catch(Exception ex){
            System.out.println("fill_combobox_from_resultset Exception:"+ex.getMessage());
        }
        return return_value;
    }
	
    /**
     * получить из указанной таблицы значение одного поля, передав в качестве параметра объект 
     * @param connection - текущее соединение с базой данных 
     * @param tableName - имя таблицы 
     * @param fieldName -имя поля 
     * @param value - значение поля
     * @param fieldDestination - поле для получения результатат
     * @return возвращает объект из указанного поля или null в случае ошибки
     */
    public static String getObjectFromTable(Connection connection,
    									    String tableName, 
    									    String fieldName, 
    									    Object value, 
    									    String fieldDestination){
    	String returnValue=null;
    	PreparedStatement statement=null;
    	ResultSet rs=null;
    	try{
    		statement=connection.prepareStatement("SELECT "+fieldDestination+" FROM "+tableName+" WHERE "+fieldName+"=?");
    		statement.setObject(1, value);
    		rs=statement.executeQuery();
    		if(rs.next()){
    			returnValue=rs.getString(fieldDestination);
    		}
    	}catch(Exception ex){
    		System.err.println("getObjectFromTable Exception:"+ex.getMessage());
    	}finally{
    		if(rs!=null){
    			try{
    				rs.close();
    			}catch(Exception ex){};
    		}
    		if(statement!=null){
    			try{
    				statement.close();
    			}catch(Exception ex){};
    		}
    	}
    	return returnValue;
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

}
