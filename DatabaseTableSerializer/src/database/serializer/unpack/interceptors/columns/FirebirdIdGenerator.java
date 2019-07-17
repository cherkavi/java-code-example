package database.serializer.unpack.interceptors.columns;

import java.sql.Connection;
import java.sql.ResultSet;

import database.serializer.common.RecordWrap;

/** колонка перехватчик для генератора уникального кода на базе Firebird */
public class FirebirdIdGenerator extends ColumnInterceptor{
	private String generatorName;
	
	public FirebirdIdGenerator(String columnName, String generatorName) {
		super(columnName);
		this.generatorName=generatorName;
	}

	@Override
	public Object processValue(Connection connection, RecordWrap recordWrap, Object[] currentRow) {
		Integer returnValue=(-1);
		ResultSet rs=null;
		try{
			rs=connection.createStatement().executeQuery("select gen_id("+generatorName+",1) from rdb$database");
			rs.next();
			returnValue=rs.getInt(1);
		}catch(Exception ex){
			System.err.println("FirebirdIdGenerator#getValue Exception: "+ex.getMessage());
		}finally{
			try{
				rs.close();
			}catch(Exception ex){};
		}
		// TODO Auto-generated method stub
		return returnValue;
	}

	@Override
	public boolean isForDatabaseSave() {
		return true;
	}

}
