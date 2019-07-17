package com.cherkashyn.vitalii.parsers.spare_parts.store;

import com.cherkashyn.vitalii.office.ExcelRowWalker;
import com.cherkashyn.vitalii.office.exception.OfficeException;
import com.cherkashyn.vitalii.parsers.spare_parts.model.SourceRecord;

public class ScarabPriceExcelWalker extends ExcelRowWalker<SourceRecord>{
	private final static int COLUMN_NUMBER=2;
	private final static int COLUMN_RESULT=5;
	private int currentLine;
	
	public ScarabPriceExcelWalker(String pathToFile) throws OfficeException {
		super(pathToFile);
	}
	
	@Override
	protected void moveToBeginData() {
		this.currentLine=5;
	}

	@Override
	public boolean moveToNextRow() {
		this.currentLine++;
		return (this.getRowCount()>this.currentLine);
	}

	@Override
	public SourceRecord readCurrentRecord() throws OfficeException {
		return new SourceRecord(this.reader.read(this.currentLine, COLUMN_NUMBER), this.reader.read(this.currentLine, COLUMN_RESULT+1));
	}
	

}
