package compare_xls.source;

import java.io.FileInputStream;
import java.util.Map;


import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import compare_xls.settings.ExcelSettings;
import compare_xls.settings.ISettings;

public class ExcelFileSource implements IGridSource{
	private Logger logger=Logger.getLogger(this.getClass());
	private ExcelSettings settings;
	private HSSFWorkbook workbook;
	private HSSFSheet sheet;
	
	public ExcelFileSource(String absolutePathToFile) throws Exception {
		logger.debug("try to open file: "+absolutePathToFile);
        workbook  = new HSSFWorkbook(new POIFSFileSystem(new FileInputStream(absolutePathToFile)));
	}
	
	/** set Excel file Settings  */
	@Override
	public void setSettings(ISettings settings){
		if(settings instanceof ExcelSettings){
			this.settings=(ExcelSettings)settings;
			this.internalRowIndex=this.settings.getStartRow()-1;
			sheet = workbook.getSheetAt(this.settings.getSheetNumber());
		}
	}
	
	@Override
	public int getMaxRowIndex() {
		return this.sheet.getLastRowNum();
	}

	@Override
	public String getStringValue(int rowIndex, int columnIndex) {
		try{
			return this.sheet.getRow(rowIndex).getCell(columnIndex).toString();
		}catch(Exception ex){
			// logger.debug("#getStringValue ("+rowIndex+","+columnIndex+") Exception:"+ex.getMessage());
			return "";
		}
	}

	@Override
	public void close() {
	}

	@Override
	public int getKeyIndex() {
		return this.settings.getPrimaryKeyColumn();
	}

	@Override
	public String[] getRecordsByKey(String keyValue) {
		for(int rowIndex=this.settings.getStartRow();rowIndex<=this.sheet.getLastRowNum();rowIndex++){
			String readedKey=this.getStringValue(rowIndex, this.getKeyIndex());
			if(readedKey!=null){
				if(readedKey.trim().equals(keyValue)){
					HSSFRow row=this.sheet.getRow(rowIndex);
					String[] returnValue=new String[row.getLastCellNum()+1];
					for(int index=0;index<returnValue.length;index++){
						returnValue[index]=this.getStringFromCell(row.getCell(index));
					}
					return  returnValue;
				}
			}
		}
		return null;
	}

	private final static String EMPTY_STRING="";
	
	private String getStringFromCell(HSSFCell cell){
		if(cell==null){
			return EMPTY_STRING;
		}else{
			return cell.toString();
		}
	}
	
	private int internalRowIndex=0;
	
	@Override
	public boolean hasNext() {
		return this.internalRowIndex<this.getMaxRowIndex();
	}

	@Override
	public String[] next() {
		this.internalRowIndex++;
		HSSFRow row=this.sheet.getRow(internalRowIndex);
		String[] returnValue=new String[row.getLastCellNum()+1];
		for(int index=0;index<returnValue.length;index++){
			returnValue[index]=this.getStringFromCell(row.getCell(index));
		}
		return returnValue;
	}

	@Override
	public Map<Integer, Integer> getCompareMap() {
		return this.settings.getCompare();
	}

}
