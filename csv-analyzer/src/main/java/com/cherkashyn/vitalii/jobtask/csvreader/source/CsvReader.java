package com.cherkashyn.vitalii.jobtask.csvreader.source;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import com.cherkashyn.vitalii.jobtask.csvreader.exception.DataSourceException;
import com.cherkashyn.vitalii.jobtask.csvreader.exception.SourceException;
import com.cherkashyn.vitalii.jobtask.csvreader.exception.SourceRuntimeException;
import com.cherkashyn.vitalii.jobtask.csvreader.source.domain.Column;
import com.cherkashyn.vitalii.jobtask.csvreader.source.domain.Row;

public class CsvReader extends Reader{
	// private static final Logger LOGGER=LoggerFactory.getLogger(CsvReader.class);
	private BufferedReader reader;
	private Row nextRow;
	private Column<?>[] columns;
	private int skipRow=0;
	
	public CsvReader(File file, int skipRow, Column<?> ... columns) throws SourceException{
		if(!file.exists()){
			throw new SourceException("file doesn't exists");
		}
		if(columns==null || columns.length==0){
			throw new IllegalArgumentException("columns should be specified ");
		}
		this.columns=columns;
		try {
			reader=new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		} catch (FileNotFoundException e) {
			// existing was checked previously 
			throw new SourceException("can't access the file: "+file.getAbsolutePath(), e);
		}
		this.skipRow=skipRow;
	}
	
	
	@Override
	public boolean hasNext() {
		if(this.skipRow>0){
			while(this.skipRow>0){
				this.skipRow--;
				try {
					reader.readLine();
				} catch (IOException e) {
					// EOF
					return false;
				}
			}
		}
		
		String rawValue=null;
		try {
			rawValue=reader.readLine();
		} catch (IOException e) {
			throw new SourceRuntimeException("can't read next portion of data from file", e);
		}
		// check for EOF 
		if(rawValue==null){
			return false;
		}
		// parse raw data 
		try {
			this.nextRow=Reader.parseRawData(rawValue, this.columns);
		} catch (DataSourceException e) {
			throw new SourceRuntimeException("can't parse next line of data:"+rawValue, e);
		}
		return rawValue!=null;
	}

	@Override
	public Row next() {
		return this.nextRow;
	}

	@Override
	public void close() throws IOException {
		Reader.closeQuitly(this.reader);
	}
	

}
