package gui.table_column_render;

import java.sql.ResultSet;

public class ColumnTruncDouble implements ICellValue{
	private int columnNumber;
	private String columnName=null;
	
	/** объект, который пытается брать из указанного столбца Double и преобразовывает его в Int*/
	public ColumnTruncDouble(int columnNumber){
		this.columnNumber=columnNumber;
	}
	
	/** объект, который пытается брать из указанного столбца Double и преобразовывает его в Int*/
	public ColumnTruncDouble(String columnName){
		this.columnName=columnName;
	}

	@Override
	public String getCellValue(ResultSet rs) {
		String returnValue=null;
		try{
			if(this.columnName==null){
				returnValue=Integer.toString((int)rs.getDouble(this.columnNumber));
			}else{
				returnValue=Integer.toString((int)rs.getDouble(this.columnName));
			}
			
		}catch(Exception ex){
			try{
				if(this.columnName==null){
					returnValue=rs.getString(this.columnNumber);
				}else{
					returnValue=rs.getString(this.columnName);
				}
			}catch(Exception ex2){};
		}
		return (returnValue==null)?"":returnValue;
	}

}
