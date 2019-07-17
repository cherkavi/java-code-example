package gui.table_column_render;

import java.sql.ResultSet;

public class ColumnConst implements ICellValue{
	private String constanta;
	
	public ColumnConst(String constanta){
		this.constanta=constanta;
	}
	
	@Override
	public String getCellValue(ResultSet rs) {
		return constanta;
	}

}
