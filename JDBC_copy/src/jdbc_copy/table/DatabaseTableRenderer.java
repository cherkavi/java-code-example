package jdbc_copy.table;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class DatabaseTableRenderer implements TableCellRenderer{

	@Override
	public Component getTableCellRendererComponent(JTable table, 
												   Object value,
												   boolean isSelected, 
												   boolean hasFocused, 
												   int row, 
												   int column) {
		
		try{
			((DatabaseTableModelObject)value).setSelected(isSelected);
			((DatabaseTableModelObject)value).setFocused(hasFocused);
		}catch(Exception ex){
			System.err.println("DatabaseTableRenderer Exception: "+ex.getMessage());
			return null;
		}
		return (DatabaseTableModelObject)value;
	}

	
	
}
