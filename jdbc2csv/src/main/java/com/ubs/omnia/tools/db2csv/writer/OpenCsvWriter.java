package com.ubs.omnia.tools.db2csv.writer;

import java.io.FileWriter;
import java.io.IOException;

import au.com.bytecode.opencsv.CSVWriter;

public class OpenCsvWriter extends CsvFileWriter{
	private CSVWriter writer;
	
	public OpenCsvWriter(FileWriter fileWriter){
		this(fileWriter, CsvFileWriter.DELIMITER);
	}
	
	public OpenCsvWriter(FileWriter fileWriter, String delimiter) {
		super(fileWriter);
		this.writer=new CSVWriter(fileWriter, delimiter.charAt(0));
	}
	
	@Override
	public void write(Object[] values) throws IOException {
		this.writer.writeNext(toArrayOfString(values));
	}

	private String[] toArrayOfString(Object[] values) {
		if(values==null){
			return null;
		}
		String[] returnValue=new String[values.length];
		for(int index=0;index<returnValue.length;index++){
			returnValue[index]=objectToString(values[index]);
		}
		return returnValue;
	}

	private String objectToString(Object object) {
		if(object==null){
			return null;
		}
		return object.toString();
	}

}
