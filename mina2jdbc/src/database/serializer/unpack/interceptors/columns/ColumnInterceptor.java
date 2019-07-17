package database.serializer.unpack.interceptors.columns;

import java.sql.Connection;

import database.serializer.common.RecordWrap;

/** объект, который заменяет данные при установке их для заливки в базу данных строки объекта RecordWrap */
public abstract class ColumnInterceptor {
	private String columnName;
	
	/** объект, который заменяет данные при установке их для заливки в базу данных строки объекта RecordWrap 
	 * @param columnName - имя колонки, в записи из базы, для которой создан данный обработчик
	 * */
	public ColumnInterceptor(String columnName){
		this.columnName=columnName;
	}
	
	public String getColumnName(){
		return columnName;
	}
	
	/** получить индекс значения в массиве
	 * @param fieldsName - поля, которые есть в объекте
	 * @param value - значение, которое нужно искать в объекте
	 * @return - индекс элемента, или (-1) если элемент не найден
	 */
	protected int getIndexInArray(String[] fieldsName, String value){
		int returnValue=(-1);
		for(int counter=0;counter<fieldsName.length;counter++){
			if(fieldsName[counter].equalsIgnoreCase(value)){
				returnValue=counter;
				break;
			}
		}
		return returnValue;
	}
	
	/** данное поле должно быть сохранено в базе данных
	 * @return 
	 * <li> <true> - да, поле должно быть сохранено </li>
	 * <li> <false> - нет, данные не для базы данных  </li>
	 * 
	 * */
	public abstract boolean isForDatabaseSave();
	
	/** получить объект на основании полученных данных */
	public abstract Object processValue(Connection connection, RecordWrap recordWrap, Object[] currentRow);
	
}
