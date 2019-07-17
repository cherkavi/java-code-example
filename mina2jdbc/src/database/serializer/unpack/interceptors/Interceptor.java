package database.serializer.unpack.interceptors;

import java.sql.Connection;

import database.serializer.common.RecordWrap;

/** перехватчик для определнного типа таблиц */
public abstract class Interceptor {
	private String interceptorName;
	
	/** зарегестрировать перехватчик для таблицы с указанным именем */
	public Interceptor(String interceptorName){
		this.interceptorName=interceptorName;
	}
	
	/** получить имя перехватчика (таблицы в базе данных )
	 * @return вернуть имя перехватываемого объекта ( имя таблицы ) 
	 * */
	public String getInterceptorName(){
		return this.interceptorName;
	}
	
	/** проверить запись на возможность декодирования - является ли данный интерцептор зарегестрированным для данной записи */
	public boolean isValidRecord(RecordWrap recordWrap){
		return recordWrap.getTableName().equalsIgnoreCase(this.interceptorName);
	}
	
	/**
	 *  обработать полученный объект данным перехватчиком, необходимо предварительно вызвать {@link Interceptor#isValidRecord(RecordWrap)}
	 * <b>!!! данные НЕ подтверждаются Connection.commit() не делается внутри метода </b>
	 * @param recordWrap - объект, который нужно сохранить 
	 * @return 
	 * <li> true - данные успешно сохранены </li>
	 * <li> false - отмена сохранения данных </li>
	 * @throws Exception - ошибка во время сохранения данных
	 */
	public abstract boolean processRecord(RecordWrap recordWrap, Connection connection) throws Exception;
}
