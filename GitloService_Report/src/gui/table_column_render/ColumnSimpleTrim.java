package gui.table_column_render;

import java.sql.ResultSet;

public class ColumnSimpleTrim implements ICellValue{
	private int column;
	private String columnName=null;
	/** объект из базы данных, предварительно сделав Trim */
	public ColumnSimpleTrim(int column){
		this.column=column;
	}

	/** объект из базы данных, предварительно сделав Trim */
	public ColumnSimpleTrim(String columnName){
		this.columnName=columnName;
	}
	
	@Override
	public String getCellValue(ResultSet rs) {
		try{
			if(this.columnName==null){
				return rs.getString(column).trim();
			}else{
				return rs.getString(columnName).trim();
			}
		}catch(Exception ex){
			return "";
		}
	}

}
