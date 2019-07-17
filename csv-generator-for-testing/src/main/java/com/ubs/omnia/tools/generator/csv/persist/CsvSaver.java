package com.ubs.omnia.tools.generator.csv.persist;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import au.com.bytecode.opencsv.CSVWriter;

import com.ubs.omnia.tools.generator.csv.domain.Column;
import com.ubs.omnia.tools.generator.csv.domain.Line;

public class CsvSaver implements Saver{
	
	private final static char SEPARATOR=';';
	private File file;
	private Column[] headers;
	private CSVWriter writer;
	
	
	public CsvSaver(String absolutePathToFile, Line line) {
		this(new File(absolutePathToFile), line);
	}
	
	public CsvSaver(File file, Line line) {
		this.file=file;
		this.headers=line.getColumns();
	}
	
	@Override
	public void init() throws IOException {
		this.writer=new CSVWriter(new FileWriter(file), SEPARATOR);
		this.writer.writeNext(getHeaders());
	}

	private String[] getHeaders() {
		String[] returnValue=new String[this.headers.length];
		for(int index=0;index<this.headers.length;index++){
			returnValue[index]=this.headers[index].getName();
		}
		return returnValue;
	}
	
	
	private void writeColumns(Column[] columns){
		String[] returnValue=new String[columns.length];
		for(int index=0;index<returnValue.length;index++){
			returnValue[index]=columns[index].getGenerator().getValue();
		}
		this.writer.writeNext(returnValue);
	}
	
	
	@Override
	public void add(Line line) {
		this.writeColumns(line.getColumns());
	}

	
	@Override
	public void destroy() {
		try {
			if(this.writer!=null){
				this.writer.close();
			}
		} catch (IOException e) {
		}
	}

}
