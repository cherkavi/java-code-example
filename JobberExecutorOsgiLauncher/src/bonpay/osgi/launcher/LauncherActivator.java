package bonpay.osgi.launcher;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;

import database.IConnectionAware;
import database.OracleConnectionAware;

import bonpay.osgi.launcher.executor.DatabaseExecutorAware;
import bonpay.osgi.launcher.executor.IExecutorAware;
import bonpay.osgi.launcher.settings.ISettingsAware;
import bonpay.osgi.launcher.settings.Settings;
import bonpay.osgi.launcher.settings.SettingsAware;
import bonpay.osgi.launcher.settings.SettingsEnum;
import bonpay.osgi.launcher.settings.storage.DatabaseStorage;
import bonpay.osgi.launcher.settings.storage.IStorage;
import bonpay.osgi.launcher.settings.storage.PropertiesFileStorage;
import bonpay.osgi.service.interf.IModuleExecutor;

public class LauncherActivator implements BundleActivator, ServiceListener, IModuleExecutorAware{
	static{
		//Logger.getLogger("database").setLevel(Level.DEBUG);
		//Logger.getLogger("database").addAppender(new ConsoleAppender(new PatternLayout()));
		//Logger.getLogger("bonpay.osgi").setLevel(Level.ERROR);
		//Logger.getLogger("bonpay.osgi").addAppender(new ConsoleAppender(new PatternLayout()));
	}
	private BundleContext context;
	/** интерфейс, для получения ModuleExecutor */
	private IModuleExecutor moduleExecutor;
	private Launcher launcher;
	
	@Override
	public void start(BundleContext context) throws Exception {
		this.context=context;
		context.addServiceListener(this);
		try{
			synchronized(this){
				moduleExecutor=(IModuleExecutor)context.getService(context.getServiceReference(IModuleExecutor.class.getName()));
			}
		}catch(Exception ex){
			System.out.println("IModuleExecutor yet not register");
		}
		this.startLauncher("Launcher.properties");
	}

	private void startLauncher(String pathToProperties){
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
		
		IStorage connectionSettings=new PropertiesFileStorage(pathToProperties,"launcher.logger.level=[debug, info, warn, error]  launcher.logger.file=[, empty, <path_to_file> ]");
		if(connectionSettings.getRecord("launcher.oracle.server")==null){
			connectionSettings.putRecord("launcher.oracle.server", "jdbc:oracle:thin:@192.168.15.254:1521:demo");
			saveStorage=true;
		}
		if(connectionSettings.getRecord("launcher.oracle.login")==null){
			connectionSettings.putRecord("launcher.oracle.login", "bc_reports");
			saveStorage=true;
		}
		if(connectionSettings.getRecord("launcher.oracle.password")==null){
			connectionSettings.putRecord("launcher.oracle.password", "bc_reports");
			saveStorage=true;
		}
		if(connectionSettings.getRecord("launcher.logger.level")==null){
			connectionSettings.putRecord("launcher.logger.level", "ERROR");
			saveStorage=true;
		}
		if(connectionSettings.getRecord("launcher.logger.file")==null){
			connectionSettings.putRecord("launcher.logger.file", "");
			saveStorage=true;
		}
		
		String packageLogger="bonpay";
		
		Level level=Level.DEBUG;
		if( ((String)connectionSettings.getRecord("launcher.logger.level")).equalsIgnoreCase("warn")){
			level=Level.WARN;
		}
		if( ((String)connectionSettings.getRecord("launcher.logger.level")).equalsIgnoreCase("debug")){
			level=Level.DEBUG;
		}
		if( ((String)connectionSettings.getRecord("launcher.logger.level")).equalsIgnoreCase("info")){
			level=Level.INFO;
		}
		if( ((String)connectionSettings.getRecord("launcher.logger.level")).equalsIgnoreCase("error")){
			level=Level.ERROR;
		}
		Logger.getLogger(packageLogger).setLevel(level);
		String fileName=((String)connectionSettings.getRecord("launcher.logger.file")).trim();
		Logger.getLogger(packageLogger).addAppender(new ConsoleAppender(new PatternLayout()));
		System.out.println("launcher.logger.file="+(String)connectionSettings.getRecord("launcher.logger.file"));
		if(fileName==null){
			Logger.getLogger(packageLogger).addAppender(new ConsoleAppender(new PatternLayout()));
		}else if(fileName.equals("")){
			Logger.getLogger(packageLogger).addAppender(new ConsoleAppender(new PatternLayout())); 
		}else if(fileName.equals("null")){
			Logger.getLogger(packageLogger).addAppender(new ConsoleAppender(new PatternLayout()));
		}else if(fileName.equals("empty")){
			Logger.getLogger(packageLogger).addAppender(new ConsoleAppender(new PatternLayout()));
		}else {
			try{
				Logger.getLogger(packageLogger).addAppender(new FileAppender(new PatternLayout(),fileName,true));
			}catch(Exception ex){
				System.err.println("Launcher Logger (launcher.logger.file:"+fileName+") init Exception: "+ex.getMessage());
			}
		}
		if(saveStorage==true){
			connectionSettings.saveAllRecords();
		}
		
		ISettingsAware settingsAware=new SettingsAware(new Settings(settingsStorage));
		
		// IExecutorAware
		IExecutorAware executorAware=new DatabaseExecutorAware(connectionAware,this);
		
		this.launcher=new Launcher(settingsAware, executorAware);
		this.launcher.start();
		
		// оповещение об изменении в настройках 
		// launcher.notifySettingsChanged()
	}
	
	@Override
	public void stop(BundleContext context) throws Exception {
		this.launcher.interrupt();
		context.removeServiceListener(this);
		this.launcher.stopThread();
	}

	@Override
	public void serviceChanged(ServiceEvent event) {
		synchronized(this){
			if((event.getType()==ServiceEvent.REGISTERED)||(event.getType()==ServiceEvent.MODIFIED)){
				// регистрация 
				String[] services=(String[])event.getServiceReference().getProperty("objectClass");
				if(indexIntoArray(services,IModuleExecutor.class.getName())>=0){
					synchronized(this){
						this.moduleExecutor=(IModuleExecutor)this.context.getService(this.context.getServiceReference(IModuleExecutor.class.getName()));
					}
				}
			}else{
				// удаление регистрации 
				String[] services=(String[])event.getServiceReference().getProperty("objectClass");
				if(indexIntoArray(services,IModuleExecutor.class.getName())>=0){
					synchronized(this){
						this.moduleExecutor=null;
					}
				}
			}
		}
	}

	private int indexIntoArray(Object[] objects, Object value){
		if((objects==null)||(value==null)){
			return -1;
		}
		for(int counter=0;counter<objects.length;counter++){
			if(objects[counter].equals(value)){
				return counter;
			}
		}
		return -1;
	}

	@Override
	public IModuleExecutor getModuleExecutor() {
		return this.moduleExecutor;
	}
	
	
}
