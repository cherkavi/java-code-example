package cherkashin.vitaliy.db_loader.configurator.configuration.elements.dictionary;

public class Dictionary {
	/** attribute of tag dictionary */
	public static String DICTIONARY_ATTR_NAME="name";
	/** sub tag of dictionary - table  */
	public static String DICTIONARY_TABLE="table";
	/** sub tag of dictionary - dictionary_column */
	public static String DICTIONARY_COLUMN="dictionary_column";
	
	private String name;
	private String dbTable;
	private String dbField;

	/**
	 * Element <b>dictionary</b>
	 * @param name - name of dictionary
	 * @param dbTable - database table for dictionary
	 * @param dbField - database field of dictionary
	 */
	public Dictionary(String name, String dbTable, String dbField) {
		this.name = name;
		this.dbTable = dbTable;
		this.dbField = dbField;
	}

	/** get Name of dictionary */
	public String getName() {
		return name;
	}

	/** get Dictionary Table */
	public String getDbTable() {
		return dbTable;
	}

	/** get Dictionary Field */
	public String getDbField() {
		return dbField;
	}

	public String toString(){
		StringBuilder returnValue=new StringBuilder();
		returnValue.append("Dictionary  Name:"+this.name+"  Table:"+dbTable+"   Field:"+dbField);
		return returnValue.toString();
	}
}
