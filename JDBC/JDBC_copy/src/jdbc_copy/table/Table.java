package jdbc_copy.table;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

/** ������ ��� ������� � ���� ������ */
public class Table {
	private String name;
	private ArrayList<Field> fields=new ArrayList<Field>();
	
	/** ������ ��� ������� � ���� ������ */
	public Table(String tableName, Connection connection) throws SQLException{
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
			Object insertedObject=rs.getObject(this.fields.get(counter).getName());
			System.out.println("InsertedObject ("+counter+") :"+insertedObject);
			if(insertedObject instanceof String){
				insertQuery.setString(counter+1, (String)insertedObject);
			}else{
				insertQuery.setObject(counter+1, insertedObject);
			}
			
		}
	}
}
