package database.serializer;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.ResultSet;

/** объект, который выдает очередную запись, по уникальному идентификатору */
public class RecordFactory {
	/** имя таблицы */
	private String tableName;
	/** имена полей, которые следует добавить в базу данных */
	private IRecordColumn[] columns;
	/** имена полей */
	private String[] fieldNames;
	/** запрос к базе данных без последнего симовола, который нужно прибавить к строке запроса  */
	private String query;
	
	
	/** объект которые выдает очередную запись, по предварительно указанному коду 
	 * @param connection - соединение с базой данных, Connection не сохраняется в объекте
	 * @param tableName - имя таблицы из которой следует получать данные
	 * */
	public RecordFactory(String tableName, String primaryField, IRecordColumn ... columns){
		this.tableName=tableName;
		this.query="select * from "+tableName+" where "+primaryField+" in ";
		this.columns=columns;
		this.fieldNames=new String[this.columns.length];
		for(int counter=0;counter<this.columns.length;counter++){
			this.fieldNames[counter]=this.columns[counter].getColumnName();
		}
	}
	
	
	/* получить из базы данных имена всех полей, которые есть в указанной таблице 
	protected String[] getFieldNames(Connection connection, String tableName){
		String[] returnValue=null;
		try{
			ResultSet rs=connection.createStatement().executeQuery("Select * from "+tableName);
			ResultSetMetaData rsmd=rs.getMetaData();
			returnValue=new String[rsmd.getColumnCount()];
			for(int counter=0;counter<returnValue.length;counter++){
				returnValue[counter]=rsmd.getColumnName(counter+1);
			};
			rs.getStatement().close();
		}catch(Exception ex){
			System.err.println("RecordFactory#getFieldNames Exception:"+ex.getMessage());
		}
		return returnValue;
	}*/
	
	/** получить из массива int строку, в которой данные элементы разделены запятыми 
	 * @param values - массив int значение
	 * @return строку для вставки в SQL запрос 
	 * */
	private String getValuesDelimeterComma(int[] values){
		if(values!=null){
			StringBuffer returnValue=new StringBuffer();
			for(int counter=0;counter<values.length;counter++){
				returnValue.append(values[counter]);
				if(counter!=(values.length-1)){
					returnValue.append(", ");
				}
			}
			return returnValue.toString();
		}else{
			return "";
		}
	}
	
	
	/** получить объект-обертку для указанных записей из базы данных  
	 * @param uniqueId - уникальный идентификатор записи, которую нужно получить из таблицы
	 * @param connection - соединение с базой данных
	 * @return null если не удалось получить данную запись 
	 * */
	public RecordWrap getRecords(Connection connection, int ... idValues){
		RecordWrap returnValue=null;
		try{
			ResultSet rs=connection.createStatement().executeQuery(query+" ( "+this.getValuesDelimeterComma(idValues) +" )");
			returnValue=new RecordWrap(this.tableName,this.fieldNames);
			while(rs.next()){
				Object[] currentValue=new Object[this.fieldNames.length];
				for(int counter=0;counter<currentValue.length;counter++){
					currentValue[counter]=this.columns[counter].getObject(rs);
				}
				returnValue.appendRow(currentValue);
			}
			rs.getStatement().close();
		}catch(Exception ex){
			returnValue=null;
			System.err.println("RecordFactory#getRecord: "+ex.getMessage());
		}
		return returnValue;
	}
	
	/** записать объект в предоставленный поток
	 * @param output - поток для вывода данных 
	 * @param recordWrap - объект, который необходимо сохранить
	 * */
	public void writeToStream(OutputStream output, RecordWrap recordWrap) throws IOException{
		ObjectOutputStream out=new ObjectOutputStream(output);
		out.writeObject(recordWrap);
		out.flush();
	}
}
