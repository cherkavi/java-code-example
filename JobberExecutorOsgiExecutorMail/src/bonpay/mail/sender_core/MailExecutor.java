package bonpay.mail.sender_core;

import java.io.FileInputStream;
import java.util.Properties;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import database.IConnectionAware;

import database.OracleConnectionAware;
import bonpay.mail.sender_core.sender.SenderThreadContainer;
import bonpay.osgi.service.interf.IExecutor;

/** объект, который запускает службу рассылки писем */
public class MailExecutor implements IExecutor{
	private IConnectionAware connectorAware;
	private SenderThreadContainer senderThreadContainer;
	private final static String oracle_path="oracle.path";
	private final static String oracle_login="oracle.login";
	private final static String oracle_password="oracle.password";

	public static void main(String[] args){
		new MailExecutor("");
	}
	
	/** объект, который запускает службу рассылки писем */
	public MailExecutor(String pathToProperties){
		try{
			Properties properties=new Properties();
			try{
				properties.load(new FileInputStream(pathToProperties));
			}catch(Exception ex){};
			String logPackage="bonpay.mail";
			// установить настройки уровня логгера  
			String propertiesLevel="executor.logger.level";
			Level level=Level.DEBUG;
			if(properties.getProperty(propertiesLevel)==null){
				level=Level.DEBUG;
			}else if(properties.getProperty(propertiesLevel).equalsIgnoreCase("debug")){
				level=Level.DEBUG;
			}else if(properties.getProperty(propertiesLevel).equalsIgnoreCase("info")){
				level=Level.INFO;			
			}else if(properties.getProperty(propertiesLevel).equalsIgnoreCase("warn")){
				level=Level.WARN;			
			}else if(properties.getProperty(propertiesLevel).equalsIgnoreCase("error")){
				level=Level.ERROR;			
			}
			Logger.getLogger(logPackage).setLevel(level);
			// установить шаблон 
			String logPattern="";
			if(properties.getProperty("executor.logger.pattern")!=null){
				logPattern=properties.getProperty("executor.logger.pattern");
			}
			// установить настройки файла 
			String propertiesFile="executor.logger.file";
			String logFileName=properties.getProperty(propertiesFile);
			if((logFileName==null)||(logFileName.equalsIgnoreCase("empty"))||(logFileName.equalsIgnoreCase("null"))||(logFileName.equalsIgnoreCase(""))){
				Logger.getLogger(logPackage).addAppender(new ConsoleAppender(new PatternLayout(logPattern)));
			}else{
				try{
					Logger.getLogger(logPackage).addAppender(new FileAppender(new PatternLayout(logPattern),logFileName));
				}catch(Exception ex){
					Logger.getLogger(logPackage).addAppender(new ConsoleAppender(new PatternLayout(logPattern)));
					System.err.println("MailExecutor logger settings Exception: "+ex.getMessage()+"  file: "+logFileName);
				}
			}
			
			// запустить задачу
			String path="jdbc:oracle:thin:@192.168.15.254:1521:demo";
			String login="bc_reports";
			String password="bc_reports";
			if(properties.getProperty(oracle_path)!=null){
				path=properties.getProperty(oracle_path);
			}
			if(properties.getProperty(oracle_login)!=null){
				login=properties.getProperty(oracle_login);
			}
			if(properties.getProperty(oracle_password)!=null){
				password=properties.getProperty(oracle_password);
			}
			connectorAware=new OracleConnectionAware(path,login,password);
			if(connectorAware.getConnection()==null){
				System.err.println("MailExecutor.Path: "+path);
				System.err.println("MailExecutor.Login: "+login);
				System.err.println("MailExecutor.Password: "+password);
				throw new Exception("Mail Executor Connection Error");
			}
			senderThreadContainer=new SenderThreadContainer(connectorAware,new LetterAware(connectorAware));
		}catch(Exception ex){
			System.err.println("MailExecutor: Excetption:"+ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	@Override
	public void execute() {
		senderThreadContainer.startAllSenderThread();
		senderThreadContainer.notifySenderSettingsChange();
		senderThreadContainer.notifyAboutNewLetter();
	}

	@Override
	public void stop() {
		senderThreadContainer.stopAllSenderThread();
	}

}
