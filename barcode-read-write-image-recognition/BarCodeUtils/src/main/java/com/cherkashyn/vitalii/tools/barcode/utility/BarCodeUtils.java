package com.cherkashyn.vitalii.tools.barcode.utility;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;

public class BarCodeUtils {
	
	public static String readCode(String pathToFile) throws IOException, NotFoundException, ChecksumException, FormatException{
		InputStream barCodeInputStream = new FileInputStream(pathToFile);
		return readCode(ImageIO.read(barCodeInputStream));
	}
	
	public static String readCode(BufferedImage barCodeBufferedImage) throws IOException, NotFoundException, ChecksumException, FormatException{
		LuminanceSource source = new BufferedImageLuminanceSource(barCodeBufferedImage);
		BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
		Reader reader = new MultiFormatReader();
		Result result = reader.decode(bitmap);

		return result.getText();
	}
	

	
	public static void writeBarCode(String text, BarcodeFormat format, int width, int height, String pathToOutputFile) throws FileNotFoundException, IOException, WriterException{
		// (ImageIO.getWriterFormatNames() returns a list of supported formats)
		String imageFormat = "png"; // could be "gif", "tiff", "jpeg" 
		
		MultiFormatWriter barcodeWriter = new MultiFormatWriter();
		BitMatrix bitMatrix = barcodeWriter.encode(text, format, width, height);
		// Map<EncodeHintType, Object> hints = new Hashtable<EncodeHintType,Object>(2);
	    // hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");		
		MatrixToImageWriter.writeToStream(bitMatrix, imageFormat, new FileOutputStream(new File(pathToOutputFile)));
	}
	

}
