package gui.table_column_render;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;

public class ColumnDate implements ICellValue{
	private int column=(-1);
	private String name=null;
	/** отображение только одной колонки */
	public ColumnDate(int column){
		this.column=column;
	}
	/** отображение только одной колонки */
	public ColumnDate(String name){
		this.name=name;
	}
	private SimpleDateFormat dateFormat=new SimpleDateFormat("dd.MM.yyyy");
	
	@Override
	public String getCellValue(ResultSet rs) {
		String returnValue="";
		try{
			if(column<0){
				returnValue=dateFormat.format(rs.getDate(name));
			}else{
				returnValue=dateFormat.format(rs.getDate(column));
			}
		}catch(Exception ex){};
		return returnValue;
	}

}
