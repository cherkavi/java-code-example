package com.ubs.omnia.tools.db2csv.writer;

import java.io.IOException;

public abstract class CsvFileWriter {
	protected final java.io.FileWriter writer;
	protected String columnDelimiter;
	protected final static String DELIMITER=",";
	
	public CsvFileWriter(java.io.FileWriter fileWriter){
		this(fileWriter, DELIMITER);
	}

	public CsvFileWriter(java.io.FileWriter fileWriter, String delimiter){
		this.writer=fileWriter;
		this.columnDelimiter=delimiter;
	}
	
	public void close() throws IOException{
		this.writer.close();
	}
	

	public void flush() throws IOException{
		this.writer.flush();
	}
	
	public abstract void write(Object[] values) throws IOException;
}
