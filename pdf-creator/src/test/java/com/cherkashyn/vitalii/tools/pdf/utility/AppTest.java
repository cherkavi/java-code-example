package com.cherkashyn.vitalii.tools.pdf.utility;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import junit.framework.Assert;

import org.junit.Test;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

public class AppTest {
	private final static String IMAGE_URL="http://www.rgagnon.com/images/javahowto.jpg";
	
	@Test
	public void testPdf() throws BadElementException, MalformedURLException, DocumentException, IOException {
		File tempFile=File.createTempFile(AppTest.class.getSimpleName(), null);
		tempFile.deleteOnExit();
		String output = tempFile.getAbsolutePath();

		Document document = new Document();
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(output));
		
		writer.open();

		document.open();
		document.add(Image.getInstance(new URL(IMAGE_URL)));
		document.newPage();
		document.add(Image.getInstance(new URL(IMAGE_URL)));
		document.newPage();
		document.close();
		
		writer.close();
		
		// then
		File createdFile=new File(tempFile.getAbsolutePath());
		Assert.assertTrue(createdFile.exists());
		Assert.assertTrue(createdFile.length()>0);
	}

}
