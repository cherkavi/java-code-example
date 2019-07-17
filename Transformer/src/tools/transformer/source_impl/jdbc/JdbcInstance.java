package tools.transformer.source_impl.jdbc;

import tools.transformer.core.instance.IColumnNames;
import tools.transformer.core.instance.IInstance;
import tools.transformer.core.instance.ISqlColumnTypes;

public class JdbcInstance implements IInstance, IColumnNames, ISqlColumnTypes {
	private String id;
	private String sql;
	private String[] columnNames=null;
	private int[] columnTypes=null;
	
	public JdbcInstance(String id, String query){
		this.id=id;
		this.sql=query;
	}
	
	public String getId() {
		return this.id;
	}

	public String getQuery(){
		return this.sql;
	}

	public void setColumnNames(String[] columns){
		this.columnNames=columns;
	}
	
	public String[] getColumnNames() {
		return this.columnNames;
	}

	public void setColumnTypes(int[] columnTypes){
		this.columnTypes=columnTypes;
	}
	
	public int[] getColumnTypes() {
		return this.columnTypes;
	}
}
