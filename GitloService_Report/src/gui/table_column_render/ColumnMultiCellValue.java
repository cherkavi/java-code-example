package gui.table_column_render;

import java.sql.ResultSet;

public class ColumnMultiCellValue implements ICellValue{
	private ICellValue[] values;

	public ColumnMultiCellValue(ICellValue ... values){
		this.values=values;
	}
	@Override
	public String getCellValue(ResultSet rs) {
		StringBuffer returnValue=new StringBuffer();
		for(int counter=0;counter<values.length;counter++){
			returnValue.append(values[counter].getCellValue(rs));
		}
		return returnValue.toString();
	}
	
}
