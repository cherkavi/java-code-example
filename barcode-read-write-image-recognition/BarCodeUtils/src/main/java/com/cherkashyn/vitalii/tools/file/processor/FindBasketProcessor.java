package com.cherkashyn.vitalii.tools.file.processor;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.cherkashyn.vitalii.tools.barcode.utility.BarCodeUtils;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;


public class FindBasketProcessor extends FolderProcessor{

	private final int shiftX;
	private final int shiftY;
	private final int width;
	private final int height;
	
	public FindBasketProcessor(int shiftXPercent, int shiftYPercent, int widthPercent, int heightPercent){
		this.shiftX=shiftXPercent;
		this.shiftY=shiftYPercent;
		this.width=widthPercent;
		this.height=heightPercent;
	}
	
	@Override
	public void process(File directory, Exchange exchange) throws ProcessException {
		exchange.clearBaskets();
		Basket currentBasket=null;
		for(File eachFile: orderFiles(directory.listFiles())){
			// skip if not a file 
			if(eachFile.isDirectory()){
				continue;
			}
			// try to read marker 
			String marker=readMarker(eachFile);
			if(marker!=null){
				// current file is marker
				if(currentBasket!=null){
					exchange.addBasket(currentBasket);
				}
				currentBasket=new Basket(marker, eachFile);
				continue;
			}else{
				// current file is not a marker
				if(currentBasket!=null){
					currentBasket.addFile(eachFile);
				}else{
					exchange.addFilesWithoutMarker(eachFile);
				}
			}
		}
		if(currentBasket!=null && !exchange.getBaskets().contains(currentBasket)){
			exchange.addBasket(currentBasket);
		}
	}
	
	/**
	 * @param eachFile
	 * @return
	 * <ul>
	 * 	<li><b>null</b> - barcode was not found </li>
	 * 	<li><b>text</b> - barcode was found </li>
	 * </ul>
	 * @throws ProcessException - when something wrong with barcode of file
	 */
	private String readMarker(File eachFile) throws ProcessException{
		try {
			return BarCodeUtils.readCode(getSubimage(ImageIO.read(eachFile)));
		} catch (NotFoundException e) {
			return null;
		} catch (ChecksumException e) {
			throw new ProcessException("can't recognize checksum of barcode for file: "+eachFile.getAbsolutePath());
		} catch (FormatException e) {
			throw new ProcessException("can't recognize format of barcode for file: "+eachFile.getAbsolutePath());
		} catch (IOException e) {
			throw new ProcessException("can't read a file: "+eachFile.getAbsolutePath());
		}
	}
	
	/**
	 * HARD CODE - barcode sould be less than half of width of page and less then 1/5 of height of page ( in the left upper corner )
	 * @param image
	 * @return
	 */
	private BufferedImage getSubimage(BufferedImage image){
		int shiftX=this.shiftX * (image.getWidth()/100);
		int shiftY=this.shiftY * (image.getHeight()/100);
		int width=this.width * (image.getWidth()/100);
		int height=this.height * (image.getHeight()/100);
		
		return image.getSubimage(shiftX, shiftY, width, height);
	}


}
