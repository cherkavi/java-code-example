package jdbc_analisator;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DatabaseAnalisator {
	/** результат анализа  */
	public enum CompareResult{
		/** успешное сравнение с базой данных */
		compareOk,
		/** ошибка сравнений с базой данных */
		compareError;
	}
	
	/** поле из таблицы в базе данных  */
	public class Field{
		private String name;
		private int size;
		private String typeName;
		/** поле из таблицы в базе данных  */
		public Field(){
		}
		
		/** поле из таблицы в базе данных  
		 * @param name - наименование поля 
		 * @param size - размер поля
		 * @param typeName - имя типа  
		 */
		public Field(String name, int size, String typeName){
			this.name=name;
			this.size=size;
			this.typeName=typeName;
		}
		
		/** получить наименование поля  */
		public String getName(){
			return this.name;
		}
		
		/** получить размер поля  */
		public int getSize(){
			return this.size;
		}
		
		@Override
		public boolean equals(Object obj){
			try{
				return ((Field)obj).name.equals(name)&&( ((Field)obj).size==size);
			}catch(Exception ex){
				return false;
			}
		}
		
		@Override
		public String toString(){
			return this.name+"  "+this.typeName+" ("+this.size+")";
		}
	}
	
	/** получить уникальные таблицы из первого соединения ( которых нет во втором ) 
	 * @param one - первое соединение с базой данных 
	 * @param two - второе соединение с базой данных 
	 * @return - список уникальных соединений 
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<String> getOneUniqueTables(Connection one, Connection two){
		ArrayList<String> oneTables=this.getTableList(one);
		ArrayList<String> twoTables=this.getTableList(two);
		return (ArrayList<String>) this.leftUnique(oneTables, twoTables);
	}
	
	/** получить таблицы, чьи имена одинаковы в первой и во второй базе  */
	public ArrayList<String> getEqualsTables(Connection one, Connection two){
		ArrayList<String> oneTables=this.getTableList(one);
		ArrayList<String> twoTables=this.getTableList(two);
		ArrayList<String> returnValue=new ArrayList<String>();
		for(String value:oneTables){
			if(twoTables.indexOf(value)>=0){
				returnValue.add(value);
			}
		}
		return returnValue;
	}
	
	/** получить список уникальных полей, которые есть в первой таблице, но нет во второй таблице 
	 * @param one - соединение с первой базой данных 
	 * @param oneTableName - имя таблицы в первой базе данных 
	 * @param two - соединение со второй базой данных 
	 * @param twoTableName - имя таблицы во второй базе данных 
	 * @return - спиок 
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<Field> getOneUniqueFields(Connection one, String oneTableName, Connection two, String twoTableName){
		ArrayList<Field> oneFields=this.getFieldsList(one, oneTableName);
		ArrayList<Field> twoFields=this.getFieldsList(one, oneTableName);
		return (ArrayList<Field>)this.leftUnique(oneFields, twoFields);
	}
	
	/** получить уникальные объекты из левого операнда, которые не содержаться во втором  */
	private ArrayList<?> leftUnique(ArrayList<? extends Object> left, ArrayList<? extends Object> right){
		ArrayList<Object> returnValue=new ArrayList<Object>();
		for(Object value:left){
			if(right.indexOf(value)<0){
				returnValue.add(value);
			}
		}
		return returnValue;
	}
	
	
	/** получить список таблиц в базе данных  */
	private ArrayList<String> getTableList(Connection connection){
		ArrayList<String> returnValue=new ArrayList<String>();
		try{
			ResultSet rs=connection.getMetaData().getTables(null, null, "%", new String[]{"TABLE"});
			// int columnCount=rs.getMetaData().getColumnCount();
			// System.out.println("Tables into Connection:");
			while(rs.next()){
				/*
				for(int counter=1;counter<=columnCount;counter++){
					System.out.print(counter+":"+rs.getString(counter)+"   ");
				}
				System.out.println();
				*/
				returnValue.add(rs.getString(3));
			}
		}catch(Exception ex){
			System.err.println("#getTableList Exception:"+ex.getMessage());
		}
		return returnValue;
	}

	/** получить поля из указанной таблицы  */
	public ArrayList<Field> getFieldsList(Connection connection, String tableName){
		ArrayList<Field> returnValue=new ArrayList<Field>();
		try{
			/** получить список всех полей в базе данных, в указанной таблице */
			ResultSet rsColumns = connection.getMetaData().getColumns(null, null, tableName, null);
		    while (rsColumns.next()) {
		    	returnValue.add(new Field(rsColumns.getString("COLUMN_NAME"),rsColumns.getInt("COLUMN_SIZE"),rsColumns.getString("TYPE_NAME")));
		    }
		}catch(Exception ex){
			System.err.println("#getColumnList Exception: "+ex.getMessage());
		}
		return returnValue;
	}
	
	// получить 
}
