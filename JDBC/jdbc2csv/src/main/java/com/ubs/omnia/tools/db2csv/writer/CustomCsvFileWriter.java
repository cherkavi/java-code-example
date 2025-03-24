package com.ubs.omnia.tools.db2csv.writer;

import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.lang.StringUtils;

public class CustomCsvFileWriter extends CsvFileWriter{
	private final static String NEW_LINE=System.getProperty("line.separator");
	
	
	public CustomCsvFileWriter(FileWriter fileWriter) {
		this(fileWriter, CsvFileWriter.DELIMITER);
	}
	
	public CustomCsvFileWriter(FileWriter fileWriter, String delimiter) {
		super(fileWriter, delimiter);
	}

	@Override
	public void write(Object[] values) throws IOException {
		this.writer.write(createString(values));
		this.writer.write(NEW_LINE);
	}

	String createString(Object[] values) {
		StringBuilder returnValue=new StringBuilder();
		for(int index=0;index<values.length;index++){
			if(index!=0){
				returnValue.append(this.columnDelimiter);
			}
			returnValue.append(toStringColumn(values[index]));
		}
		return returnValue.toString();
	}

	static final char AMBULA='"';
	
	String toStringColumn(Object object) {
		if(object==null){
			return StringUtils.EMPTY;
		}
		if(String.class.equals(object.getClass())){
			return AMBULA+escape(object.toString())+AMBULA;
		}else{
			return object.toString();
		}
	}

	String escape(String value) {
		if(value.indexOf(AMBULA)>=0){
			StringBuilder returnValue=new StringBuilder();
			for(int index=0;index<value.length();index++){
				if(value.charAt(index)==AMBULA){
					returnValue.append(AMBULA);
				}
				returnValue.append(value.charAt(index));
			}
			return returnValue.toString();
		}else{
			return value;
		}
	}

}
