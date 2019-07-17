package com.ubs.omnia.tools.db2csv.reader;

import java.sql.Connection;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.ubs.omnia.tools.db2csv.exception.GenericConverterException;
import com.ubs.omnia.tools.db2csv.settings.Parameters;

public class DbReaderTest {
	
	private Connection connection;
	
	@BeforeClass
	public void createConnectionWithData(){
		// TODO 
		// create HSQLDB connection
		// DDL
		// DML 
	}
	
	private Parameters getParameters(){
		Parameters returnValue=new Parameters();
		// TODO 
		return returnValue; 
	}
	
	
	@Test
	public void testReadData() throws GenericConverterException{
		// given
		String query="";
		DbReader reader=new DbReader(getParameters());
		
		// when
		List<String> headers=reader.getHeaders();
		List<String> data=reader.next();
		
		// then 
		// TODO 
	}
}
