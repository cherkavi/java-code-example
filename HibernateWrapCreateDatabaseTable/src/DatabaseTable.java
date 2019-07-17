import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Properties;


/** обертка для таблицы в базе, для получения полной информации о данной таблице */
public class DatabaseTable {
	private String tableName;
	private ArrayList<DatabaseField> fields=new ArrayList<DatabaseField>();
	/** обертка для таблицы в базе данных, для получения полной информации о данной таблице*/
	public DatabaseTable(Connection connection, String tableName) throws Exception{
		this.tableName=tableName;
		this.initAllColumns(connection);
	}
	
	private void initAllColumns(Connection connection) throws Exception {
		// TABLE_CAT    <>     TABLE_SCHEM    <>     TABLE_NAME    <>     COLUMN_NAME    <>     DATA_TYPE    <>     TYPE_NAME    <>     COLUMN_SIZE    <>     BUFFER_LENGTH    <>     DECIMAL_DIGITS    <>     NUM_PREC_RADIX    <>     NULLABLE    <>     REMARKS    <>     COLUMN_DEF    <>     SQL_DATA_TYPE    <>     SQL_DATETIME_SUB    <>     CHAR_OCTET_LENGTH    <>     ORDINAL_POSITION    <>     IS_NULLABLE    <>     
		ResultSet rs=connection.getMetaData().getColumns(null, null, this.tableName, "%");
		int columnCount=rs.getMetaData().getColumnCount();
		this.fields.clear();
		while(rs.next()){
			this.fields.add(new DatabaseField(rs.getString("COLUMN_NAME"),rs.getString("TYPE_NAME"),rs.getInt("COLUMN_SIZE")));
		}

		rs=connection.getMetaData().getPrimaryKeys(null, null, tableName);
		//TABLE_CAT    <>     TABLE_SCHEM    <>     TABLE_NAME    <>     COLUMN_NAME    <>     KEY_SEQ    <>     PK_NAME    <>     
		while(rs.next()){
			this.setPrimaryKey(rs.getString("COLUMN_NAME"));
		}
	}

	private void setPrimaryKey(String columnName){
		for(int counter=0;counter<this.fields.size();counter++){
			if(this.fields.get(counter).getFieldName().equals(columnName)){
				this.fields.get(counter).setPrimaryKey(true);
			}
		}
	}
	
	/** получить все поля из данной таблицы */
	public ArrayList<DatabaseField> getAllFields(){
		return this.fields;
	}
	
	/** получить кол-во всех полей из данной таблицы */
	public int getFieldCount(){
		return this.fields.size();
	}
	
	/** получить выделенное поле из данной таблицы */
	public DatabaseField getFieldByIndex(int index){
		return this.fields.get(index);
	}
	
	
	public String toString(){
		StringBuffer returnValue=new StringBuffer();
		returnValue.append(this.tableName);
		returnValue.append("[");
		for(int counter=0;counter<this.fields.size();counter++){
			returnValue.append(this.fields.get(counter));
			if(counter!=(this.fields.size()-1)){
				returnValue.append(", ");
			}
		}
		returnValue.append("]");
		return returnValue.toString();
	}
	
	public String getTableNameFirstUpperCase(){
		String name=this.tableName.toLowerCase();
		String returnValue=name.substring(1).toLowerCase();
		returnValue=name.substring(0, 1).toUpperCase()+returnValue;
		return returnValue;
	}
	
	public StringBuffer getHibernateFile(Properties javaSqlTypeToJavaType){
		StringBuffer returnValue=new StringBuffer();
		returnValue.append("import javax.persistence.Column;\n");
		returnValue.append("import javax.persistence.Entity;\n");
		returnValue.append("import javax.persistence.GeneratedValue;\n");
		returnValue.append("import javax.persistence.GenerationType;\n");
		returnValue.append("import javax.persistence.Id;\n");
		returnValue.append("import javax.persistence.SequenceGenerator;\n");
		returnValue.append("import javax.persistence.Table;\n");
		returnValue.append("import java.util.Date;\n");
		returnValue.append("@Entity\n");		
		returnValue.append("@Table(name=\""+this.tableName+"\") \n");
		returnValue.append("public class "+getTableNameFirstUpperCase()+" {\n");
		for(int counter=0;counter<this.fields.size();counter++){
			if(this.fields.get(counter).isPrimaryKey()){
				// is primary key
				returnValue.append("	@Id\n");
				returnValue.append("	@Column(name=\""+this.fields.get(counter).getFieldName()+"\")\n");
				returnValue.append("	@SequenceGenerator(name=\"generator\",sequenceName=\"GEN_"+this.tableName+"_ID\")\n");
				returnValue.append("	@GeneratedValue(generator=\"generator\",strategy=GenerationType.AUTO)\n");
				returnValue.append("    	private "+javaSqlTypeToJavaType.getProperty(this.fields.get(counter).getTypeName())+" "+this.fields.get(counter).getFieldName().toLowerCase()+";\n");
			}else{
				// is not primary key
				returnValue.append("	@Column(name=\""+this.fields.get(counter).getFieldName()+"\")\n");
				returnValue.append("    	private "+javaSqlTypeToJavaType.getProperty(this.fields.get(counter).getTypeName())+" "+this.fields.get(counter).getFieldName().toLowerCase()+";\n");
			}
			returnValue.append("\n");
		}
		returnValue.append("}\n");		
		return returnValue;
	}
}
