package com.cherkashyn.vitalii.jobtask.csvreader.source;

import java.io.Closeable;
import java.io.IOException;
import java.util.Iterator;

import com.cherkashyn.vitalii.jobtask.csvreader.exception.DataSourceException;
import com.cherkashyn.vitalii.jobtask.csvreader.source.domain.Column;
import com.cherkashyn.vitalii.jobtask.csvreader.source.domain.Field;
import com.cherkashyn.vitalii.jobtask.csvreader.source.domain.Row;

/**
 * read data from external sources
 * 
 * @author technik
 *
 */
public abstract class Reader implements Iterator<Row>, Closeable{
	
	protected static Row parseRawData(String rawValue, Column<?>[] columns) throws DataSourceException {
		String[] splittedData=splitData(rawValue);
		Field<?>[] fields=new Field[columns.length];
		for(int index=0;index<splittedData.length;index++){
			fields[index]=columns[index].parseData(splittedData[index]);
		}
		return new Row(columns, fields);
	}
	
	private static String[] splitData(String rawValue){
		// TODO change to escape string
		return rawValue.split(",");
	}

	protected static void closeQuitly(Closeable resource){
		if(resource==null){
			return;
		}
		try {
			resource.close();
		} catch (IOException e) {
		}
	}
}
