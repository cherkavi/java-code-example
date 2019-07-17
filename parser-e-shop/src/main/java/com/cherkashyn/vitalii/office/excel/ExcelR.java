package com.cherkashyn.vitalii.office.excel;

import java.io.File;
import java.io.IOException;

import com.cherkashyn.vitalii.office.exception.OfficeException;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class ExcelR {
	private Workbook workbook;
	private Sheet sheet;
	
	private int rowCount;
	private int columnCount;
	
	public ExcelR(String pathToFile, int sheetNumber) throws OfficeException{
		this(pathToFile, (Object)sheetNumber);
	}
	
	public ExcelR(String pathToFile, String sheetName) throws OfficeException{
		this(pathToFile, (Object)sheetName);
	}

	public ExcelR(String pathToFile) throws OfficeException{
		this(pathToFile, (Object)null);
	}
	
	
	private ExcelR(String pathToFile, Object sheetNumber) throws OfficeException{
		try {
			this.workbook=Workbook.getWorkbook(new File(pathToFile));
			if(sheetNumber==null){
				sheet=workbook.getSheet(0);
			}else if (sheetNumber instanceof String){
				sheet=workbook.getSheet((String)sheetNumber);
			}else if(sheetNumber instanceof Integer){
				sheet=workbook.getSheet((Integer)sheetNumber);
			}else{
				throw new IllegalArgumentException("check second argument");
			}
			this.rowCount=this.sheet.getRows();
			this.columnCount=this.sheet.getColumns();
		} catch (BiffException e) {
			throw new OfficeException(e);
		} catch (IOException e) {
			throw new OfficeException(e);
		}
	}
	
	public String read(int row, int column){
		try{
			return this.sheet.getCell(column, row).getContents();
		}catch(RuntimeException re){
			return null;
		}
	}
	
	
	public void close(){
		try {
			if(this.workbook!=null){
				this.workbook.close();
			}
		} catch (Exception e) {
		}
	}
	
	@Override
	protected void finalize() throws Throwable {
		close();
		super.finalize();		
	}
	
	public int getRowCount(){
		return this.rowCount;
	}
	public int getColumnCount(){
		return this.columnCount;
	}
}
