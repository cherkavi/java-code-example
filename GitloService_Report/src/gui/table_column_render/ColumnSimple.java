package gui.table_column_render;

import java.sql.ResultSet;

public class ColumnSimple implements ICellValue{
	private int column=(-1);
	private String name=null;
	/** отображение только одной колонки */
	public ColumnSimple(int column){
		this.column=column;
	}
	/** отображение только одной колонки */
	public ColumnSimple(String name){
		this.name=name;
	}
	
	@Override
	public String getCellValue(ResultSet rs) {
		String returnValue="";
		try{
			if(column<0){
				returnValue=rs.getString(name);
			}else{
				returnValue=rs.getString(column);
			}
		}catch(Exception ex){};
		return returnValue;
	}

}
