import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;
import com.ibatis.common.resources.Resources;
import database.Information;
import database.connector.FirebirdConnector;

import java.io.FileInputStream;
import java.io.Reader;
import java.io.IOException;
import java.util.List;
import java.sql.Connection;
import java.sql.SQLException;

public class EnterPoint {
	private static SqlMapClient client=null; 
	public static void main(String[] args){
		try{
			// получить соединение с базой данных 
			FirebirdConnector connector=new FirebirdConnector();
			Connection connection=connector.getConnection();
			// подключить iBatis
			client=SqlMapClientBuilder.buildSqlMapClient(new FileInputStream("D:\\eclipse_workspace\\iBatis_example\\SqlMapConfig.xml"));
			// либо же установить данный блок в SqlMapConfig.xml (как Child для sqlMapConfig)
/*
  <transactionManager type="JDBC" commitRequired="false">
    <dataSource type="SIMPLE">
      <property name="JDBC.Driver" value="org.firebirdsql.jdbc.FBDriver"/>
      <property name="JDBC.ConnectionURL" value="jdbc:firebirdsql://localhost:3050/c:/temp_data.GDB?sql_dialect=3"/>
      <property name="JDBC.Username" value="SYSDBA"/>
      <property name="JDBC.Password" value="masterkey"/>
    </dataSource>
  </transactionManager>
 */
			// можно либо установить Connection для использования
			client.setUserConnection(connection);
			
			Information information=new Information();
			information.setId(10);
			information.setTextValue("this is temp value");
			information.setIntValue(20);
			information.setDateValue(new java.util.Date());
			updateInformation(information);
			System.out.println("Update OK");
			
			List<?> list=selectAllInformation();
			for(Object element:list){
				System.out.println("this is element:"+((Information)element).getTextValue());
			}
			
			System.out.println("Ok");
		}catch(Exception ex){
			System.err.println("Exception: "+ex.getMessage());
		}
	}
	
	private static List selectAllInformation() throws Exception{
		return client.queryForList("selectAllInformation");
	}

	private static Information selectInformationById(int id) throws Exception{
		return (Information)client.queryForObject("selectInformationById",id);
	}
	 
	private static void insertInformation (Information Information) throws SQLException {
	    client.insert("insertInformation", Information);
	}

	private static void updateInformation (Information Information) throws SQLException {
	   client.update("updateInformation", Information);
	}

	private static void deleteInformation (int id) throws SQLException {
	   client.delete("deleteInformation", id);
	}
	
	
}
