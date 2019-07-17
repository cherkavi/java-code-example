package terminal_client.gui.utility;

import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import terminal.transport.Task;

public class TaskTableModel extends AbstractTableModel{
	/** */
	private static final long serialVersionUID = 1L;
	private int field_column_count=3;
	private ArrayList<ArrayList<String>> field_data=new ArrayList<ArrayList<String>>();
	
	/** Logger DEBUG */
	protected void debug(String information){
		System.out.print(this.getClass().getName());
		System.out.print(" DEBUG: ");
	    System.out.println(information);
	}
	/** Logger ERROR */
	protected void error(String information){
		System.out.print(this.getClass().getName());
		System.out.print(" ERROR: ");
	    System.out.println(information);
	}
	
	/** создать таблицу с загруженными файлами от сервера, на основании полученного Task */
	@SuppressWarnings("unchecked")
	public TaskTableModel(Task task){
		if((task!=null)&&(task.getDataCount()>0)){
			for(int counter=0;counter<task.getDataCount();counter++){
				try{
					this.field_data.add((ArrayList<String>)task.getData(counter).getObject());
				}catch(Exception ex){
					error("Data count:"+counter+" is not ArrayList<String>:"+ex.getMessage());
				}
			}
		}else{
			debug("is empty");
		}
	}
	@Override
	public int getColumnCount() {
		return this.field_column_count;
	}

	@Override
	public int getRowCount() {
		return field_data.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if(rowIndex<this.field_data.size()){
			if(columnIndex<this.field_column_count){
				return this.field_data.get(rowIndex).get(columnIndex);
			}else{
				return "";
			}
		}else{
			return "";
		}
	}
	
    /**   статический метод для установки размеров столбцов в "готовой" таблице
     * @param table таблица в которой необходимо установить ширину столбцов
     * @param width значения ширины столбцов
     * */
    public static void setColumnWidth(JTable table,int[] width){
        for(int counter=0;counter<table.getColumnCount();counter++){
            try{
                table.getColumnModel().getColumn(counter).setPreferredWidth(width[counter]);
                //System.out.println("set "+width[counter]);
            }catch(Exception e){
                System.out.println("setColumnWidth Exception:"+e.getMessage());
            }
        }
    }

}
