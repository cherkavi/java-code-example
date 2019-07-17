package cherkashin.vitaliy.db_loader.configurator.configuration.elements.sheet;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import cherkashin.vitaliy.db_loader.configurator.configuration.elements.column.Column;
import cherkashin.vitaliy.db_loader.configurator.configuration.elements.column.ConstColumn;
import cherkashin.vitaliy.db_loader.configurator.configuration.elements.column.IColumnListHolder;
import cherkashin.vitaliy.db_loader.configurator.configuration.elements.dictionary.Dictionary;
import cherkashin.vitaliy.db_loader.exception.EDbLoaderException;
import cherkashin.vitaliy.db_loader.writer.ColumnDataAdapter;

/** this is reflection of Excel.Sheet */
public class ExcelLoaderSheet extends ALoaderSheet<jxl.Sheet>{
	private Logger logger=Logger.getLogger(this.getClass().getName());
	private IColumnListHolder columnHolder;
	
	
	
	/**
	 * Sheet presentation
	 * @param name2 - name of sheet
	 * @param tableName2 - name of table for fill data
	 * @param startRow - start of the data in sheet
	 */
	protected ExcelLoaderSheet(String name2, 
							   String tableName2, 
							   int startRow, 
							   IColumnListHolder columnHolder) {
		this.setName(name2);
		this.setTableName(tableName2);
		this.setStartRow(startRow);
		this.columnHolder=columnHolder;
	}

	/**
	 * @param name - name of sheet
	 * @param dictionaryName - dictionary name
	 * @param dictionaryValue - value for dictionary
	 * @param startRow - start of the row 
	 * @param columnHolder - columnHolder 
	 */
	protected ExcelLoaderSheet(String name,
							   Dictionary dictionary, 
							   String dictionaryValue,
							   Integer startRow, 
							   IColumnListHolder columnHolder) {
		this.setName(name);
		this.addKindOfSheet(new DictionaryKindOfSheet(dictionary, dictionaryValue));
		this.setStartRow(startRow);
		this.columnHolder=columnHolder;
	}

	private int rowCount=0;
	
	public boolean hasNextData() throws EDbLoaderException{
		try{
			return !isCellInCurrentRowEmpty(getMinColumnCount());
		}catch(Exception ex){
			return false;
			// throw new EDbLoaderException("get data from First Column Exception:"+ex.getMessage());
		}
/*
		for(int counter=0;counter<this.columns.size();counter++){
			try{
				 if(isCellInCurrentRowEmpty(this.columns.get(counter).getNumber())){
					 return false;
				 }
			}catch(ArrayIndexOutOfBoundsException ex){
				return false;
			}
		}
		return true;
		
*/		
	}
	
	private boolean isCellInCurrentRowEmpty(int columnCount) throws EDbLoaderException{
		try{
			String value=this.currentSheet.getCell(columnCount, rowCount).getContents();
			return (value==null)||(value.trim().equals(EMPTY_STRING));
		}catch(java.lang.ArrayIndexOutOfBoundsException ex){
			// end of rows
			throw ex;
		}catch(Exception ex){
			logger.error("Exception:"+ex.getMessage());
			throw new EDbLoaderException("get Cell(R:"+rowCount+", C:"+columnCount+") Exception:"+ex.getMessage());
		}
	}

	/** 
	 * <ul>
	 * 	<li> get data </li>
	 *  <li> move to next value </li>
	 * </ul>
	 * */
	public ColumnDataAdapter[] getNextData() {
		List<ColumnDataAdapter> returnValue=new ArrayList<ColumnDataAdapter>(this.getColumns().size());
		List<Column> columnList=this.getColumns();
		for(int counter=0;counter<columnList.size();counter++){
			ColumnDataAdapter currentAdapter=null;
			if(columnList.get(counter) instanceof ConstColumn){
				continue;
			}else{
				currentAdapter=new ColumnDataAdapter(columnList.get(counter),
		   			     this.currentSheet.getCell(columnList.get(counter).getNumber(), 
						 this.rowCount).getContents());
			}
			returnValue.add(currentAdapter);
			this.logger.debug("value from Column:"+currentAdapter.getValue());
		}
		for(ABridgeKindOfSheet kind:this.chainOfKindOfSheet){
			returnValue=kind.getColumnDataAdapters(returnValue);
		}
		this.rowCount++;
		return returnValue.toArray(new ColumnDataAdapter[]{});
	}
	
	/*
	private String convertDataToRowString(){
		StringBuffer returnValue=new StringBuffer();
		for(int counter=0;counter<this.getColumns().size();counter++){
			returnValue.append(this.getColumns().get(counter).getNumber()+" = "+this.currentSheet.getCell(this.getColumns().get(counter).getNumber(), rowCount).getContents());
			returnValue.append(",    ");
		}
		return returnValue.toString();
	}*/
	
	/** get data from current row, from first column <br /> 
	 * use for identification of data row
	 * */
	public String getNextDataFirstColumn() throws EDbLoaderException{
		try{
			return this.currentSheet.getCell(getMinColumnCount(), rowCount).getContents();
		}catch(Exception ex){
			logger.error("#getNextDataFirstColumn Exception:"+ex.getMessage());
			throw new EDbLoaderException("get data from First Column Exception:"+ex.getMessage());
		}
	}
	
	private int minColumn=(-1);
	
	private int getMinColumnCount(){
		if(minColumn<0){
			this.minColumn=Column.getMinColumn(this.getColumns());
		}
		return minColumn;
	}
	
	/** native Sheet */
	private jxl.Sheet currentSheet;
	/** set native sheet as Opened */
	public void setOpen(jxl.Sheet openSheet) {
		currentSheet=openSheet;
		this.rowCount=this.getStartRow();
	}

	/** set native sheet as Closed */
	public void setClose(jxl.Sheet closeSheet) {
		currentSheet=null;
		
	}

	@Override
	public List<Column> getColumns() {
		if(this.columnHolder!=null){
			try{
				if(this.chainOfKindOfSheet!=null){
					List<Column> returnValue=this.columnHolder.getColumns();
					for(ABridgeKindOfSheet kindOfSheet:this.chainOfKindOfSheet){
						returnValue=kindOfSheet.getColumns(returnValue);
					}
					return returnValue;
				}else{
					return this.columnHolder.getColumns();
				}
			}catch(EDbLoaderException ex){
				logger.error("#getColumns Exception:"+ex.getMessage());
			}
		}
		return null;
	}

	@Override
	public String getTableName() {
		String dictionaryTableName=(String)this.getProperty(DictionaryKindOfSheet.PROPERTY_DICTIONARY_TABLE_NAME);
		if(dictionaryTableName!=null){
			return dictionaryTableName;
		}
		return super.getTableName();
	}
}
