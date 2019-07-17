package database.serializer.unpack.interceptors;

import java.sql.Connection;
import java.sql.PreparedStatement;

import database.serializer.common.RecordWrap;

/** обертка для таблицы, которая полностью копирует в предоставленный Connection по имени таблицы все существующие данные */
public class TableFullCopy extends Interceptor {
	private String tableName;
	
	/** обертка для таблицы, которая полностью копирует в предоставленный Connection по имени таблицы все существующие данные 
	 * @param interceptorName - имя перехватываемой таблицы из удаленного объекта 
	 * @param tableName - имя таблицы, в которую нужно производить запись данных 
	 */
	public TableFullCopy(String interceptorName, String tableName ) {
		super(interceptorName);
		this.tableName=tableName;
	}

	/** получить имя таблицы в которую должны быть сохранены данные 
	 * @return имя таблицы, в которую будут сохранены данные 
	 * */
	public String getTableName(){
		return this.tableName;
	}
	
	@Override
	public boolean processRecord(RecordWrap recordWrap, Connection connection) throws Exception {
		// creating SQL INSERT query
		// "insert into EVENT(date_sensor, date_camera) values(?,?)"
		String delimeter=", ";
		StringBuffer sqlNames=new StringBuffer();
		StringBuffer sqlValues=new StringBuffer();
		for(int counter=0;counter<recordWrap.getFieldNames().length;counter++){
			if(sqlNames.length()>0){
				sqlNames.append(delimeter);
				sqlValues.append(delimeter);
			};
			sqlNames.append(recordWrap.getFieldNames()[counter]);
			sqlValues.append("?");
		}
		StringBuffer query=new StringBuffer();
		query.append("insert into "+this.getTableName()+"(");
		query.append(sqlNames);
		query.append(") values(");
		query.append(sqlValues);
		query.append(")");
		PreparedStatement preparedStatement=connection.prepareStatement(query.toString());
		boolean returnValue=false;
		// обработать все строки, которые есть в объекте
		for(int rowCounter=0;rowCounter<recordWrap.getRowCount();rowCounter++){
			// очистить предварительный запрос
			preparedStatement.clearParameters();
			// наполнить PreparedStatement данными 
			Object[] currentObjects=recordWrap.getObjects(rowCounter);
			for(int counter=0;counter<recordWrap.getFieldNames().length;counter++){
				preparedStatement.setObject(counter+1,currentObjects[counter]);
			}
			// отработать запрос 
			try{
				preparedStatement.executeUpdate();
			}catch(Exception ex){
				System.err.println("TableEvent#processRecord Exception:"+ex.getMessage());
				throw ex;
			}
		}
		return returnValue;
	}

}
