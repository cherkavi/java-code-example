package jdbc_copy.table;

import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import jdbc_copy.connection.IConnectionAware;

public class DatabaseTableModel extends DefaultTableModel{
	private static final long serialVersionUID = 1L;
	private ArrayList<Table> row=new ArrayList<Table>();
	private ArrayList<DatabaseTableModelObject> rowObject=new ArrayList<DatabaseTableModelObject>();
	private IConnectionAware sourceConnectionAware,destinationConnectionAware;

	/** модель данных, котора€ содержит все необходимые данные */
	public DatabaseTableModel(IConnectionAware sourceConnection, IConnectionAware destinationConnection){
		this.sourceConnectionAware=sourceConnection;
		this.destinationConnectionAware=destinationConnection;
	}
	
	public void addRow(Table table){
		this.row.add(table);
		this.rowObject.add(new DatabaseTableModelObject(table,this.sourceConnectionAware, this.destinationConnectionAware));
		this.fireTableDataChanged();
	}
	
	public Table getTable(int index){
		return this.row.get(index);
	}

	public void removeRow(Table table){
		int index=this.row.indexOf(table);
		if(index>=0){
			this.row.remove(index);
			this.rowObject.remove(index);
			this.fireTableDataChanged();
		}
	}
	
	public DatabaseTableModelObject getRow(int index){
		if(index<this.rowObject.size()){
			return this.rowObject.get(index);
		}else{
			return null;
		}
	}
	
	@Override
	public int getColumnCount() {
		return 1;
	}
	
	@Override
	public int getRowCount() {
		if(row==null){
			return 0;
		}else{
			return row.size();
		}
	}
	
	@Override
	public String getColumnName(int column) {
		return "“аблицы в базе данных";
	}
	
	@Override
	public Object getValueAt(int row, int column) {
		return this.rowObject.get(row);
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return true;
	}
}
