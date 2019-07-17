import java.util.ArrayList;
import java.util.List;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import org.hibernate.Session;
import org.hibernate.criterion.Order;

import database.Event;


public class DataTableModel implements TableModel{
	private ArrayList<TableModelListener> listeners=new ArrayList<TableModelListener>();
	@Override
	public void addTableModelListener(TableModelListener l) {
		listeners.add(l);
	}
	@Override
	public void removeTableModelListener(TableModelListener l) {
		listeners.remove(l);
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}

	@Override
	public int getColumnCount() {
		return 1;
	}

	@Override
	public String getColumnName(int columnIndex) {
		return "";
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}


	@Override
	public void setValueAt(Object value, int rowIndex, int columnIndex) {
	}

	private List<Event> data=new ArrayList<Event>();
	public DataTableModel(Session session){
		
	}
	
	@SuppressWarnings("unchecked")
	public void refresh(Session session){
		data.clear();
		try{
			data=(List<Event>)session.createCriteria(Event.class).addOrder(Order.desc("id")).list();
		}catch(Exception ex){
			
		}
		TableModelEvent event=new TableModelEvent(this);
		for(int counter=0;counter<listeners.size();counter++){
			listeners.get(counter).tableChanged(event);
		}
	}
	
	// ------------------------------
	@Override
	public int getRowCount() {
		return data.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return data.get(rowIndex).getId();
	}

}
