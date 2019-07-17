package com.cherkashyn.vitalii.tools.file.processor;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;


public class MoveFolderProcessor extends FolderProcessor{
	private final File destinationFolder;
	
	public MoveFolderProcessor(File destinationFolder){
		this.destinationFolder=destinationFolder;
		if(!this.destinationFolder.isDirectory()){
			throw new IllegalArgumentException("Destination folder should be a Folder not a file: "+destinationFolder);
		}
		
	}
	
	private final static String MARKER_FILE="___MARKER___";
	private final static String FOLDER_WITHOUT_MARKER="WITHOUT_MARKER";
	
	@Override
	public void process(File directory, Exchange exchange)
			throws ProcessException {
		// create folder into destination
		File parentFolder=createSubFolder(destinationFolder, exchange.getParentId());
		
		// create folder for each basket
		for(Basket eachBasket: exchange.getBaskets()){
			File basketFolder=createSubFolder(parentFolder, eachBasket.getMarker());
			for(File eachFile:eachBasket.getFiles()){
				moveFile(eachFile, basketFolder);
			}
			moveFile(eachBasket.getMarkerFile(), basketFolder, MARKER_FILE);
		}
		
		// create folder for files without baskets
		File folderWithoutMarker=createSubFolder(parentFolder, FOLDER_WITHOUT_MARKER);
		for(File eachFile: exchange.getFilesWithoutMarker()){
			moveFile(eachFile, folderWithoutMarker);
		}
		
		try {
			FileUtils.deleteDirectory(directory);
		} catch (IOException e) {
			throw new ProcessException("can't remove processed folder "+directory.getAbsolutePath());
		}
	}
	private void moveFile(File eachFile, File basketFolder) throws ProcessException {
		moveFile(eachFile, basketFolder, eachFile.getName());
	}
	
	private void moveFile(File sourceFile, File destinationFolder, String fileName) throws ProcessException {
		String destinationFilePath=getFolderPath(destinationFolder)+fileName;
		try {
			FileUtils.copyFile(sourceFile, new File(destinationFilePath));
		} catch (IOException e) {
			throw new ProcessException(MessageFormat.format("can''t copy from file source:{0} to file destination:{1} ", sourceFile.getAbsoluteFile(), destinationFilePath));
		}
		sourceFile.delete();
	}
	private File createSubFolder(File parentFolder, String name) {
		File file=new File(getFolderPath(parentFolder)+name);
		file.mkdir();
		return file;
	}
	
	private String getFolderPath(File directory){
		String directoryPath=directory.getAbsolutePath();
		if(!StringUtils.endsWith(directoryPath, File.separator)){
			directoryPath=directoryPath+File.separator;
		}
		return directoryPath;
	}

}
