package write_users.data_source;

import java.io.FileInputStream;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class ExcelPoiReader {
	private HSSFWorkbook workbook;
	/**  open XLS file */
	public void openXls(String pathToFile) throws FileNotFoundException, IOException{
		POIFSFileSystem tempFile = new POIFSFileSystem(new FileInputStream(pathToFile));
        workbook    = new HSSFWorkbook(tempFile);
        
	}
	
	private int lastSheetIndex=(-1);
	private HSSFSheet sheet;
	
	/**
	 * get last row number on sheet [0..n] 
	 * @param sheetIndex
	 * @return -1 if does not found 
	 */
	public int getMaxRowIndex(int sheetIndex){
		try{
			return this.getSheetByIndex(sheetIndex).getLastRowNum();
		}catch(Exception ex){
			return -1;
		}
	}
	
	/**
	 * get last index of cell in row [0..n] 
	 * @param sheetIndex
	 * @param rowIndex
	 * @return -1 if does not found 
	 */
	public int getMaxColumnIndexInRow(int sheetIndex, int rowIndex){
		try{
			return this.getSheetByIndex(sheetIndex).getRow(rowIndex).getLastCellNum();
		}catch(Exception ex){
			return -1;
		}
	}

	/**
	 * get string as array of row 
	 * @param sheetIndex - index of sheet 
	 * @param rowIndex - index of row into sheet 
	 * @return
	 * <ul>
	 * 	<li> <b>empty array of string </b> - data does not loaded </li>
	 * 	<li> <b>array of string with elements </b> data was loaded correctly</li>
	 * </ul>
	 */
	public String[] getRowAsArrayOfString(int sheetIndex, int rowIndex){
		try{
			HSSFRow row=this.getSheetByIndex(sheetIndex).getRow(rowIndex);
			String[] returnValue=new String[row.getLastCellNum()];
			for(int counter=0;counter<returnValue.length;counter++){
				returnValue[counter]=this.getStringValueFromRow(row.getCell(counter));
			}
			return returnValue;
		}catch(Exception ex){
			return new String[]{};
		}
	}
	
	
	/**
	 * get Cell value as string 
	 * @param sheetIndex - index of sheet 
	 * @param rowIndex - row index 
	 * @param columnIndex - column index
	 * @return string representation of string or "" - empty string if does not found 
	 */
	public String getCells(int sheetIndex, int rowIndex, int columnIndex){
		try{
			HSSFSheet sheet=getSheetByIndex(sheetIndex);
			return getStringValueFromRow(sheet.getRow(rowIndex).getCell(columnIndex));
		}catch(Exception ex){
			return "";
		}
	}

	private HSSFSheet getSheetByIndex(int sheetIndex){
		if(sheetIndex!=lastSheetIndex){
			this.lastSheetIndex=sheetIndex;
			sheet=this.workbook.getSheetAt(lastSheetIndex);
		}
		return sheet;
	}
	
	private String getStringValueFromRow(HSSFCell cell){
		if(cell==null){
			return "";
		}
		int type=cell.getCellType();
		switch(type){
			case HSSFCell.CELL_TYPE_BLANK: {
				return "";
			} 
			case HSSFCell.CELL_TYPE_BOOLEAN: {
				return Boolean.toString(cell.getBooleanCellValue());
			} 
			case HSSFCell.CELL_TYPE_ERROR: {
				return Byte.toString(cell.getErrorCellValue());
			} 
			case HSSFCell.CELL_TYPE_FORMULA: {
				return cell.getCellFormula();
			} 
			case HSSFCell.CELL_TYPE_NUMERIC: {
				return Integer.toString((int) Math.round(cell.getNumericCellValue()) );
			} 
			case HSSFCell.CELL_TYPE_STRING: {
				return cell.getRichStringCellValue().toString();
			} 
			default : return "";
		}
	}
	
}
