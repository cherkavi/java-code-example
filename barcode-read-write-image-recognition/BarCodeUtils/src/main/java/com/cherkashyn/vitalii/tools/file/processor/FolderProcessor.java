package com.cherkashyn.vitalii.tools.file.processor;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;



public abstract class FolderProcessor {
	public static class Exchange{
		private List<Basket> baskets=new LinkedList<Basket>();
		private List<File> filesWithoutMarker=new LinkedList<File>();
		
		private String parentId;
		
		public void clearBaskets(){
			this.baskets.clear();
		}
		
		public void addBasket(Basket basket){
			this.baskets.add(basket);
		}
		public List<Basket> getBaskets(){
			return this.baskets;
		}

		public void setParentIdentifier(String createParentIdentifier) {
			this.parentId=createParentIdentifier;
		}

		public String getParentId() {
			return parentId;
		}

		public void addFilesWithoutMarker(File eachFile) {
			this.filesWithoutMarker.add(eachFile);
		}

		public List<File> getFilesWithoutMarker() {
			return this.filesWithoutMarker;
		}
	}
	
	public static class Basket{
		private final String marker;
		private final File file;
		private final List<File> listOfFiles=new LinkedList<File>();
		
		public Basket(String marker, File markerFile) {
			this.marker=marker;
			this.file=markerFile;
		}
		
		public void addFile(File file){
			this.listOfFiles.add(file);
		}
		
		public String getMarker(){
			return this.marker;
		}

		public List<File> getFiles() {
			return listOfFiles;
		}

		public File getMarkerFile() {
			return this.file;
		}
	}
	
	
	public abstract void process(File directory, Exchange exchange) throws ProcessException;

	private final static String FILE_EXTENSION_DELIMITER=".";
	private final static String FILE_NUMBER_DELIMITER="_";
	/**
	 * expected names: "page_023.png"
	 * @param files
	 * @return
	 * @throws ProcessException 
	 */
	List<File> orderFiles(File[] files) throws ProcessException{
		List<File> returnValue=new ArrayList<File>(Arrays.asList(files));
		try{
			Collections.sort(returnValue, new Comparator<File>(){
				@Override
				public int compare(File firstFile, File secondFile) {
					return getNumber(firstFile.getName())-getNumber(secondFile.getName());
				}

				private int getNumber(String name) {
					return Integer.parseInt(StringUtils.substringAfterLast(StringUtils.substringBeforeLast(name, FILE_EXTENSION_DELIMITER), FILE_NUMBER_DELIMITER));
				}
				
			});
		}catch(RuntimeException ex){
			throw new ProcessException("can't parse number of file: "+files);
		}
		return returnValue;
	}
}
