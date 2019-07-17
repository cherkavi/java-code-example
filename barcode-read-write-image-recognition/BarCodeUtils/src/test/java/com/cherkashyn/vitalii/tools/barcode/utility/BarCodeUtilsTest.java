package com.cherkashyn.vitalii.tools.barcode.utility;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import junit.framework.Assert;

import org.junit.Test;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;

public class BarCodeUtilsTest {
	private final static String TEXT_OF_CODE="00001";
	
	@Test
	public void readWriteBarCode() throws IOException, WriterException, NotFoundException, ChecksumException, FormatException{
		// given
		BarcodeFormat format=BarcodeFormat.CODE_39;
		int width=300;
		int height=200;
		File tempFile=File.createTempFile(BarCodeUtilsTest.class.getSimpleName(), null);
		tempFile.deleteOnExit();
		String pathToOutputFile=tempFile.getAbsolutePath();
		
		// when
		BarCodeUtils.writeBarCode(TEXT_OF_CODE, format, width, height, pathToOutputFile);
		// then
		File createdFile=new File(pathToOutputFile);
		Assert.assertTrue(createdFile.exists());
		Assert.assertTrue(createdFile.length()>0);

	
		// when
		String result=BarCodeUtils.readCode(pathToOutputFile);
		// then
		Assert.assertEquals(TEXT_OF_CODE, result);
	}
	
	private final static String FILE_BARCODE="barcode_marker1.png";
	private final static String FILE_TEXT="text.png";
	private final static String FILE_TEXT2="text2.png";
	
	@Test
	public void cropImageRecognizeBarCode() throws IOException, NotFoundException, ChecksumException, FormatException{
		// given
		BufferedImage croppedImage=getSubimage(ImageIO.read(this.getClass().getClassLoader().getResourceAsStream(FILE_BARCODE)));
		
		// then
		File tempFile=File.createTempFile(BarCodeUtils.class.getSimpleName(), "cropRecognizeBarCode");
		tempFile.deleteOnExit();
		ImageIO.write(croppedImage, "png", tempFile);
		
		String text=BarCodeUtils.readCode(tempFile.getAbsolutePath());
		Assert.assertEquals(TEXT_OF_CODE, text);
	}
	
	@Test(expected=NotFoundException.class)
	public void textImageWithoutBarcode() throws IOException, NotFoundException, ChecksumException, FormatException{
		// given
		BufferedImage croppedImage=getSubimage(ImageIO.read(this.getClass().getClassLoader().getResourceAsStream(FILE_TEXT)));
		
		// then
		File tempFile=File.createTempFile(BarCodeUtils.class.getSimpleName(), "textImageWithoutBarcode");
		tempFile.deleteOnExit();
		ImageIO.write(croppedImage, "png", tempFile);
		
		BarCodeUtils.readCode(tempFile.getAbsolutePath());
	}
	
	@Test(expected=NotFoundException.class)
	public void textImageWithoutBarcode2() throws IOException, NotFoundException, ChecksumException, FormatException{
		// given
		BufferedImage croppedImage=getSubimage(ImageIO.read(this.getClass().getClassLoader().getResourceAsStream(FILE_TEXT2)));
		
		// then
		File tempFile=File.createTempFile(BarCodeUtils.class.getSimpleName(), "textImageWithoutBarcode");
		tempFile.deleteOnExit();
		ImageIO.write(croppedImage, "png", tempFile);
		
		BarCodeUtils.readCode(tempFile.getAbsolutePath());
	}
	
	private BufferedImage getSubimage(BufferedImage image){
		int shiftX=0 * (image.getWidth()/100);
		int shiftY=0* (image.getHeight()/100);
		int width=50 * (image.getWidth()/100);
		int height=20 * (image.getHeight()/100);
		
		return image.getSubimage(shiftX, shiftY, width, height);
	}

}
