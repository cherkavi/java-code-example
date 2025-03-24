package jdbc_analisator;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DatabaseAnalisator {
	/** ��������� �������  */
	public enum CompareResult{
		/** �������� ��������� � ����� ������ */
		compareOk,
		/** ������ ��������� � ����� ������ */
		compareError;
	}
	
	/** ���� �� ������� � ���� ������  */
	public class Field{
		private String name;
		private int size;
		private String typeName;
		/** ���� �� ������� � ���� ������  */
		public Field(){
		}
		
		/** ���� �� ������� � ���� ������  
		 * @param name - ������������ ���� 
		 * @param size - ������ ����
		 * @param typeName - ��� ����  
		 */
		public Field(String name, int size, String typeName){
			this.name=name;
			this.size=size;
			this.typeName=typeName;
		}
		
		/** �������� ������������ ����  */
		public String getName(){
			return this.name;
		}
		
		/** �������� ������ ����  */
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
	
	/** �������� ���������� ������� �� ������� ���������� ( ������� ��� �� ������ ) 
	 * @param one - ������ ���������� � ����� ������ 
	 * @param two - ������ ���������� � ����� ������ 
	 * @return - ������ ���������� ���������� 
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<String> getOneUniqueTables(Connection one, Connection two){
		ArrayList<String> oneTables=this.getTableList(one);
		ArrayList<String> twoTables=this.getTableList(two);
		return (ArrayList<String>) this.leftUnique(oneTables, twoTables);
	}
	
	/** �������� �������, ��� ����� ��������� � ������ � �� ������ ����  */
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
	
	/** �������� ������ ���������� �����, ������� ���� � ������ �������, �� ��� �� ������ ������� 
	 * @param one - ���������� � ������ ����� ������ 
	 * @param oneTableName - ��� ������� � ������ ���� ������ 
	 * @param two - ���������� �� ������ ����� ������ 
	 * @param twoTableName - ��� ������� �� ������ ���� ������ 
	 * @return - ����� 
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<Field> getOneUniqueFields(Connection one, String oneTableName, Connection two, String twoTableName){
		ArrayList<Field> oneFields=this.getFieldsList(one, oneTableName);
		ArrayList<Field> twoFields=this.getFieldsList(one, oneTableName);
		return (ArrayList<Field>)this.leftUnique(oneFields, twoFields);
	}
	
	/** �������� ���������� ������� �� ������ ��������, ������� �� ����������� �� ������  */
	private ArrayList<?> leftUnique(ArrayList<? extends Object> left, ArrayList<? extends Object> right){
		ArrayList<Object> returnValue=new ArrayList<Object>();
		for(Object value:left){
			if(right.indexOf(value)<0){
				returnValue.add(value);
			}
		}
		return returnValue;
	}
	
	
	/** �������� ������ ������ � ���� ������  */
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

	/** �������� ���� �� ��������� �������  */
	public ArrayList<Field> getFieldsList(Connection connection, String tableName){
		ArrayList<Field> returnValue=new ArrayList<Field>();
		try{
			/** �������� ������ ���� ����� � ���� ������, � ��������� ������� */
			ResultSet rsColumns = connection.getMetaData().getColumns(null, null, tableName, null);
		    while (rsColumns.next()) {
		    	returnValue.add(new Field(rsColumns.getString("COLUMN_NAME"),rsColumns.getInt("COLUMN_SIZE"),rsColumns.getString("TYPE_NAME")));
		    }
		}catch(Exception ex){
			System.err.println("#getColumnList Exception: "+ex.getMessage());
		}
		return returnValue;
	}
	
	// �������� 
}
