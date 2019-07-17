package database.serializer;

import java.sql.ResultSet;

/** описание элемента из базы данных, на основании имени таблицы */
public class DatabaseColumn implements IRecordColumn{
	private String columnName=null;;
	
	/** описание элемента из базы данных, на основании имени таблицы */
	public DatabaseColumn(String columnName){
		this.columnName=columnName;
	}
	
	@Override
	public String getColumnName() {
		return this.columnName;
	}

	@Override
	public Object getObject(ResultSet rs) {
		try{
			return rs.getObject(this.columnName);
		}catch(Exception ex){
			return null;
		}
		
	}

}
