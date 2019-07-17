package gui.swing_table;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/** класс, который служит для получения результирующих запросов для тела и для ключей */
public class KeyQueryExecutor {
	/** вывести отладочную информацию */
	@SuppressWarnings("unused")
	private void debug(Object information){
		System.out.print("KeyQueryExecutor: ");
		System.out.print(" DEBUG ");
		System.out.println(information);
	}
	/** вывести информацию об ошибках */
	private void error(Object information){
		System.out.print("KeyQueryExecutor: ");
		System.out.print(" ERROR ");
		System.out.println(information);
	}
	private PreparedStatement statementForData;
	private ArrayList<ArrayList<Object>> keys;
	public int length=0;
	/** объект, который служит для получения ResultSet на основании ключей 
	 * @param statementForData - запрос для ключей
	 * @param keys - массив из ArrayList, который используется в качестве ключей для вставки в PreparedStatement
	 * (при чем порядок в ArrayList строго соответствует порядку вставки данных в PreparedStatement)
	 * */
	public KeyQueryExecutor(PreparedStatement statementForData,ArrayList<ArrayList<Object>> keys){
		this.statementForData=statementForData;
		this.keys=keys;
		this.length=keys.size();
	}

	/** получить новые ключи на основании запроса и соединения с базой данных */
	private ArrayList<ArrayList<Object>> getKeys(String query, Connection connection){
		ArrayList<ArrayList<Object>> returnValue=null;
		ResultSet rs=null;
		try{
			rs=connection.createStatement().executeQuery(query);
			int columnCount=rs.getMetaData().getColumnCount();
			returnValue=new ArrayList<ArrayList<Object>>();
			while(rs.next()){
				ArrayList<Object> currentRow=new ArrayList<Object>();
				for(int counter=0;counter<columnCount;counter++){
					currentRow.add(rs.getObject(counter+1));
				}
				this.keys.add(currentRow);
			}
		}catch(Exception ex){
			error("Constructor Exception: "+ex.getMessage());
		}finally{
			try{
				rs.getStatement().close();
			}catch(Exception ex){};
		}
		return returnValue;
	}
	/** функция(не работает с членами объекта): получить новые ключи на основании запроса и соединения с базой данных */
	private ArrayList<ArrayList<Object>> getKeys(ResultSet rs){
		ArrayList<ArrayList<Object>> returnValue=null;
		try{
			int columnCount=rs.getMetaData().getColumnCount();
			returnValue=new ArrayList<ArrayList<Object>>();
			while(rs.next()){
				ArrayList<Object> currentRow=new ArrayList<Object>();
				for(int counter=0;counter<columnCount;counter++){
					currentRow.add(rs.getObject(counter+1));
				}
				returnValue.add(currentRow);
			}
		}catch(Exception ex){
			error("Constructor Exception: "+ex.getMessage());
		}finally{
			try{
				rs.getStatement().close();
			}catch(Exception ex){};
		}
		return returnValue;
	}
	
	/** объект, который служит для получения ResultSet на основании ключей 
	 * @param statementForData - запрос для ключей
	 * @param query - запрос, который нужно выполнить на основании connection, и все полученные данные занести как ключи в ArrayList&ltObject&gt[]
	 * @param connection - соединение с базой данных, на основании которого нужно получить запрос 
	 * */
	public KeyQueryExecutor(PreparedStatement statementForData,
						    String query, 
						    Connection connection){
		this.statementForData=statementForData;
		this.keys=this.getKeys(query, connection);
		this.length=keys.size();
	}

	/** объект, который служит для получения ResultSet на основании ключей 
	 * @param statementForKey - запрос для ключей
	 * @param rs - набор данных для ключей
	 * */
	public KeyQueryExecutor(PreparedStatement statementForData,
						    ResultSet rs){
		this.statementForData=statementForData;
		this.keys=this.getKeys(rs);
		this.length=keys.size();
	}
	
	/** установить новые ключи для запроса */
	public void setNewKeys(ArrayList<ArrayList<Object>> newKeys){
		this.keys=newKeys;
		this.length=this.keys.size();
	}
	
	/** установить новые ключи на основании запроса и соединения с базой данных
	 * @param query - запрос к базе данных, который содержит только необходимые поля для получения ключей
	 * @param connection - соединение с базой данных 
	 * */
	public void setNewKeys(String query, Connection connection){
		this.keys=this.getKeys(query,connection);
		this.length=this.keys.size();
	}
	
	/** установить новые ключи на основании набора данных ( ResultSet )
	 * @param rs - набор данных, который содержит только необходимые ключи 
	 * */
	public void setNewKeys(ResultSet rs){
		this.keys=this.getKeys(rs);
		this.length=this.keys.size();
	}
	
	/** получить на основании индекса ResultSet 
	 * @param rowIndex - индекс ключа, который нужно подставлять в ResultSet
	 * */
	public ResultSet getResultSetBody(int rowIndex){
		ResultSet returnValue=null;
		try{
			this.statementForData.clearParameters();
			for(int counter=0;counter<this.keys.get(rowIndex).size();counter++){
				this.statementForData.setObject(counter+1,this.keys.get(rowIndex).get(counter));
			}
			returnValue=this.statementForData.executeQuery();
		}catch(Exception ex){
			error("getResultSetBody: Exception:"+ex.getMessage());
		}
		return returnValue;
	}
	
	/** получить на основании ключей ResultSet*/
	public ResultSet getResultSetBody(ArrayList<Object> keyValues){
		ResultSet returnValue=null;
		try{
			this.statementForData.clearParameters();
			for(int counter=0;counter<keyValues.size();counter++){
				this.statementForData.setObject(counter+1,keyValues.get(counter));
			}
			returnValue=this.statementForData.executeQuery();
		}catch(Exception ex){
			error("getResultSetBody: Exception:"+ex.getMessage());
		}
		return returnValue;
	}
	
	/** получить набор ключей для указанного rowIndex 
	 * @param rowIndex - строка, по которой нужно получить данные
	 * */
	public ArrayList<Object> getKeys(int rowIndex){
		return this.keys.get(rowIndex);
	}
	
}
