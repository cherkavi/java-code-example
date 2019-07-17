package com.ubs.omnia.tools.db2csv.writer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import junit.framework.Assert;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import com.ubs.omnia.tools.db2csv.exception.GenericWriterException;
import com.ubs.omnia.tools.db2csv.settings.Parameters;

public class FixColumnFileWriterTest {

	@Test
	public void setAlignmentForString() throws GenericWriterException{
		// given
		int[] columns=new int[]{5, 5, 5};
		Boolean[] alignment=new Boolean[]{true, null, false};
		FixColumnFileWriter fileWriter=new FixColumnFileWriter(new Parameters(), columns);
		String controlValue="xxx";
		// when left pad  
		String newValue=fileWriter.setLength(controlValue, columns, alignment, 0);
		// then
		Assert.assertEquals("  "+controlValue, newValue);

		// when right pad  
		newValue=fileWriter.setLength(controlValue, columns, alignment, 2);
		// then
		Assert.assertEquals(controlValue+"  ", newValue);
	
		// when mid pad  
		newValue=fileWriter.setLength(controlValue, columns, alignment, 1);
		// then
		Assert.assertEquals(" "+controlValue+" ", newValue);
	}
	
	@Test
	public void setLengthForString() throws GenericWriterException{
		// given
		int[] columns=new int[]{3, 5, 7};
		Boolean[] alignment=new Boolean[]{true, null, false};
		
		FixColumnFileWriter fileWriter=new FixColumnFileWriter(new Parameters(), columns);
		String controlValue="xxx";
		
		for(int index=0;index<columns.length;index++){
			// when
			String newValue=fileWriter.setLength(controlValue, columns, alignment, index);
			// then
			Assert.assertEquals(columns[index], newValue.length());
		}

		// when size is not assigned
		String newValue=fileWriter.setLength(controlValue, columns, alignment, columns.length+1);
		// then 
		Assert.assertEquals(controlValue, newValue);

		// when size is 0
		columns[0]=0;
		newValue=fileWriter.setLength(controlValue, columns, alignment, 0);
		// then 
		Assert.assertEquals(controlValue, newValue);
		
		// when size is small than value
		columns[0]=2;
		newValue=fileWriter.setLength(controlValue, columns, alignment, 0);
		// then 
		Assert.assertEquals(controlValue, newValue);
		
		// when controlValue is null 
		columns[0]=3;
		newValue=fileWriter.setLength(null, columns, alignment, 0);
		// then 
		Assert.assertEquals("   ", newValue);
	}

	
	@Test
	public void writeTest() throws IOException, GenericWriterException{
		File tempFile=null;
		try{
			// given
			List<String> header=Arrays.asList("one", "two", "three");
			List<String> data=Arrays.asList("1", "2", "3");
			int[] columnsSize=new int[]{2,4};
			Parameters parameters=new Parameters();
			tempFile=File.createTempFile("FileWriterTest", "temp");
			tempFile.deleteOnExit();
			parameters.setOutputUrl(tempFile.getAbsolutePath());
			FileWriter writer=new FixColumnFileWriter(parameters, columnsSize);
			
			// when
			writer.writeHeader(header);
			writer.writeNext(data);
			writer.close();
			
			// then 
			List<String> lines=IOUtils.readLines(new FileInputStream(tempFile));
			Assert.assertNotNull(lines);
			Assert.assertEquals(2, lines.size());
			for(String eachHeader : header){
				Assert.assertTrue(lines.get(0).contains(eachHeader));
			}
			
			for(String eachData : data){
				Assert.assertTrue(lines.get(1).contains(eachData));
			}
			// check size 
			String columnValue=StringUtils.split(lines.get(1),",")[0];
			Assert.assertEquals(columnsSize[0]+2, columnValue.length());
			
		}finally{
			if(tempFile!=null){
				tempFile.delete();
			}
		}
	}
	
}
