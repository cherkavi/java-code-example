package cherkashin.vitaliy.db_loader.manager;

import java.sql.Connection;

import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import cherkashin.vitaliy.db_loader.configurator.XmlConfiguration;
import cherkashin.vitaliy.db_loader.configurator.configuration.Configuration;
import cherkashin.vitaliy.db_loader.configurator.configuration.elements.File;
import cherkashin.vitaliy.db_loader.configurator.configuration.elements.sheet.ALoaderSheet;
import cherkashin.vitaliy.db_loader.exception.EDbLoaderException;
import cherkashin.vitaliy.db_loader.writer.DbWriter;
import cherkashin.vitaliy.db_loader.writer.IWriter;

public class Manager {
	private Configuration configuration;
	private Logger logger=Logger.getLogger(this.getClass().getName());
	
	public Manager(Configuration configuration) {
		this.configuration=configuration;
	}
	
	
	@SuppressWarnings("unchecked")
	public void run() throws EDbLoaderException{
		logger.debug("get Connection:");
		Connection connection=this.configuration.getConnector().getConnection();
		IWriter writer=new DbWriter(connection);
		List<File> fileList=this.configuration.getFiles();
		for(int fileIndex=0;fileIndex<fileList.size();fileIndex++){
			File currentFile=fileList.get(fileIndex);
			logger.info("Open File:"+currentFile.getPath());
			currentFile.open();
			logger.info("   Get Sheets from File: "+currentFile.getSheets().size());
			for(int sheetIndex=0;sheetIndex<currentFile.getSheets().size();sheetIndex++){
				ALoaderSheet<Object> currentLoaderSheet=currentFile.getSheets().get(sheetIndex);
				writer.init(currentLoaderSheet);
				logger.info("      open currentSheet:"+currentLoaderSheet.getName());
				currentLoaderSheet.setOpen(currentFile.openSheet(currentLoaderSheet));
				while(currentLoaderSheet.hasNextData()){
					logger.debug("         nextDataId:"+currentLoaderSheet.getNextDataFirstColumn());
					writer.writeData(currentLoaderSheet.getNextData());
				}
				logger.info("		close currentSheet:"+currentLoaderSheet.getName());
				writer.deInit(currentLoaderSheet);
				currentLoaderSheet.setClose(currentFile.closeSheet(currentLoaderSheet));
			}
			logger.info("Close File:"+currentFile.getPath());
			currentFile.close();
		}
		writer.applyAllChanges();
	}

	public static void main(String[] args) throws EDbLoaderException{
		BasicConfigurator.configure();
		System.out.println("begin");
		Configuration configuration=new XmlConfiguration().getConfiguration("TestConfiguration.xml");
		Manager manager=new Manager(configuration);
		manager.run();
		System.out.println("-end-");
	}
	
}
