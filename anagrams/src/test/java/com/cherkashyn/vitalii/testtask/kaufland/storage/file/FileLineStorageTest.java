package com.cherkashyn.vitalii.testtask.kaufland.storage.file;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.cherkashyn.vitalii.testtask.kaufland.storage.CloseableIterator;

import junit.framework.Assert;

public class FileLineStorageTest {

	@Test
	public void checkRead() throws IOException{
		// given
		File file=new File(Thread.currentThread().getContextClassLoader().getResource("test.data").getFile());
		
		// when
		FileLineStorage storage=new FileLineStorage(file);
		CloseableIterator<String> iterator=storage.getIterator();
		
		StringBuilder result=new StringBuilder();
		while(iterator.hasNext()){
			result.append(iterator.next());
		}
		iterator.close();
		storage.close();
		String fileContent=result.toString();
		
		// then
		Assert.assertTrue(fileContent.contains("act"));
		Assert.assertTrue(fileContent.contains("cat"));
		Assert.assertTrue(fileContent.contains("tree"));
		Assert.assertTrue(fileContent.contains("race"));
		Assert.assertTrue(fileContent.contains("care"));
		Assert.assertTrue(fileContent.contains("acre"));
		Assert.assertTrue(fileContent.contains("bee"));
	}
	
	
}
