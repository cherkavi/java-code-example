package cherkashin.vitaliy.db_loader.configurator.configuration.elements.column;

import java.util.List;

import cherkashin.vitaliy.db_loader.configurator.configuration.elements.format.Format;

public class Column {
	public static final String NUMBER = "number";
	public static final String TABLE_FIELD = "table_field";
	public static final String FORMAT="format_name";
	/** number of Column on Sheet  */
	private int number;
	/** Database Table name  */
	private String dbFieldName;
	/** format of column */
	private Format format;
	
	/**
	 * @param number2 - number on the Sheet
	 * @param dbFieldName - table name into database
	 * @param format - data format 
	 */
	public Column(int number2, 
				  String dbFieldName, 
				  Format format) {
		this.number=number2;
		this.dbFieldName=dbFieldName;
		this.format=format;
	}
	
	/**
	 * @param number2 - number on the Sheet
	 * @param tableField - table name into database
	 */
	public Column(int number2, String tableField) {
		this(number2, tableField, null);
	}

	/** number of Column on Sheet  */
	public int getNumber() {
		return number;
	}
	/** number of Column on Sheet  */
	public void setNumber(int number) {
		this.number = number;
	}
	
	/** Database Table name  */
	public String getDbFieldName() {
		return dbFieldName;
	}
	/** Database Table name  */
	public void setDbFieldName(String dbFieldName) {
		this.dbFieldName = dbFieldName;
	}
	
	/** get min column ( by number ) from list */
	public static int getMinColumn(List<Column> columns) {
		if((columns==null)||(columns.isEmpty())) return 0;
		int minValue=0;
		for(int counter=0;counter<columns.size();counter++){
			if(columns.get(0).getNumber()>=0){
				minValue=columns.get(0).getNumber();
			}
		}
		for(int counter=1;counter<columns.size();counter++){
			if(columns.get(counter).getNumber()>=0)
			if(columns.get(counter).getNumber()<minValue){
				minValue=columns.get(counter).getNumber();
			}
		}
		return minValue;
	}
	
	public Format getFormat() {
		return format;
	}
	
	public void setFormat(Format format) {
		this.format = format;
	}
	
	
}
