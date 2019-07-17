package gui.swing_table;
import javax.swing.*;

import java.sql.*;
import java.util.ArrayList;
/** отображение таблицы из базы данных на 
 * визуальный компонент Swing*/
public class DatabaseTableWrap {
	/** таблица, над которой будут производиться основные операции */
	private JTable table;
	/** имена полей для отображения на заголовке */
	private String[] dataCaption;
	/** модель данных для таблицы */
	private DatabaseTableWrapModel model;
	/** массив из PrefferedWidth for table */
	private int[] columnWidth;
	/** объект, который получает данные и содержит ключи из таблицы */
	private KeyQueryExecutor keyExecutor;
	
	/** объект, который отображает таблицу из базы данных на Swing компонент JTable
	 * @param statementData - запрос к базе для получения данных, на основании кодов <i> setInteger(0) must be allowed </i>
	 * @param columnCaption - заголовки, которые нужно отображать в колонках
	 * @param columnWidth - значения размеров для колонок
	 * @param keys - набор данных для первоначального отображения ключей
	 * */
	public DatabaseTableWrap(PreparedStatement statementData,
						     String[] columnCaption,
						     int[] columnWidth,
						     ResultSet keys){
		this.dataCaption=columnCaption;
		this.columnWidth=columnWidth;
		this.keyExecutor=new KeyQueryExecutor(statementData,keys);
		createTable();
	}

	
	private void createTable(){
		// создать компоненты 
		table=new JTable();
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		try{
			this.model=new DatabaseTableWrapModel(this.keyExecutor,
												  dataCaption);
			this.table.setModel(this.model);
			for(int counter=0;counter<dataCaption.length;counter++){
				String columnName=this.table.getColumnName(counter);
				this.table.getColumn(columnName).setPreferredWidth(this.columnWidth[counter]);
				this.table.getColumn(columnName).setHeaderValue(this.dataCaption[counter]);
			}
		}catch(Exception ex){
			System.err.println("DataBaseTableWrap Exception: create Table Error:"+ex.getMessage());
		}
		
	}
	
	/** обновить данные в таблице - обновить ключи-коды на основании которых отображаются данные */
	public void updateData(ResultSet rs) throws Exception{
		this.keyExecutor.setNewKeys(rs);
		this.model.fireTableDataChanged();
		this.table.revalidate();
	}
	
	/** получить ссылку на таблицу, которая содержит все необходимые данные */
	public JTable getTable(){
		return this.table;
	}
	
	/** получить объекты из выделенного диапазона 
	 * */
	public ArrayList<ArrayList<Object>> getSelectedKeys(){
		ArrayList<ArrayList<Object>> returnValue=new ArrayList<ArrayList<Object>>();
		int[] selectedRows=this.table.getSelectedRows();
		for(int counter=0;counter<selectedRows.length;counter++){
			returnValue.add(
							this.model.getKeysByIndex(
													  this.table.convertRowIndexToModel(selectedRows[counter])
													  )
							);
		}
		return returnValue;
	}
	
	/** получить объекты из выделенного диапазона на основании имени колонки из запроса */
	public ArrayList<Object> getSelectedColumnKeys(int columnIndex){
		ArrayList<Object> returnValue=new ArrayList<Object>();
		int[] selectedRows=this.table.getSelectedRows();
		for(int counter=0;counter<selectedRows.length;counter++){
			returnValue.add(
							this.model.getKeysByIndex(
													  this.table.convertRowIndexToModel(selectedRows[counter])
													  ).get(columnIndex)
							);
		}
		return returnValue;
	}
	
	/** получить данные, которые получены на основании выделенных ключей */
	public ArrayList<ArrayList<Object>> getSelectedValues(){
		ArrayList<ArrayList<Object>> keys=this.getSelectedKeys();
		ArrayList<ArrayList<Object>> returnValue=new ArrayList<ArrayList<Object>>();
		for(int counter=0;counter<keys.size();counter++){
			ResultSet currentResultSet=null;
			try{
				currentResultSet=this.keyExecutor.getResultSetBody(keys.get(counter));
				if(currentResultSet.next()){
					ArrayList<Object> currentRecord=new ArrayList<Object>();
					int columnCount=currentResultSet.getMetaData().getColumnCount();
					for(int column=0;column<columnCount;column++){
						currentRecord.add(currentResultSet.getObject(column+1));
					}
					returnValue.add(currentRecord);
				}
			}catch(Exception ex){
				System.err.println("DatabaseTableWrap#getSelectedValues Exception:"+ex.getMessage());
			}finally{
				try{
					currentResultSet.close();
				}catch(Exception ex){};
			}
		}
		return returnValue;
	}
}
