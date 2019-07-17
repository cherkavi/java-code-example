package com.cherkashyn.vitalii.tools.file.processor;

import java.io.File;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

public class FolderProcessorTest {

	@Test
	public void fileOrdering() throws ProcessException{
		// given
		FolderProcessor processor=new FolderProcessor(){
			@Override
			public void process(File directory, Exchange exchange)
					throws ProcessException {
			}
		};
		File[] files=new File[]{new File("/tmp/page_003.png"),new File("/tmp/page_005.png"),new File("/tmp/page_001.png"),new File("/tmp/page_002.png"),};
		// when
		List<File> orderedFiles=processor.orderFiles(files);
		// then
		Assert.assertEquals(files.length, orderedFiles.size());
		Assert.assertTrue(orderedFiles.get(0).getName().contains("001"));
		Assert.assertTrue(orderedFiles.get(1).getName().contains("002"));
		Assert.assertTrue(orderedFiles.get(2).getName().contains("003"));
		Assert.assertTrue(orderedFiles.get(3).getName().contains("005"));
	}
}
