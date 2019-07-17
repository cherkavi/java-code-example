package cherkashin.vitaliy.db_loader.configurator.configuration.elements;

public class Column {
	public static final String NUMBER = "number";
	public static final String TABLE_FIELD = "table_field";
	/** number of Column on Sheet  */
	private int number;
	/** Database Table name  */
	private String dbFieldName;
	
	public Column(int number2, String tableField) {
		this.number=number2;
		this.dbFieldName=tableField;
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
	
	
}
