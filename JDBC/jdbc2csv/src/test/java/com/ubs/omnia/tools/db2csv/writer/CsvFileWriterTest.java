package com.ubs.omnia.tools.db2csv.writer;

import junit.framework.Assert;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

public class CsvFileWriterTest {

	@Test
	public void checkEscape(){
		// given
		CustomCsvFileWriter writer=new CustomCsvFileWriter(null);
		String value1="this is test";
		
		// when 
		String result=writer.escape(value1);
		// then
		Assert.assertTrue(StringUtils.contains(result, value1));

		// given
		String value2="test\"value\"";
		// when
		result=writer.escape(value2);
		// then
		Assert.assertEquals(result.length(), value2.length()+2);
		// Assert.assertTrue(result.charAt(0)==CustomCsvFileWriter.AMBULA);
		// Assert.assertTrue(result.charAt(result.length()-1)==CustomCsvFileWriter.AMBULA);
	}
	
	@Test
	public void checkCreateString(){
		// given
		CustomCsvFileWriter writer=new CustomCsvFileWriter(null,";");
		Object[] objectLine=new Object[]{1, "max", "3", "this \"max\" value"};
		
		// when
		String result=writer.createString(objectLine);
		// then
		Assert.assertTrue(StringUtils.contains(result, ";\"3\";"));
		Assert.assertTrue(StringUtils.startsWith(result, "1;"));
		Assert.assertTrue(StringUtils.contains(result, ";\"3\";"));
	}

	@Test
	public void checkToString(){
	}
	
}
