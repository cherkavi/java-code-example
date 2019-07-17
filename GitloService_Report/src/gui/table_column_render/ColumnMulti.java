package gui.table_column_render;

import java.sql.ResultSet;


public class ColumnMulti implements ICellValue{
	private int[] columns;
	private String[] columnNames;
	/**  соединение через пробел нескольких колонок */
	public ColumnMulti(int ... columns){
		this.columns=columns;
	}
	/** соединение через пробел нескольких колонок */
	public ColumnMulti(String ... columnNames){
		this.columnNames=columnNames;
	}
	
	
	@Override
	public String getCellValue(ResultSet rs) {
		StringBuffer returnValue=new StringBuffer();
		if(columns!=null){
			for(int counter=0;counter<this.columns.length;counter++){
				try{
					returnValue.append(rs.getString(columns[counter]).trim());
				}catch(Exception ex){};
				returnValue.append(" ");
			}
		}
		if(columnNames!=null){
			for(int counter=0;counter<this.columnNames.length;counter++){
				try{
					returnValue.append(rs.getString(columnNames[counter]).trim());
				}catch(Exception ex){};
				returnValue.append(" ");
			}
		}
		return returnValue.toString();
	}
}
