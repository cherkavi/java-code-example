package database.serializer;

import java.sql.ResultSet;

public interface IRecordColumn {
	/** имя колонки для сохранения в объекте */
	public String getColumnName();
	
	public Object getObject(ResultSet rs);
}
