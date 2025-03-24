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

public class FileWriterTest {

	@Test
	public void writeTest() throws IOException, GenericWriterException{
		File tempFile=null;
		try{
			// given
			List<String> header=Arrays.asList("one", "two", "three");
			List<String> data=Arrays.asList("1", "2", "3");
			Parameters parameters=new Parameters();
			tempFile=File.createTempFile("FileWriterTest", "temp");
			tempFile.deleteOnExit();
			parameters.setOutputUrl(tempFile.getAbsolutePath());
			parameters.setDelimiter(";");
			FileWriter writer=new FileWriter(parameters);
			
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
			Assert.assertEquals(header.size()-1, StringUtils.countMatches(lines.get(0), parameters.getDelimiter()));
			for(String eachData : data){
				Assert.assertTrue(lines.get(1).contains(eachData));
			}
		}finally{
			if(tempFile!=null){
				tempFile.delete();
			}
		}
	}
}
