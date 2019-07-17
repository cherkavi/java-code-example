package table;
import java.util.LinkedList;

import javax.swing.table.AbstractTableModel;

import common.Field;
/** визуальная панель, которая содержит все поля и имеет интерфейс для манипуляций с выделенными полями */
public class FieldTableModel extends AbstractTableModel{
	private final static long serialVersionUID=1L;
	
	private LinkedList<Field> fields=new LinkedList<Field>();
	
	public FieldTableModel(Field[] fields){
		for(int counter=0;counter<fields.length;counter++){
			this.fields.add(fields[counter]);
		}
	}
	
	public boolean isCellEditable(int row, int column){
		return true;
	}
	
	@Override
	public int getColumnCount() {
		return 1;
	}

	@Override
	public int getRowCount() {
		return this.fields.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return this.fields.get(rowIndex);
	}

	public boolean moveFirst(Field element){
		int elementIndex=this.fields.indexOf(element);
		if(elementIndex>=0){
			this.fields.remove(elementIndex);
			this.fields.addFirst(element);
			this.fireTableDataChanged();
			return true;
		}else{
			System.err.println("Element is not found:"+element.getSqlFieldName());
			return false;
		}
	}
	
	public boolean moveLast(Field element){
		int elementIndex=this.fields.indexOf(element);
		if(elementIndex>=0){
			this.fields.remove(elementIndex);
			this.fields.addLast(element);
			this.fireTableDataChanged();
			return true;
		}else{
			System.err.println("Element is not found:"+element.getSqlFieldName());
			return false;
		}
		
	}

	public void moveUp(Field element){
		int position=this.fields.indexOf(element);
		if(position>0){
			this.fields.remove(position);
			this.fields.add(position-1,element);
		}else{
			// element on top or not in list
		}
		this.fireTableDataChanged();
	}
	
	public void moveDown(Field element){
		int position=this.fields.indexOf(element);
		if((position>=0)&&(position<(this.fields.size()-1))){
			this.fields.remove(position);
			this.fields.add(position+1,element);
		}else{
			// element on top or not in list
		}
		this.fireTableDataChanged();
	}
	
	/** получить строку запроса согласно расположению элементов
	 * @param tableName - имя таблицы в базе данных 
	 * @param whereString - если равен null - то не учитывается в запросе
	 * @param isDistinct - нужно ли отбирать только уникальные записи 
	 * */
	public String getQueryByData(String tableName, String whereString, boolean isDistinct){
		StringBuffer buffer=new StringBuffer();
		String fieldsNames=this.getFieldsForQuery();
		buffer.append("SELECT \n");
		if(isDistinct){
			buffer.append(" DISTINCT ");
		}
		buffer.append(fieldsNames);
		buffer.append("\n FROM \n");
		buffer.append(tableName);
		if(whereString!=null){
			buffer.append("\n WHERE "+whereString);
		}
		buffer.append("\n ORDER BY ");
		buffer.append(fieldsNames);
		return buffer.toString();
	}
	
	/** получить имена SQL полей через запятую */
	private String getFieldsForQuery(){
		StringBuffer buffer=new StringBuffer();
		for(int counter=0;counter<fields.size();counter++){
			if(fields.get(counter).isSelected()){
				buffer.append(fields.get(counter).getSqlFieldName());
				buffer.append(",");
			}
		}
		String returnValue=buffer.toString();
		return returnValue.substring(0,buffer.length()-1);
	}
	
	public int getSelectedFieldCount(){
		int returnValue=0;
		for(Field field:fields){
			if(field.isSelected()){
				returnValue++;
			}
		}
		return returnValue;
	}

	public String getSelectedFieldName(int index) {
		int counter=0;
		for(Field field:fields){
			if(field.isSelected()){
				if(index==counter){
					return field.getSqlFieldName();
				}
				counter++;
			}
			
		}
		return null;
	}
}


