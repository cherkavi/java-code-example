/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package selectblobtobyte;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import oracle.jdbc.driver.OracleCallableStatement;
import java.sql.*;
/** 
 * отвечает за выемку значений из базы данных по уникальному номеру SESSION_ID <br>
 * эти данные были положены в базу данных в объекте Action во время информационного обмена CONNECTION:3 и CONNECTION:4 
 */

public class bcAppletActionDBParameters {
 private String field_session_id="";
 /** поле, в котором хранятся ключи из базы данных по заданной сессии */
 private ArrayList<String> field_database_key=new ArrayList<String>();
 /** поле, в котором хранятся byte[] данные из базы по их именам */
 private ArrayList<Object> field_database_value=new ArrayList<Object>();
 /** */
 private String field_query="select * from v_applet_parameters_all where id_user_session=?";
 
 private void debug(String value){
  System.out.print(this.getClass().getName());
  System.out.print(" DEBUG ");
  System.out.println(value);
 }

 private void error(String value){
  System.out.print(this.getClass().getName());
  System.out.print(" ERROR ");
  System.out.println(value);
 }
 /** получить значения из базы данных для указанной сессии, эти значения были положены в базу данных во время
  * отработки Action на этапе CONNECTION:3 и CONNECTION:4 
  */
 public bcAppletActionDBParameters(String session_id){
  this.field_session_id=session_id;
  getAllObjectsFromDataBaseBySessionId();
 }
 
 /** получить из базы данных все объекты по текущей сессии */
 private void getAllObjectsFromDataBaseBySessionId(){
  field_database_key.clear();
  field_database_value.clear();
  try{
    debug("get connection ");
   Connector connector=new Connector();
   Connection connection=connector.getConnection();
   debug("create statement");
   PreparedStatement statement=connection.prepareStatement(field_query);
   statement.setString(1, this.field_session_id);
   debug("execute query");
   ResultSet rs=statement.executeQuery();
   while(rs.next()){
        this.addParameter(rs.getString("NAME_PARAM"), rs.getBlob("VALUE_PARAM").getBinaryStream());
   }
   rs.close();
   statement.close();
   connection.close();
  }catch(SQLException ex){
   error("getAllObjectFromDataBaseBySessionId: SQLException:"+ex.getMessage());
  }catch(Exception ex){
   error("getAllObjectFromDataBaseBySessionId:    Exception:"+ex.getMessage());
  }
 }
 
 /** добавить еще один параметр в наш список на основании [ключ][значение]
  * @param param_name - ключ, по которому лежит данный параметр в базе
  * @param deserialize_object - десериализованный объект из базы данных в виде byte[]
  * */
 private void addParameter(String param_name, byte[] deserialize_object){
  try{
   Object object_from_base=this.readObject_from_array(deserialize_object);
   this.field_database_key.add(param_name);
   this.field_database_value.add(object_from_base);
   debug("parameter added:"+param_name);
  }catch(Exception ex){
   error("parameter add error:"+param_name);
  }
 }
 
 private void addParameter(String param_name, InputStream is){
  try{
   Object object_from_base=this.readObject_from_inputStream(is);
   this.field_database_key.add(param_name);
   this.field_database_value.add(object_from_base);
   debug("parameter added:"+param_name);
  }catch(Exception ex){
   error("parameter add error:"+param_name);
  }
 }
 
 
 /** <b>прочитать объект из byte[] и вернуть прочитанный объект</b>
  * @param массив из байт, который содержит объект 
  * @return прочитанный из данной последовательности объект
  * */
 private Object readObject_from_inputStream(InputStream is){
  Object return_value=null;
  try{
    ObjectInputStream ois=new ObjectInputStream(is);
    return_value=ois.readObject();
    debug("Object read from byte_array is OK");
  }catch(Exception ex){
   error("readObject_from_array Error:"+ex.getMessage());
  }
  return return_value;
 }

 /** <b>прочитать объект из byte[] и вернуть прочитанный объект</b>
  * @param массив из байт, который содержит объект 
  * @return прочитанный из данной последовательности объект
  * */
 private Object readObject_from_array(byte[] array_of_byte){
  Object return_value=null;
  try{
   if(array_of_byte==null){
    error("readObject_from_array parameter is null ");
   }else{
    ByteArrayInputStream byte_array_input_stream=new ByteArrayInputStream(array_of_byte);
    ObjectInputStream ois=new ObjectInputStream(byte_array_input_stream);
    return_value=ois.readObject();
    debug("Object read from byte_array is OK");
   }
  }catch(Exception ex){
   error("readObject_from_array Error:"+ex.getMessage());
  }
  return return_value;
 }
 
 /**
  * возвращаем параметр из базы данных по SESSION_ID
  * @param key ключ из базы данных
  * @return либо пустое значение, если не найден, либо 
  */
 public String getParameterByString(String key){
  String return_value="";
  int index=this.field_database_key.indexOf(key);
  if(index>=0){
   try{
    return_value=(String)this.field_database_value.get(index);
   }catch(Exception ex){
    // return_value="";
   }
   
  }
  return return_value;
 }
 
 /**
  * получить кол-во всех параметров из базы
  */
 public int getParameterCount(){
  return this.field_database_key.size();
 }
 
 /**
  * получить имя ключа для указанного параметра
  * @param index уникальный индекс
  * @return ключ, или пустое поле, если ключ выходит за пределы
  */
 public String getParameterKeyByIndex(int index){
  String return_value="";
  try{
   return_value=this.field_database_key.get(index);
  }catch(Exception ex){
   
  }
  return return_value;
 }
 
 /** получить объект в виде строки на основании указанного индекса
  * @param index - индекс в контексте параметров 
  * @return возвращает пустое значение(если индекс не найден) либо значение параметра
  */
 public String getParameterValueStringByIndex(int index){
  String return_value="";
  try{
   return_value=(String)this.field_database_value.get(index);
  }catch(Exception ex){
   // return_value="";
  }
  return return_value;
 }
}