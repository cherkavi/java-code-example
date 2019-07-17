package com.cherkashyn.vitalii.utility.net.downloader;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

public class ImageDownloader {
	public static void main(String[] args) throws IOException{
		new ImageDownloader().start(new SequenceDigitFormatSource("http://www.urwerk.com/uploads/animation_images/uwk_2880_00%04d.jpg", 2880, 2881), new FolderDestination("d:\\urwerk"));
	}

	private void start(SequenceDigitFormatSource sequenceDigitFormatSource, FolderDestination folderDestination) throws IOException{
		BufferedImage image;
		while(sequenceDigitFormatSource.hasNext()){
			// read
		    URL url = new URL(sequenceDigitFormatSource.next());
		    image = ImageIO.read(url);
			// write
		    String fileName=getNameOfFile(url);
			System.out.println(folderDestination.getFile(fileName));
		    ImageIO.write(image, getExtension(fileName), folderDestination.getFile(fileName));
		    try {
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
			}
		}
	}

	
	private String getExtension(String fileName) {
		if(fileName.indexOf('.')>=0){
			return fileName.substring(fileName.lastIndexOf('.')+1);
		}else{
			return "jpg";
		}
	}

	private String getNameOfFile(URL url) {
		String path=url.getFile();
		if(path.indexOf('/')>=0){
			return path.substring(path.lastIndexOf('/')+1);
		}else{
			return path;
		}
	}
	
}

class SequenceDigitFormatSource implements Iterator<String>{

	private String format;
	private int rangeBegin;
	private int rangeEnd;
	private int currentValue;

	public SequenceDigitFormatSource(String format, int rangeBegin, int rangeEnd) {
		this.format=format;
		this.rangeBegin=rangeBegin;
		this.rangeEnd=rangeEnd;
		this.currentValue=rangeBegin-1;
	}

	@Override
	public boolean hasNext() {
		return this.currentValue<this.rangeEnd;
	}

	@Override
	public String next() {
		this.currentValue++;
		return String.format(format, this.currentValue);
	}

	@Override
	public void remove() {
	}
	
	
	
}

class FolderDestination{
	private final String destinationFolder;
	
	public FolderDestination(String folder){
		this.destinationFolder=folder;
	}
	
	public File getFile(String fileName){
		return new File(destinationFolder+File.separator+fileName);
	}
}
