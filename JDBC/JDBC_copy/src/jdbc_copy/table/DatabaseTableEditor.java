package jdbc_copy.table;

import java.awt.Component;
import java.util.EventObject;

import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;

public class DatabaseTableEditor implements TableCellEditor{
	private DatabaseTableModelObject editComponent;
	
	@Override
	public Component getTableCellEditorComponent(JTable table, 
												 Object value,
												 boolean isSelected, 
												 int row, 
												 int column) {
		this.editComponent=((DatabaseTableModelObject)table.getModel().getValueAt(row, column));
		return this.editComponent;
	}

	@Override
	public void addCellEditorListener(CellEditorListener l) {
	}

	@Override
	public void cancelCellEditing() {
	}

	@Override
	public Object getCellEditorValue() {
		return null;
	}

	@Override
	public boolean isCellEditable(EventObject anEvent) {
		return true;
	}

	@Override
	public void removeCellEditorListener(CellEditorListener l) {
	}

	@Override
	public boolean shouldSelectCell(EventObject anEvent) {
		this.editComponent.setSelected(true);
		return true;
	}

	@Override
	public boolean stopCellEditing() {
		this.editComponent.setSelected(false);
		return true;
	}
}
