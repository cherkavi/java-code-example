package com.cherkashyn.vitalii.tools.barcode.utility;

import java.io.File;
import java.io.IOException;

import junit.framework.Assert;

import org.junit.Test;

public class BarCodeUtils2Test {

	
	@Test
	public void testWrite() throws IOException{
		// given
		String text="987654321";
		int resolution=300;
		File tempFile=File.createTempFile(BarCodeUtils2Test.class.getSimpleName(), null);
		String pathTofile=tempFile.getAbsolutePath();
		
		// when
		BarCodeUtils2.writeBarCode(text, resolution, pathTofile);
		
		// then
		File createdFile=new File(pathTofile);
		Assert.assertNotNull(createdFile);
		Assert.assertTrue(createdFile.exists());
		Assert.assertTrue(createdFile.length()>0);
	}
}
