package bonpay.osgi.launcher;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.Iterator;

import logger.utility.LoggerActivator;

import bonpay.osgi.launcher.executor.DatabaseExecutorAware;
import bonpay.osgi.launcher.executor.IExecutorAware;
import bonpay.osgi.launcher.settings.ISettingsAware;
import bonpay.osgi.launcher.settings.Settings;
import bonpay.osgi.launcher.settings.SettingsAware;
import bonpay.osgi.launcher.settings.SettingsEnum;
import bonpay.osgi.launcher.settings.storage.DatabaseStorage;
import bonpay.osgi.launcher.settings.storage.IStorage;
import bonpay.osgi.launcher.settings.storage.PropertiesFileStorage;
import bonpay.osgi.service.interf.IExecutor;
import bonpay.osgi.service.interf.IModuleExecutor;

import database.IConnectionAware;
import database.OracleConnectionAware;


public class EnterPoint {
	public static void main(String[] args){
		
		// saveStorage=false;
		// private static String field_path="jdbc:oracle:thin:@192.168.15.254:1521:demo";
		// private static String field_login="bc_reports";
		// private static String field_password="bc_reports";
		
		IStorage connectionSettings=new PropertiesFileStorage("Launcher.properties","logger_level=[debug, info, warn, error]  logger_file=[, empty, <path_to_file> ]");
		if(connectionSettings.getRecord("oracle.path")==null){
			connectionSettings.putRecord("oracle.path", "jdbc:oracle:thin:@192.168.15.254:1521:demo");
			// saveStorage=true;
		}
		if(connectionSettings.getRecord("oracle.login")==null){
			connectionSettings.putRecord("oracle.login", "bc_reports");
			// saveStorage=true;
		}
		if(connectionSettings.getRecord("oracle.password")==null){
			connectionSettings.putRecord("oracle.password", "bc_reports");
			// saveStorage=true;
		}
		
		String packageLogger="bonpay";
		new LoggerActivator(packageLogger, "Launcher.properties");

		//Logger.getRootLogger().setLevel(Level.DEBUG);
		//Logger.getRootLogger().addAppender(new ConsoleAppender(new PatternLayout()));
		System.out.println("begin");
		// DatabaseConnectionAware 
		//IConnectionAware connectionAware=new FirebirdConnectionAware("","jobber","SYSDBA","masterkey");
		IConnectionAware connectionAware=new OracleConnectionAware((String)connectionSettings.getRecord("oracle.path"),(String)connectionSettings.getRecord("oracle.login"), (String)connectionSettings.getRecord("oracle.password"));
		// ISettingsAware
		IStorage settingsStorage=new DatabaseStorage(connectionAware);
			//new FileStorage("settings");
		
		// boolean saveStorage=false;
		if(settingsStorage.getRecord(SettingsEnum.time_wait.toString())==null){
			settingsStorage.putRecord(SettingsEnum.time_wait.toString(), 5*1000);
			// saveStorage=true;
		}
		
		/*
		if(saveStorage==true){
			settingsStorage.saveAllRecords();
		}
		if(connectionSettings.getRecord("logger_level")==null){
			connectionSettings.putRecord("logger_level", "ERROR");
			saveStorage=true;
		}
		if(connectionSettings.getRecord("logger_file")==null){
			connectionSettings.putRecord("logger_file", "");
			saveStorage=true;
		}

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
		*/
		ISettingsAware settingsAware=new SettingsAware(new Settings(settingsStorage));
		
		// IExecutorAware
		
		IExecutorAware executorAware=new DatabaseExecutorAware(connectionAware,new ModuleExecutorAware());
		
		Launcher launcher=new Launcher(settingsAware, executorAware);
		launcher.start();
		
		// оповещение об изменении в настройках 
		// launcher.notifySettingsChanged()
		System.out.println("end");
	}
}

class ModuleExecutorAware implements IModuleExecutorAware{

	@Override
	public IModuleExecutor getModuleExecutor() {
		return null;
	}
}

class ModuleExecutor implements IModuleExecutor{
	private HashMap<String, IExecutor> listOfExecutor=new HashMap<String, IExecutor>();
	
	@Override
	public boolean addModuleExecutor(String executorName, IExecutor executor) {
		listOfExecutor.put(executorName, executor);
		return true;
	}

	@Override
	public IExecutor getExecutorByName(String executorName) {
		return this.listOfExecutor.get(executorName);
	}

	@Override
	public String[] getNameOfExecutors() {
		Iterator<String> setOfString=this.listOfExecutor.keySet().iterator();
		ArrayList<String> list=new ArrayList<String>();
		while(setOfString.hasNext()){
			list.add(setOfString.next());
		}
		return list.toArray(new String[]{});
	}

	@Override
	public boolean removeModuleExecutor(String executorName) {
		return this.listOfExecutor.remove(executorName)!=null;
	}

	@Override
	public boolean removeModuleExecutor(IExecutor executor) {
		boolean returnValue=false;
		String[] key=this.getNameOfExecutors();
		for(int counter=0;counter<key.length;counter++){
			if(this.listOfExecutor.get(key[counter])!=null){
				returnValue=true;
				this.listOfExecutor.remove(key[counter]);
			}
		}
		return returnValue;
	}
	
}