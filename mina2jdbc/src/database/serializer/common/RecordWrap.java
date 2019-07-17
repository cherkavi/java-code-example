package database.serializer.common;

import java.io.Serializable;
import java.util.ArrayList;

/** Обертка одной записи из базы данных 
 */
public class RecordWrap implements Serializable{
	private final static long serialVersionUID=1L;
	/** имя таблицы базы данных, которую оборачивают в обертку */
	private String tableName;
	/** имена полей базы */
	private String[] fieldNames;
	/** объекты, которые хранятся в базе в виде строк из массивов объектов */
	private ArrayList<Object[]> objects=new ArrayList<Object[]>();
	/** кол-во элементов в базе данных */
	private int columnSize;
	
	
	/** обертка одной записи из базы данных 
	 * @param tableName - имя таблицы из которой данная запись
	 * @param fieldNames - имена полей из таблицы в базе данных 
	 * */
	public RecordWrap(String tableName, String[] fieldNames){
		this.tableName=tableName;
		this.fieldNames=fieldNames;
		if(fieldNames!=null){
			this.columnSize=this.fieldNames.length;
		}
	}
	
	/** обертка одной записи из базы данных 
	 * @param tableName - имя таблицы из которой данная запись
	 * @param fieldNames - имена полей из таблицы в базе данных 
	 * */
	public RecordWrap(String tableName, String[] fieldNames, Object ... values){
		this(tableName, fieldNames);
		this.appendRow(values);
	}
	

	/** получить имя таблицы, запись которой обернута*/
	public String getTableName() {
		return tableName;
	}

	/** установить имя таблицы, запись которой обернута*/
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/** получить имена полей, которые сохранены в данном объекте*/
	public String[] getFieldNames() {
		return fieldNames;
	}

	/** установить имена полей для */
	public void setFieldNames(String[] fieldNames) {
		this.fieldNames = fieldNames;
	}


	/** получить кол-во элементов */
	public int getColumnSize() {
		return columnSize;
	}

	/** установить кол-во элементов */
	public void setColumnSize(int size) {
		this.columnSize = size;
	}

	/** 
	 * Добавить в качестве еще одной строки объектов заранее определнный массив Object[]<br>
	 * <b> Важно: проверка на размерность не производится, то есть объект просто добавляется к строкам </b>
	 * @param appendObjects - массив из добавляемых объектов 
	 */
	public void appendRow(Object[] appendObjects){
		this.objects.add(appendObjects);
	}
	
	
	/** получить кол-во строк в объекте */
	public int getRowCount(){
		return this.objects.size();
	}
	
	/** получить порцию данных на основании индекса строки 
	 * @param rowIndex - индекс строки с данными 
	 * @return массив из объектов 
	 */
	public Object[] getObjects(int rowIndex){
		if(rowIndex<this.objects.size()){
			return this.objects.get(rowIndex);
		}else{
			return null;
		}
	}
	
	/** очистить все данные в объекте */
	public void clearDataOnly(){
		this.objects.clear();
	}
	
	/** полность обнулить объект */
	public void reset(){
		this.fieldNames=null;
		this.columnSize=0;
		this.objects.clear();
		this.tableName=null;
	}
}
