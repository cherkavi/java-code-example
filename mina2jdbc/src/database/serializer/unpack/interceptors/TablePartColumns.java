package database.serializer.unpack.interceptors;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import database.serializer.common.RecordWrap;
import database.serializer.unpack.interceptors.columns.ColumnInterceptor;

/** класс, который содержит список колонок, которые используются в получении данных из объекта RecordWrap при не полном копировании */
public class TablePartColumns {
	private String tableDestination;
	private String[] columns;
	/** индекс, который говорит про место деслокации в предоставленных данных указанных в данном объекте полей */
	private int[] indexes;
	/** перехватчики для данных, которые внедряются в объект базы данных  */
	private ColumnInterceptor[] columnInterceptors; 
	
	/** класс, который содержит список колонок, которые используются в получении данных из объекта RecordWrap при не полном копировании 
	 * @param tableDestination - таблица, в которую будут записаны значения 
	 * @param columns - столбцы, которые должны быть прочитаны из удаленного объекта
	 * @param columnInterceptors - столбцы перехватчики из удаленного объекта 
	 */
	public TablePartColumns(String tableDestination, 
							String[] columns, 
							ColumnInterceptor[] columnInterceptors){
		this.tableDestination=tableDestination;
		this.columns=columns;
		this.columnInterceptors=columnInterceptors;
	}
	
	/** текущее соединение с базой данных */
	private Connection connection;
	private RecordWrap recordWrap;
	private PreparedStatement preparedStatement;
	
	
	/** получить индекс строки в массиве строк
	 * @param array массив из строк
	 * @param value строка для поиска
	 * @return - вернуть -1, если объект не найден 
	 */
	private int getStringIndex(String[] array, String value){
		int returnValue=(-1);
		try{
			for(int counter=0;counter<array.length;counter++){
				if(array[counter].equalsIgnoreCase(value)){
					returnValue=counter;
					break;
				}
			}
		}catch(Exception ex){};
		return returnValue;
	}
	
	
	
	/** пересчитать объект на основании новых данных
	 * @param recordWrap
	 * @param connection
	 * @return false - если не удалось получить данные для дальнейшей работы - ошибку обработки 
	 */
	public boolean recalculateRecord(RecordWrap recordWrap, Connection connection) {
		try{
			// получить все индексы, которые бы говорили про сопоставимость имен таблиц в recordWrap, и указанным данным
			/** индексы указанных в данном объекте полей и номера их в recordWrap*/
			this.indexes=new int[this.columns.length];
			for(int counter=0;counter<this.indexes.length;counter++){
				this.indexes[counter]=this.getStringIndex(recordWrap.getFieldNames(), this.columns[counter]);
			}
			// проверить наличие имен полей интерцепторов в колонках, если их нет - добавить
			if(this.columnInterceptors!=null){
				for(int counter=0;counter<this.columnInterceptors.length;counter++){
					if(this.getStringIndex(this.columns, this.columnInterceptors[counter].getColumnName())<0){
						// данной колонки нет - добавить
						this.columns=this.addStringToArrayString(this.columns, this.columnInterceptors[counter].getColumnName());
						this.indexes=this.addIntToArrayInt(this.indexes,(-2));
					}
				}
				
			}
			// создать PreparedStatement
			String delimeter=", ";
			StringBuffer sqlNames=new StringBuffer();
			StringBuffer sqlValues=new StringBuffer();
			// место для вставки внешних полей
			// проверить чтобы все перехватчики были установлены в (-2)
			if(this.columnInterceptors!=null){
				// есть перехватчики, установить значения в -2
				for(int counter=0;counter<this.columnInterceptors.length;counter++){
					int indexInterceptor=this.getStringIndex(this.columns, this.columnInterceptors[counter].getColumnName());
					if(indexInterceptor>=0){
						// имя перехватчика найдено в устанавливающихся столбцах 
						this.indexes[indexInterceptor]=(-2);
					}
				}
			}
			
			for(int counter=0;counter<columns.length;counter++){
				if(indexes[counter]!=(-1)){
					if(indexes[counter]==(-2)){
						// данное поле должно быть обработано перехватчиком
						ColumnInterceptor interceptor=this.getInterceptorByName(this.columns[counter]);
						if(interceptor.isForDatabaseSave()){
							if(sqlNames.length()>0){
								sqlNames.append(delimeter);
								sqlValues.append(delimeter);
							};
							sqlNames.append(this.columns[counter]);
							sqlValues.append("?");
						}else{
							// данные не будут сохранены в базе данных 
						}
					}else{
						// поле без перехватчика - просто сохранить данные в базе
						if(sqlNames.length()>0){
							sqlNames.append(delimeter);
							sqlValues.append(delimeter);
						};
						sqlNames.append(this.columns[counter]);
						sqlValues.append("?");
					}
				}else{
					// нет отображения в присланных данных для текущего элемента 
				}
			}
			if(sqlNames.length()==0){
				throw new Exception("");
			}
			// сохранить connection
			this.connection=connection;
			this.recordWrap=recordWrap;
			// создать PreparedStatement
			StringBuffer query=new StringBuffer();
			query.append("insert into "+this.tableDestination+"(");
			query.append(sqlNames);
			query.append(") values(");
			query.append(sqlValues);
			query.append(")");
			this.preparedStatement=this.connection.prepareStatement(query.toString());
			return true;
		}catch(Exception ex){
			System.err.println("TablePartColumns#recalculateRecord Exception: "+ex.getMessage());
			return false;
		}
	}
	
	/** 
	 * добавить к массиву еще один элемент в виде 
	 * @param array
	 * @param value
	 * @return
	 */
	private int[] addIntToArrayInt(int[] array, int value) {
		if(array==null){
			return new int[]{value};
		}else{
			int[] returnValue=new int[array.length+1];
			for(int counter=0;counter<array.length;counter++){
				returnValue[counter]=array[counter];
			}
			returnValue[array.length]=value;
			return returnValue;
		}
	}

	/** 
	 * добавить к массиву еще один элемент в виде 
	 * @param arrayOfString
	 * @param value
	 * @return
	 */
	private String[] addStringToArrayString(String[] arrayOfString, String value) {
		if(arrayOfString==null){
			return new String[]{value};
		}else{
			String[] returnValue=new String[arrayOfString.length+1];
			for(int counter=0;counter<arrayOfString.length;counter++){
				returnValue[counter]=arrayOfString[counter];
			}
			returnValue[arrayOfString.length]=value;
			return returnValue;
		}
	}

	/** получить перехватчик по имени столбца */
	private ColumnInterceptor getInterceptorByName(String columnName){
		ColumnInterceptor returnValue=null;
		for(int counter=0;counter<this.columnInterceptors.length;counter++){
			if(this.columnInterceptors[counter].getColumnName().equalsIgnoreCase(columnName)){
				returnValue=this.columnInterceptors[counter];
				break;
			}
		}
		return returnValue;
	}
	
	/** установить в подготовленный объект новые данные 
	 * @param currentObjects - объекты, которые необходимо установить 
	 * @throws если не получилось установить данные в объект 
	 * */ 
	public void setDataToStatement(Object[] currentObjects) throws SQLException {
		this.preparedStatement.clearParameters();
		int queryPosition=0;
		for(int counter=0;counter<this.indexes.length;counter++){
			/** есть ли индекс указанного поля в объекте */
			if(this.indexes[counter]>=0){
				// нужно вставить данные
				queryPosition++;
				this.preparedStatement.setObject(queryPosition, currentObjects[this.indexes[counter]]);
			}else{
				// возможно, нужно получить данные от перехватчика 
				if(this.indexes[counter]==(-2)){
					ColumnInterceptor interceptor=this.getInterceptorByName(this.columns[counter]);
					if(interceptor.isForDatabaseSave()){
						// для сохранения в базе данных
						queryPosition++;
						Object value=interceptor.processValue(this.connection, this.recordWrap, currentObjects);
						this.preparedStatement.setObject(queryPosition, value);
					}else{
						// не для базы данных - просто обработать объект
						interceptor.processValue(this.connection, this.recordWrap, currentObjects);
					}
				}
			}
		}
	}

	/** выполнить запрос и выбросить исключение, если что-то пошло не так */
	public void executeUpdate() throws SQLException{
		preparedStatement.executeUpdate();
	}

	/** освободить все ресурсы, связанные с действиями по базе данных */
	public void freeConnection() {
		try{
			this.preparedStatement.close();
		}catch(Exception ex){};
		this.connection=null;
		this.recordWrap=null;
	}

}
