package compare_xls.settings;

import java.util.Map;

/** settings for Excel file  */
public class ExcelSettings implements ISettings{
	/** number of sheet into workbook */
	private int sheetNumber;
	/** the start row for parsing data  */
	private int startRow;
	/** the column for has unique values into  */
	private int primaryKeyColumn;
	/** column for compare original and destination [original key][destination key ], may nullable in control Settings  */
	private Map<Integer, Integer> compare;
	
	public int getSheetNumber() {
		return sheetNumber;
	}
	public void setSheetNumber(int sheetNumber) {
		this.sheetNumber = sheetNumber;
	}
	/** starting from 0 */
	public int getStartRow() {
		return startRow;
	}
	/** starting from 0 */
	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}
	/** starting from 0 */
	public int getPrimaryKeyColumn() {
		return primaryKeyColumn;
	}
	/** starting from 0 */
	public void setPrimaryKeyColumn(int primaryKeyColumn) {
		this.primaryKeyColumn = primaryKeyColumn;
	}
	public Map<Integer, Integer> getCompare() {
		return compare;
	}
	public void setCompare(Map<Integer, Integer> compare) {
		this.compare = compare;
	}
	
	
}
