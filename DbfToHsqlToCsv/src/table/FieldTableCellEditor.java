package table;

import java.awt.Component;
import java.util.ArrayList;
import java.util.EventObject;

import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.TableCellEditor;

import common.Field;

public class FieldTableCellEditor implements TableCellEditor{
	private JCheckBox currentCheckBox;
	
	@Override
	public Component getTableCellEditorComponent(JTable table, 
												 Object value,
												 boolean isSelected, 
												 int row, 
												 int column) {
		this.currentCheckBox=null;
		try{
			this.currentCheckBox=(JCheckBox)((Field)value).getJComponent();
			System.out.println("get value for row:"+row+"  column:"+column);
			return ((Field)value).getJComponent();
		}catch(Exception ex){
			System.err.println("getTableCellEditorComponent Exception:"+ex.getMessage());
			return null;
		}
	}

	@Override
	public void cancelCellEditing() {
		ChangeEvent event=null;
		try{
			event=new ChangeEvent(new Boolean(this.currentCheckBox.isSelected()));
		}catch(Exception ex){
		}
		try{
			for(CellEditorListener element:list){
				element.editingCanceled(event);
			}
		}catch(Exception ex){
			System.err.println("Exception:"+ex.getMessage());
		}
	}

	@Override
	public Object getCellEditorValue() {
		return new Boolean(this.currentCheckBox.isSelected());
	}

	@Override
	public boolean isCellEditable(EventObject anEvent) {
		return true;
	}

	@Override
	public boolean stopCellEditing() {
		ChangeEvent event=null;
		try{
			event=new ChangeEvent(new Boolean(this.currentCheckBox.isSelected()));
		}catch(Exception ex){
		}
		try{
			for(CellEditorListener element:list){
				element.editingStopped(event);
			}
		}catch(Exception ex){
			System.err.println();
		}
		return true;
	}

	private ArrayList<CellEditorListener> list=new ArrayList<CellEditorListener>();
	@Override
	public void addCellEditorListener(CellEditorListener l) {
		list.add(l);
	}
	@Override
	public void removeCellEditorListener(CellEditorListener l) {
		list.remove(l);
	}

	@Override
	public boolean shouldSelectCell(EventObject anEvent) {
		return true;
	}
}
