package com.cherkashyn.vitalii.tools.barcode.utility;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.krysalis.barcode4j.impl.code39.Code39Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;

public class BarCodeUtils2 {

	public static void writeBarCode(String text, int resolutionDpi, String outputFile) throws IOException{
		Code39Bean bean = new Code39Bean();

		//Configure the barcode generator
		bean.setModuleWidth(UnitConv.in2mm(1.0f / resolutionDpi)); //makes the narrow bar 
		                                                 //width exactly one pixel
		bean.setWideFactor(3);
		bean.doQuietZone(false);
	
		//Open output file
		OutputStream out = new FileOutputStream(new File("out.png"));
		try {
		    //Set up the canvas provider for monochrome PNG output 
		    BitmapCanvasProvider canvas = new BitmapCanvasProvider(
		            out, "image/x-png", resolutionDpi, BufferedImage.TYPE_BYTE_BINARY, false, 0);

		    //Generate the barcode
		    bean.generateBarcode(canvas, text);

		    //Signal end of generation
		    canvas.finish();
		} finally {
		    out.close();
		}
	}
}
