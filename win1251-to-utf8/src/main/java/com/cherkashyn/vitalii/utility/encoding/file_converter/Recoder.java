package com.cherkashyn.vitalii.utility.encoding.file_converter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

public class Recoder 
{
	private final static String UTF8="UTF-8";
	private final static String WIN1251="windows-1251";
	
    public static void main( String[] args ) throws IOException{
    	Argument argument=getArgument(args);
    	new Recoder().processDir(argument.getSource(), argument.getDestination().getAbsolutePath());
    }
    
    
    private void processDir(File source, String pathToDestination) throws IOException {
    	createDirectory(pathToDestination);
    	for(File eachFile:source.listFiles()){
    		String destinationPath=calculateDirName(source, pathToDestination, eachFile);
    		if(eachFile.isDirectory()){
    			processDir(eachFile, destinationPath);
    		}else{
    			copyFile(eachFile, destinationPath);
    		}
    	}
	}
    
    private void copyFile(File eachFile, String destinationPath) throws IOException {
    	Reader reader=null;
    	Writer writer=null;
    	try{
        	reader=new InputStreamReader(new FileInputStream(eachFile), WIN1251);
        	writer=new OutputStreamWriter(new FileOutputStream(destinationPath), UTF8);
    		IOUtils.copy(reader, writer);
    	}finally{
    		IOUtils.closeQuietly(reader);
    		IOUtils.closeQuietly(writer);
    	}
    }
		


	private String calculateDirName(File source, String destinationPath, File eachFile) {
		String fileSubName=StringUtils.removeStart(eachFile.getAbsolutePath(), source.getAbsolutePath());
		return destinationPath+fileSubName;
	}


	private void createDirectory(String pathToDir) throws IOException{
    	if(new File(pathToDir).mkdir()==false){
    		throw new RuntimeException("can't create new file: "+pathToDir);
    	}
    }


	private static Argument getArgument(String[] args) throws RuntimeException{
		if(ArrayUtils.isEmpty(args) || args.length<2){
			throw new RuntimeException("need to set at least two parameters: <source dir> <destination dir>");
		}
		Argument returnValue=new Argument();
		returnValue.setPathToSource(args[0]);
		returnValue.setPathToDestination(args[1]);
		returnValue.validate();
		return returnValue;
	}


}

class Argument{
	private File pathToSource;
	private File pathToDestination;
	
	private final static String FOLDER_DELIMITER=System.getProperty("file.separator");
	
	private String clearFolder(String dirtyPath){
		String returnValue=StringUtils.trim(dirtyPath);
		if(!StringUtils.endsWith(returnValue, FOLDER_DELIMITER)){
			returnValue=returnValue+FOLDER_DELIMITER;
		}
		return returnValue;
	}
	
	public void validate() throws RuntimeException{
		if(this.pathToSource.getAbsolutePath().equals(this.pathToDestination.getAbsolutePath())){
			throw new RuntimeException("source folder and destination folder is the same - impossible");
		}
		if(this.pathToSource.exists()==false){
			throw new RuntimeException("source folder MUST exists");
		}
		if(this.pathToDestination.exists()){
			throw new RuntimeException("destination folder MUST NOT exists");
		}
	}
	
	public void setPathToSource(String pathToSource) {
		this.pathToSource = new File(clearFolder(pathToSource));
	}
	
	public File getSource() {
		return pathToSource;
	}

	public File getDestination() {
		return pathToDestination;
	}
	public void setPathToDestination(String pathToDestination) {
		this.pathToDestination = new File(clearFolder(pathToDestination));
	}
	
	
}
