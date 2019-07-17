package svn_xml_to_db;

import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import svn_xml_to_db.console_parameters.ConsoleChecker;
import svn_xml_to_db.console_parameters.ConsoleChecker.EParameters;
import svn_xml_to_db.reader.ISourceReader;
import svn_xml_to_db.reader.XmlFileReader;
import svn_xml_to_db.transit_format.LogEntry;
import svn_xml_to_db.writer.IDestinationWriter;
import svn_xml_to_db.writer.OracleDatabaseDestination;

/**
 * Данный проект предназначен для автоматической заливки из SVN XML (пример Linux команды: svn log -r17760:17766 -v --xml >> svn_log.xml) 
 * в базу данных в таблицы: SVN_LogEntry, SVN_LogEntryFile ( имеется ключ на LogEntry )
 */
public class SvnXmlToDb {
	/** configure Log4J */
	{
		BasicConfigurator.configure();
	}
	private Logger logger=Logger.getLogger(this.getClass());
	
	/** main method  */
	public static void main(String[] args){
		new SvnXmlToDb(args).run();
	}
	
	
	
	/** arguments from Console */
	private String[] consoleArguments=null;
	public SvnXmlToDb(String[] args){
		this.consoleArguments=args;
	}
	
	
	/** run program  */
	public void run(){
		logger.trace("check arguments");
		CommandLine commandLine=null;
		try{
			commandLine= new ConsoleChecker().checkArguments(consoleArguments);
		}catch(Exception ex){
			logger.error("Input arguments Exception:"+ex.getMessage());
			System.out.println("----------Execute Exception-------------");
			System.out.println(ex.getMessage());
			System.out.println("----------------------------------------");
			return;
		}
		
		logger.trace("init object by input parameters");
		try{
			this.init(commandLine);
		}catch(Exception ex){
			System.out.println("----------Execute Exception-------------");
			System.out.println(ex.getMessage());
			System.out.println("----------------------------------------");
			return;
		}

		logger.trace("convert Xml to transit format");
		List<LogEntry> list=null;
		try{
			list=reader.getLogEntry();
			/*
			for(int counter=0;counter<list.size();counter++){
				System.out.println(counter+" : "+list.get(counter));
			}*/
		}catch(Exception ex){
			logger.error("read data from source Exception: "+ex.getMessage());
			System.out.println("----------Execute Exception-------------");
			System.out.println(ex.getMessage());
			System.out.println("----------------------------------------");
			return;
		}
		logger.trace("save Transit format to");
		try{
			this.writer.writeToDestination(list);
		}catch(Exception ex){
			logger.error("write to destination Exception: "+ex.getMessage());
			System.out.println("----------Execute Exception-------------");
			System.out.println(ex.getMessage());
			System.out.println("----------------------------------------");
			return;
		}
		logger.info("OK");
		System.out.println("OK");
	}

	private ISourceReader reader=null;
	private IDestinationWriter writer=null;
	
	
	/**  init current object by input parameters */
	private void init(CommandLine commandLine) throws Exception{
		logger.trace("read XML file for EntryLog");
		this.reader=new XmlFileReader(commandLine.getOptionValue(EParameters.svn_xml_file.name()));
		logger.trace("Connect to database ");
		String dbLogin=commandLine.getOptionValue(EParameters.db_login.name());
		String dbPassword=commandLine.getOptionValue(EParameters.db_password.name());
		String dbUrl=commandLine.getOptionValue(EParameters.db_url.name());
		this.writer=new OracleDatabaseDestination(dbUrl, dbLogin, dbPassword);
		
		if(commandLine.hasOption(EParameters.log_level.name())){
			// trace, debug, info, warn, error
			String logLevel=commandLine.getOptionValue(EParameters.log_level.name());
			if(logLevel!=null){
				logLevel=logLevel.trim().toLowerCase();
				if("trace".equals(logLevel)){
					logger.trace("logger set to TRACE ");
					Logger.getRootLogger().setLevel(Level.TRACE);
					// Logger.getLogger("svn_xml_to_db").setLevel(Level.TRACE);
				}
				if("debug".equals(logLevel)){
					logger.trace("logger set to DEBUG ");
					Logger.getRootLogger().setLevel(Level.DEBUG);
					// Logger.getLogger("svn_xml_to_db").setLevel(Level.DEBUG);
				}
				if("info".equals(logLevel)){
					logger.trace("logger set to INFO ");
					Logger.getRootLogger().setLevel(Level.INFO);
					// Logger.getLogger("svn_xml_to_db").setLevel(Level.INFO);
				}
				if("warn".equals(logLevel)){
					logger.trace("logger set to WARN");
					Logger.getRootLogger().setLevel(Level.WARN);
					// Logger.getLogger("svn_xml_to_db").setLevel(Level.WARN);
				}
				if("error".equals(logLevel)){
					logger.trace("logger set to ERROR");
					Logger.getRootLogger().setLevel(Level.ERROR);
					// Logger.getLogger("svn_xml_to_db").setLevel(Level.ERROR);
				}
			}
		}
	}
	
}
