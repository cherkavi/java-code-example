package com.cherkashyn.vitalii.office.excel;

import java.io.File;
import java.io.IOException;

import com.cherkashyn.vitalii.office.exception.OfficeException;

import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class ExcelRW {
	private Workbook book;
	private WritableWorkbook workbook;
	
	private WritableSheet sheet;
	private int rowCount;
	private int columnCount;
	private File fileOriginal;
	private File fileWorkCopy;
	
	public ExcelRW(String pathToFile, int sheetNumber) throws OfficeException{
		this(pathToFile, (Object)sheetNumber);
	}
	
	public ExcelRW(String pathToFile, String sheetName) throws OfficeException{
		this(pathToFile, (Object)sheetName);
	}

	public ExcelRW(String pathToFile) throws OfficeException{
		this(pathToFile, (Object)null);
	}
	
	
	private ExcelRW(String pathToFile, Object sheetNumber) throws OfficeException{
		try {
			this.fileOriginal=new File(pathToFile);
			this.book=Workbook.getWorkbook(this.fileOriginal);
			this.fileWorkCopy=new File(pathToFile+"_another");
			this.workbook=Workbook.createWorkbook(this.fileWorkCopy, book);
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
		return this.sheet.getCell(column, row).getContents();
	}
	
	public void write(int row, int column, String value) throws OfficeException{
		Label cell=new Label(column, row, value);
		try {
			this.sheet.addCell(cell);
			this.workbook.write();
		} catch (RowsExceededException e) {
			throw new OfficeException(e);
		} catch (WriteException e) {
			throw new OfficeException(e);
		} catch(IOException e){
			throw new OfficeException(e);
		}
	}
	
	public void close(){
		try {
			if(this.book!=null){
				this.book.close();
			}
		} catch (Exception e) {
		}
		try{
			if(this.workbook!=null){
				this.workbook.write();
				this.workbook.close();
			}
		}catch(Exception ex){
		}
		
		String originalFileName=this.fileOriginal.getAbsolutePath();
		this.fileOriginal.delete();
		this.fileWorkCopy.renameTo(new File(originalFileName));
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
