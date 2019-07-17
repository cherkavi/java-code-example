package cherkashin.vitaliy.db_loader.writer;

import cherkashin.vitaliy.db_loader.configurator.configuration.elements.column.Column;
import cherkashin.vitaliy.db_loader.exception.EDbLoaderException;

public class ColumnDataAdapter {
	private Column column;
	private String value;
	
	public ColumnDataAdapter(Column column, String value){
		this.column=column;
		this.value=value;
	}

	public Column getColumn() {
		return column;
	}

	public void setColumn(Column column) {
		this.column = column;
	}

	/** if format is exist - apply it */
	public Object getFormatValue() throws EDbLoaderException {
		if(this.column.getFormat()!=null){
			return this.column.getFormat().getValue(this.value);
		}
		return value;
	}
	
	/** get value as string representation */
	public String getValue(){
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
}
