package com.ubs.omnia.tools.generator.csv.persist;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.ubs.omnia.tools.generator.csv.domain.Column;
import com.ubs.omnia.tools.generator.csv.domain.Line;

public class TextDelimiterSaver implements Saver{
	
	private final static char SEPARATOR=';';
	private final static String END_OF_LINE=System.getProperty("line.separator");
	private File file;
	private Column[] headers;
	private BufferedWriter writer;
	
	
	public TextDelimiterSaver(String absolutePathToFile, Line line) {
		this(new File(absolutePathToFile), line);
	}
	
	public TextDelimiterSaver(File file, Line line) {
		this.file=file;
		this.headers=line.getColumns();
	}
	
	@Override
	public void init() throws IOException {
		this.writer=new BufferedWriter(new FileWriter(file));
		this.writeNext(getHeaders());
	}

	private String[] getHeaders() {
		String[] returnValue=new String[this.headers.length];
		for(int index=0;index<this.headers.length;index++){
			returnValue[index]=this.headers[index].getName();
		}
		return returnValue;
	}
	
	
	private void writeNext(Column[] columns) throws IOException{
		String[] returnValue=new String[columns.length];
		for(int index=0;index<returnValue.length;index++){
			returnValue[index]=columns[index].getGenerator().getValue();
		}
		this.writeNext(returnValue);
	}
	
	
	private void writeNext(String[] returnValue) throws IOException {
		StringBuilder value=new StringBuilder();
		for(String eachString: returnValue){
			value.append(eachString);
			value.append(SEPARATOR);
		}
		value.append(END_OF_LINE);
		this.writer.write(value.toString());
	}

	@Override
	public void add(Line line) throws IOException {
		this.writeNext(line.getColumns());
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
