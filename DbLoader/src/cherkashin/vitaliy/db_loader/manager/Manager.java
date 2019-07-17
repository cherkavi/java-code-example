package cherkashin.vitaliy.db_loader.manager;

import java.sql.Connection;
import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import cherkashin.vitaliy.db_loader.configurator.XmlConfiguration;
import cherkashin.vitaliy.db_loader.configurator.configuration.Configuration;
import cherkashin.vitaliy.db_loader.configurator.configuration.elements.File;
import cherkashin.vitaliy.db_loader.configurator.configuration.elements.Sheet;
import cherkashin.vitaliy.db_loader.exception.EDbLoaderException;

public class Manager {
	private Configuration configuration;
	private Logger logger=Logger.getLogger(this.getClass().getName());
	
	public Manager(Configuration configuration) {
		this.configuration=configuration;
	}
	
	public void run() throws EDbLoaderException{
		logger.debug("get Connection:");
		Connection connection=null; //this.configuration.getConnector().getConnection();
		List<File> fileList=this.configuration.getFiles();
		for(int fileIndex=0;fileIndex<fileList.size();fileIndex++){
			File currentFile=fileList.get(fileIndex);
			logger.debug("Open File:"+currentFile.getPath());
			currentFile.open();
			logger.debug("   Get Sheets from File: "+currentFile.getSheets().size());
			for(int sheetIndex=0;sheetIndex<currentFile.getSheets().size();sheetIndex++){
				Sheet currentSheet=currentFile.getSheets().get(sheetIndex);
				logger.debug("      currentSheet:"+currentSheet.getName());
				currentSheet.open(currentFile);
				while(currentSheet.hasNextData()){
					logger.debug("         nextDataId:"+currentSheet.getNextDataFirstColumn());
					currentSheet.saveNextData(connection);
				}
				currentSheet.close(currentFile);
			}
			logger.debug("Close File:"+currentFile.getPath());
			currentFile.close();
		}
	}

	public static void main(String[] args) throws EDbLoaderException{
		BasicConfigurator.configure();
		System.out.println("begin");
		Configuration configuration=new XmlConfiguration().getConfiguration("TestConfiguration.xml");
		Manager manager=new Manager(configuration);
		manager.run();
		System.out.println("Configuration:"+configuration);
		System.out.println("-end-");
	}
	
}
