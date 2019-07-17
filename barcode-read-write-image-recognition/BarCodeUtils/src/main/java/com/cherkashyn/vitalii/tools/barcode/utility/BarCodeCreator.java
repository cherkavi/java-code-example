package com.cherkashyn.vitalii.tools.barcode.utility;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;

public class BarCodeCreator {
	public static void main(String[] args) throws FileNotFoundException, IOException, WriterException{
		if(args.length<2){
			System.out.println("arguments: <text> <ouput file> ( png )");
			System.exit(1);
		}
		BarCodeUtils.writeBarCode(args[0], BarcodeFormat.CODE_39, 300, 200, args[1]);
		System.out.println("check your file: "+args[1]);
	}
}
