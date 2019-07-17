package cherkashin.vitaliy.db_loader.configurator.configuration.elements.sheet;

import java.util.ArrayList;
import java.util.List;

import cherkashin.vitaliy.db_loader.configurator.configuration.elements.File;
import cherkashin.vitaliy.db_loader.configurator.configuration.elements.column.Column;
import cherkashin.vitaliy.db_loader.exception.EDbLoaderException;
import cherkashin.vitaliy.db_loader.writer.ColumnDataAdapter;
import cherkashin.vitaliy.db_loader.writer.IWriter;

public abstract class ALoaderSheet <T>{
	public enum ESheetAttributes{
		/** attribute "name" - name of Sheet */
		name,
		/** attribute "table_name" - name of table  */
		table_name,
		/** attribute "start_row" - number of row start */
		start_row,
		/** attribute "dictionary_name" - if need upload data to Dictionary */
		dictionary_name,
		/** attribute "dictionary_value" - if need upload data to Dictionary*/
		dictionary_value,
		/**  this is key for Enum {@link File.EType}*/
		file_type
	}
	/** empty string value  */
	protected static String EMPTY_STRING="";
	
	/** name of sheet */
	private String name;
	/** name of Database Table  */
	private String tableName;
	/** number of start of data */
	private int startRow;
	
	
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
	public abstract List<Column> getColumns();
	
	/** Columns in Sheet  */
	
	public int getStartRow() {
		return startRow;
	}
	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	/** 
	 * @return
	 * <ul>
	 * 	<li><b>true</b> - if data exists</li>
	 * 	<li><b>false</b> - if data not exists, ending of data</li>
	 * </ul>
	 * @throws EDbLoaderException
	 */
	public abstract boolean hasNextData() throws EDbLoaderException;
	
	/**
	 * @return
	 * data for {@link IWriter}
	 */
	public abstract ColumnDataAdapter[] getNextData();
	
	
	public abstract void setOpen(T openSheet);

	public abstract void setClose(T closeSheet);
	
	public abstract String getNextDataFirstColumn() throws EDbLoaderException;
	
	protected ArrayList<ABridgeKindOfSheet> chainOfKindOfSheet=new ArrayList<ABridgeKindOfSheet>();
	
	/** add kind of the sheet */
	protected void addKindOfSheet(ABridgeKindOfSheet kindOfSheet) {
		this.chainOfKindOfSheet.add(kindOfSheet);
	}

	public Object getProperty(String property){
		if(this.chainOfKindOfSheet!=null){
			for(ABridgeKindOfSheet chain:this.chainOfKindOfSheet){
				Object returnValue=chain.getProperty(property);
				if(returnValue!=null)return returnValue;
			}
		}
		return null;
	}

}
