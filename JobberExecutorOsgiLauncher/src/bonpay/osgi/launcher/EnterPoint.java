package bonpay.osgi.launcher;

import org.apache.log4j.ConsoleAppender;



import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import bonpay.osgi.launcher.settings.ISettingsAware;
import bonpay.osgi.launcher.settings.Settings;
import bonpay.osgi.launcher.settings.SettingsAware;
import bonpay.osgi.launcher.settings.SettingsEnum;
import bonpay.osgi.launcher.settings.storage.DatabaseStorage;
import bonpay.osgi.launcher.settings.storage.IStorage;
import bonpay.osgi.launcher.settings.storage.PropertiesFileStorage;

import database.IConnectionAware;
import database.OracleConnectionAware;


public class EnterPoint {
	public static void main(String[] args){
		System.out.println("begin");
		// DatabaseConnectionAware 
		//IConnectionAware connectionAware=new FirebirdConnectionAware("","jobber","SYSDBA","masterkey");
		IConnectionAware connectionAware=new OracleConnectionAware();
		// ISettingsAware
		IStorage settingsStorage=new DatabaseStorage(connectionAware);
			//new FileStorage("settings");
		
		
		boolean saveStorage=false;
		if(settingsStorage.getRecord(SettingsEnum.time_wait.toString())==null){
			settingsStorage.putRecord(SettingsEnum.time_wait.toString(), 5*1000);
			saveStorage=true;
		}
		if(saveStorage==true){
			settingsStorage.saveAllRecords();
		}
		
		saveStorage=false;
		// private static String field_path="jdbc:oracle:thin:@192.168.15.254:1521:demo";
		// private static String field_login="bc_reports";
		// private static String field_password="bc_reports";
		
		IStorage connectionSettings=new PropertiesFileStorage("connection.properties","logger_level=[debug, info, warn, error]  logger_file=[, empty, <path_to_file> ]");
		if(connectionSettings.getRecord("oracle_server")==null){
			connectionSettings.putRecord("oracle_server", "jdbc:oracle:thin:@192.168.15.254:1521:demo");
			saveStorage=true;
		}
		if(connectionSettings.getRecord("oracle_login")==null){
			connectionSettings.putRecord("oracle_login", "bc_reports");
			saveStorage=true;
		}
		if(connectionSettings.getRecord("oracle_password")==null){
			connectionSettings.putRecord("oracle_password", "bc_reports");
			saveStorage=true;
		}
		if(connectionSettings.getRecord("logger_level")==null){
			connectionSettings.putRecord("logger_level", "ERROR");
			saveStorage=true;
		}
		if(connectionSettings.getRecord("logger_file")==null){
			connectionSettings.putRecord("logger_file", "");
			saveStorage=true;
		}
		
		String packageLogger="bonpay";
		
		Level level=Level.DEBUG;
		if( ((String)connectionSettings.getRecord("logger_level")).equalsIgnoreCase("warn")){
			level=Level.WARN;
		}
		if( ((String)connectionSettings.getRecord("logger_level")).equalsIgnoreCase("debug")){
			level=Level.DEBUG;
		}
		if( ((String)connectionSettings.getRecord("logger_level")).equalsIgnoreCase("info")){
			level=Level.INFO;
		}
		if( ((String)connectionSettings.getRecord("logger_level")).equalsIgnoreCase("error")){
			level=Level.ERROR;
		}
		Logger.getLogger(packageLogger).setLevel(level);
		String fileName=((String)connectionSettings.getRecord("logger_file")).trim();
		Logger.getLogger(packageLogger).addAppender(new ConsoleAppender(new PatternLayout()));
		if(fileName==""){
			Logger.getLogger(packageLogger).addAppender(new ConsoleAppender(new PatternLayout())); 
		}else if(fileName=="null"){
			Logger.getLogger(packageLogger).addAppender(new ConsoleAppender(new PatternLayout()));
		}else if(fileName=="empty"){
			Logger.getLogger(packageLogger).addAppender(new ConsoleAppender(new PatternLayout()));
		}else {
			try{
				Logger.getLogger(packageLogger).addAppender(new FileAppender(new PatternLayout(),fileName,true));
			}catch(Exception ex){
				System.err.println("Logger error: "+ex.getMessage());
			}
		}
		if(saveStorage==true){
			connectionSettings.saveAllRecords();
		}
		
		@SuppressWarnings("unused")
		ISettingsAware settingsAware=new SettingsAware(new Settings(settingsStorage));
		
		// IExecutorAware
		//IExecutorAware executorAware=new DatabaseExecutorAware(connectionAware);
		
		//Launcher launcher=new Launcher(settingsAware, executorAware);
		//launcher.start();
		
		// оповещение об изменении в настройках 
		// launcher.notifySettingsChanged()
		System.out.println("end");
	}
}

