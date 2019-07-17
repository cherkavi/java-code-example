package database.serializer.unpack.interceptors;

import java.sql.Connection;
import database.serializer.common.RecordWrap;

/** обертка для таблицы, частично копирует только указанные изначально поля в базу данных  */
public class TablePartCopy extends Interceptor {
	private TablePartColumns columns;
	
	/** обертка для таблицы, частично копирует только указанные изначально поля в базу данных
	 * @param interceptor - имя перехватчика
	 * @param fieldsForSave
	 */
	public TablePartCopy(String interceptor, TablePartColumns columns) {
		super(interceptor);
		this.columns=columns;
	}

	@Override
	public boolean processRecord(RecordWrap recordWrap, Connection connection) throws Exception {
		// получить PreparedStatement
		if(this.columns.recalculateRecord(recordWrap, connection)){
			// пробежаться по всем строкам переданных данных 
			for(int rowCounter=0;rowCounter<recordWrap.getRowCount();rowCounter++){
				Object[] currentObjects=recordWrap.getObjects(rowCounter);
				// загрузить в PreparedStatement очередную порцию данных
				this.columns.setDataToStatement(currentObjects);
				// выполнить по базе полученный объект
				this.columns.executeUpdate();
			}
			this.columns.freeConnection();
			return true;
		}else{
			return false;
		}
	}

}
