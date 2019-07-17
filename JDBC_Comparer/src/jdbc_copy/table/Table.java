package jdbc_copy.table;

import java.sql.Connection;

import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/** ������ ��� ������� � ���� ������ */
public class Table {
	private String name;
	private ArrayList<Field> fields=new ArrayList<Field>();
	
	/**
	 * get list of tables from connection 
	 * @param connection - connection
	 * @param removeList - list for element which will be removed from table list (null or empty - not used )
	 * @param includeList -  filter for element ( null or empty - not used ) 
	 * @return - list of table 
	 * @throws Exception - if get data from the connection was called Exception 
	 */
	public static List<Table> getTablesFromConnection(Connection connection, List<String> removeList, List<String> includeList) throws Exception{
		DatabaseMetaData metaData=connection.getMetaData();
		ArrayList<String> tableNames=getTableNames(metaData);
		if((removeList!=null)&&(removeList.size()>0)){
			tableNames=removeFromList(tableNames, removeList);
		}
		if((includeList!=null)&&(includeList.size()>0)){
			tableNames=booleanAnd(tableNames, includeList);
		}
		ArrayList<Table> returnValue=new ArrayList<Table>();
		for(int counter=0;counter<tableNames.size();counter++){
			returnValue.add(new Table(tableNames.get(counter),connection));
		}
		return returnValue;
	}
	
	/**
	 * remove from <b> source </b> list all elements has in <b> listOfRemove </b>
	 * @param source
	 * @param listForRemove
	 * @return
	 */
	private static ArrayList<String> removeFromList(List<String> source, List<String> listForRemove){
		ArrayList<String> returnValue=new ArrayList<String>();
		for(String currentValue:source){
			if(!listForRemove.contains(currentValue)){
				returnValue.add(currentValue);
			}
		}
		return returnValue;
	}
	
	private static ArrayList<String> booleanAnd(List<String> first, List<String> second){
		ArrayList<String> returnValue=new ArrayList<String>();
		for(String currentValue:first){
			if(second.contains(currentValue)){
				returnValue.add(currentValue);
			}
		}
		return returnValue;
	}
	
	/** get list of table from connection  */
	private static ArrayList<String> getTableNames(DatabaseMetaData metaData) throws SQLException {
		ArrayList<String> returnValue=new ArrayList<String>();
		ResultSet rs=metaData.getTables(null, null, "%",new String[]{"TABLE"});
		/*int columnCount=rs.getMetaData().getColumnCount();
		for(int counter=0;counter<columnCount;counter++){
			System.out.print("   "+counter+" : "+rs.getMetaData().getColumnName(counter+1));
		}*/
		while (rs.next()){
			returnValue.add(rs.getString("TABLE_NAME"));
		}
		return returnValue;
	}
	
	/** ������ ��� ������� � ���� ������ */
	public Table(String tableName, Connection connection) throws SQLException{
/*
		Connection connection=this.source.getConnection();
		if(connection!=null){
			try{
			}catch(Exception ex){
				JOptionPane.showMessageDialog(this,ex.getMessage(), "������ ��� ������������ ������ ",JOptionPane.ERROR_MESSAGE);
			}
		}else{
			JOptionPane.showMessageDialog(this, "�������� ��������", "������",JOptionPane.ERROR_MESSAGE);
		}
 */
		
		this.name=tableName;
		ResultSet rs=connection.createStatement().executeQuery("select * from "+tableName);
		ResultSetMetaData rsmd=rs.getMetaData();
		int columnCount=rsmd.getColumnCount();
		for(int counter=1;counter<=columnCount;counter++){
			this.fields.add(new Field(rsmd.getColumnName(counter),rsmd.getColumnType(counter),rsmd.getColumnTypeName(counter),rsmd.getColumnDisplaySize(counter)));
			System.out.println();
		}
		rs.getStatement().close();
	}

	
	/** �������� ��� ������� */
	public String getName(){
		return this.name;
	}
	
	/** �������� ���-�� ����� �� ���� ������ */
	public int getFieldCount(){
		return this.fields.size();
	}
	
	/** �������� ���� �� ���� ������ */
	public Field getField(int index){
		if(this.getFieldCount()<index){
			return this.fields.get(index);
		}else{
			return null;
		}
	}

	/** �������� SQL ������ ��� �������� �������� */
	public String getSqlCreate() {
		StringBuffer query=new StringBuffer();
		query.append("create table ");
		query.append(this.name);
		query.append("( ");
		for(int counter=0;counter<this.fields.size();counter++){
			query.append(this.fields.get(counter).getName());
			query.append(" ");
			query.append(this.fields.get(counter).getTypeName());
			if(  (this.fields.get(counter).getTypeName().equalsIgnoreCase("VARCHAR"))
			   ||(this.fields.get(counter).getTypeName().equalsIgnoreCase("CHAR"))){
				query.append("(");
				query.append(this.fields.get(counter).getSize());
				query.append(")");
			}
			if(counter!=(this.fields.size()-1)){
				query.append(", ");
			}
		}
		query.append(") ");
		return query.toString();
	}

	/** �������� ������ �� ������� ������ - ������� �� ��������� �������� ����������� SQL Prepared */
	public String getSqlPreparedInsert() {
		StringBuffer query=new StringBuffer();
		query.append("INSERT INTO "+this.name+" (");
		for(int counter=0;counter<this.fields.size();counter++){
			query.append(this.fields.get(counter).getName());
			if(counter!=(this.fields.size()-1)){
				query.append(", ");
			}
		}
		query.append(") ");
		query.append(" VALUES (");
		for(int counter=0;counter<this.fields.size();counter++){
			query.append("?");
			if(counter!=(this.fields.size()-1)){
				query.append(", ");
			}
		}
		query.append(") ");
		return query.toString();
	}


	/** ��������� ����������� ���������� ������� */
	public void fillParameters(PreparedStatement insertQuery, ResultSet rs) throws SQLException{
		for(int counter=0;counter<this.fields.size();counter++){
			insertQuery.setObject(counter+1, rs.getObject(this.fields.get(counter).getName()));
		}
	}

	public static List<Table> getUniqueTablesFromFirstList(List<Table> firstConnectionTables, 
														   List<Table> secondConnectionTables) {
		ArrayList<Table> returnValue=new ArrayList<Table>();
		for(Table table:firstConnectionTables){
			if(! isTableNameInList(table, secondConnectionTables)){
				returnValue.add(table);
			}
		}
		return returnValue;
	}
	
	private static boolean isTableNameInList(Table table, List<Table> listOfTable){
		for(Table currentTable:listOfTable){
			if(currentTable.getName().equals(table.getName())){
				return true;
			}
		}
		return false;
	}

	public static List<String> getShareTableNames(List<Table> firstConnectionTables,List<Table> secondConnectionTables) {
		ArrayList<String> returnValue=new ArrayList<String>();
		for(Table table:firstConnectionTables){
			if(getTableByName(table.getName(), secondConnectionTables)!=null){
				returnValue.add(table.getName());
			}
		}
		return returnValue;
	}

	public static Table getTableByName(String name, List<Table> secondConnectionTables) {
		for(Table table:secondConnectionTables){
			if(table.getName().equals(name)){
				return table;
			}
		}
		return null;
	}

	/**
	 * get all fields from table 
	 * @return
	 */
	public List<Field> getFields(){
		return this.fields;
	}

	@Override
	public String toString() {
		return this.name;
	}
	
	
}
