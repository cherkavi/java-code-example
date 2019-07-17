package tools.transformer.source_impl.jdbc;

import java.util.Arrays;

import tools.transformer.core.common_model.IModel;

public class JdbcModel implements IModel{
	private Object[] values;

	public JdbcModel(Object[] values){
		this.values=values;
	}
	
	public Object[] getObjects() {
		return this.values;
	}
	
	public String toString() {
		return Arrays.toString(values);
	}

}
