package cherkashin.vitaliy.db_loader.configurator.configuration.elements;

import java.sql.Connection;
import java.util.List;

/** this is reflection of Excel.Sheet */
public class Sheet {
	/** attribute "name" - name of Sheet */
	public static final String NAME = "name";
	/** attribute "table_name" - name of table  */
	public static final String TABLE_NAME = "table_name";
	/** attribute "start_row" - number of row start */
	public static final String START_ROW = "start_row";

	/** name of sheet */
	private String name;
	/** name of Database Table  */
	private String tableName;
	/** number of start of data */
	private int startRow;
	/** Columns in Sheet  */
	private List<Column> columns;
	
	/**
	 * Sheet presentation
	 * @param name2 - name of sheet
	 * @param tableName2 - name of table for fill data
	 * @param startRow - start of the data in sheet
	 */
	public Sheet(String name2, String tableName2, int startRow, List<Column> columns) {
		this.name=name2;
		this.tableName=tableName2;
		this.startRow=startRow;
		this.columns=columns;
	}
	/** name of sheet */
	public String getName() {
		return name;
	}
	/** name of sheet */
	public void setName(String name) {
		this.name = name;
	}
	
	/** name of Database Table  */
	public String getTableName() {
		return tableName;
	}
	/** name of Database Table  */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	/** Columns in Sheet  */
	public List<Column> getColumns() {
		return columns;
	}
	
	/** Columns in Sheet  */
	public void setColumns(List<Column> columns) {
		this.columns = columns;
	}
	public int getStartRow() {
		return startRow;
	}
	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}
	public boolean hasNextData() {
		// TODO Auto-generated method stub
		return false;
	}
	public void saveNextData(Connection connection) {
		// TODO Auto-generated method stub
		
	}
	public String getNextDataFirstColumn() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/** open this sheet by File */
	public void open(File currentFile) {
		// TODO Auto-generated method stub
	}
	
	/** close this sheet by File */
	public void close(File currentFile) {
		// TODO Auto-generated method stub
		
	}

	
}
