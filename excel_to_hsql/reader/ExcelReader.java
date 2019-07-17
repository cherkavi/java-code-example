package excel_to_hsql.reader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;


import org.apache.log4j.Logger;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class ExcelReader implements Iterator<String[]>{
	private Logger logger=Logger.getLogger(ExcelReader.class);
	private Workbook book;
	private Sheet sheet;
	private int currentRow=1;
	private int maxRowNumber;
	private List<Column> columns;
	
	/**
	 * @param pathToExcelFile - path to Excel file 
	 * @param sheetName - name of sheet 
	 * @param columns - set of columns;
	 * @throws BiffException
	 * @throws IOException
	 */
	public ExcelReader(String pathToExcelFile, 
					   String sheetName, 
					   List<Column> columns) throws BiffException, IOException{
		book=Workbook.getWorkbook(new File(pathToExcelFile));
		sheet=book.getSheet(sheetName);
		maxRowNumber=sheet.getRows();
		List<Column> list=new ArrayList<Column>(columns);
		/*
		Collections.sort(list, new Comparator<Column>(){
			public int compare(Column arg0, Column arg1) {
				return arg0.getColumnIndex()-arg1.getColumnIndex();
			}
		});*/
		this.columns=list;
	}
	
	/**
	 * set Row for start read data
	 * @param startRow
	 */
	public void setCurrentRow(int startRow){
		this.currentRow=startRow;
	}

	/**
	 * close the book 
	 */
	public void close(){
		book.close();
	}

	public boolean hasNext() {
		return this.currentRow<this.maxRowNumber;
	}

	public String[] next() throws NoSuchElementException{
		if(hasNext()){
			Cell[] cells=this.sheet.getRow(this.currentRow);
			if(cells==null){
				logger.error("null CELLS: "+( this.currentRow-1) );
			}
			this.currentRow++;
			String[] returnValue=convertRowToString(cells);
			if(returnValue==null){
				logger.error("null value in Row: "+( this.currentRow-1) );
			}
			return returnValue;
		}else{
			return null;
		}
	}

	private final static String emptyString="";
	
	private String[] convertRowToString(Cell[] cells) {
		ArrayList<String> returnValue=new ArrayList<String>(this.columns.size());
		for(int counter=0;counter<this.columns.size();counter++){
			try{
				if(counter<cells.length){
					returnValue.add(cells[this.columns.get(counter).getColumnIndex()].getContents());
				}else{
					returnValue.add(emptyString);
				}
			}catch(Exception ex){
				logger.error("#convertRowToString Exception for Cell index:"+counter,ex);
				return null;
			}
		}
		return returnValue.toArray(new String[this.columns.size()]);
	}

	public void remove() {
	}
}
