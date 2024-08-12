package com.cherkashyn.vitaliy.bpmn.utility;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.Assert;
import org.junit.Test;

import com.cherkashyn.vitaliy.bpmn.utility.InputStreamIterator;

public class InputStreamIteratorTest {

	@Test
	public void testReader() throws IOException{
		InputStreamIterator iterator=new InputStreamIterator("/", new AcceptAllFilesFilter());
		Assert.assertTrue(getFilesCount(iterator)>0);
		Assert.assertTrue(getFilesCount(iterator)>0);
		System.out.println("---  end  --- ");
	}
	
	private int getFilesCount(InputStreamIterator iterator){
		int counter=0;
		for(Pair<String, InputStream> eachFile:iterator){
			System.out.println("next file: "+eachFile.getKey());
			counter++;
		}
		return counter;
	}
	
	
	private final static class AcceptAllFilesFilter implements FileFilter{
		@Override
		public boolean accept(File file) {
			return (file.isFile());
		}
	}
	
}
