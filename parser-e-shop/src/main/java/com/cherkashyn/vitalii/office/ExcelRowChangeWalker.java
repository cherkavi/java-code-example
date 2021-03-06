package com.cherkashyn.vitalii.office;

import com.cherkashyn.vitalii.office.excel.ExcelRW;
import com.cherkashyn.vitalii.office.exception.OfficeException;

public abstract class ExcelRowChangeWalker<R,W> {
	protected ExcelRW reader;
	
	public ExcelRowChangeWalker(String pathToFile) throws OfficeException{
		this.reader=new ExcelRW(pathToFile);
		moveToBeginData();
	}

	protected int getRowCount(){
		return this.reader.getRowCount();
	}
	
	protected int getColumnCount(){
		return this.reader.getColumnCount();
	}
	
	public abstract R readCurrentRecord() throws OfficeException;
	public abstract void writeToCurrentRecord(W value) throws OfficeException;
	
	/**
	 * find start of Data
	 */
	protected abstract void moveToBeginData();

	/**
	 * move to Next Row 
	 * @return
	 * <ul>
	 * 	<li>
	 * </ul>
	 */
	public abstract boolean moveToNextRow();

	/**
	 * close all file resources
	 */
	public void close(){
		this.reader.close();
	}
}
