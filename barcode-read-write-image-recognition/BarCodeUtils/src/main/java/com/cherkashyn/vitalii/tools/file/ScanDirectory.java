package com.cherkashyn.vitalii.tools.file;

import java.io.File;
import java.text.MessageFormat;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.cherkashyn.vitalii.tools.file.processor.FolderProcessor;
import com.cherkashyn.vitalii.tools.file.processor.ProcessException;

public class ScanDirectory extends Thread{
	private final Logger LOGGER=Logger.getLogger(ScanDirectory.class);
	
	private final int delaySec;
	private final File directoryForScan;
	private boolean needToStop=false;
	private FolderProcessor[] processors;
	private File directoryForError;
	
	public ScanDirectory(File sourceDirectory, File errorDirectory, int delayInSec, FolderProcessor ... folderProcessors){
		this.delaySec=delayInSec;
		this.directoryForScan=sourceDirectory;
		this.processors=folderProcessors;
		this.directoryForError=errorDirectory;
		if(!directoryForScan.isDirectory()){
			throw new IllegalArgumentException("need to have directory as input parameter");
		}
		if(!directoryForScan.canRead()){
			throw new IllegalArgumentException("need to have read access to directory "+this.directoryForScan.getAbsolutePath());
		}
		if(!directoryForScan.canWrite()){
			throw new IllegalArgumentException("need to have write access to directory: "+this.directoryForScan.getAbsolutePath());
		}
	}
	
	@Override
	public void run() {
		while(needToStop==false){
			// work cycle
			// read directory
			for(File eachFile:this.directoryForScan.listFiles()){
				if(eachFile.isDirectory()){
					// parse each folder
					workWithFolder(eachFile);
				}
			}
			
			// sleep
			try {
				TimeUnit.SECONDS.sleep(delaySec);
			} catch (InterruptedException e) {
				LOGGER.warn("sleep before next iteration exception: "+e.getMessage(), e);
			}
		}
	}

	private void workWithFolder(File eachFolder) {
		FolderProcessor.Exchange exchange=new FolderProcessor.Exchange();
		for(FolderProcessor eachProcessor: processors){
			try {
				
				eachProcessor.process(eachFolder, exchange);
			} catch (ProcessException e) {
				moveFolderToErrorDestination(eachFolder, e);
				LOGGER.error("parse folder error: "+e.getMessage(), e);
			}
		}
	}

	private void moveFolderToErrorDestination(File eachFolder,
			ProcessException e) {
		LOGGER.warn(MessageFormat.format("move folder: {0} to error destination : {1}", eachFolder, this.directoryForError));
		// TODO need to realize
		// create text file with error
		// write file to directory
		// move current directory to error directory
	}
}

