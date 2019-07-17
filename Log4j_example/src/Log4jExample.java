import java.util.Properties;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;


public class Log4jExample {
	private Logger field_logger=Logger.getLogger(this.getClass());
	{
		// либо конфигурирование внутри класса 
		Properties properties=new Properties();
		properties.put("log4j.rootLogger","INFO,other_log");
		properties.put("log4j.appender.other_log","org.apache.log4j.FileAppender");
		properties.put("log4j.appender.other_log.file","c:\\logging2.log");
		properties.put("log4j.appender.other_log.append","true");
		properties.put("log4j.appender.other_log.layout","org.apache.log4j.PatternLayout");
		properties.put("log4j.appender.other_log.layout.ConversionPattern","%d{yyyy-MM-dd HH:mm:ss} %p [%c] - %m%n");
		PropertyConfigurator.configure(properties);
		
		// либо конфигурирование файлом
		//PropertyConfigurator.configure("D:\\eclipse_workspace\\Log4j_example\\logger.properties ");

		// либо стандартное конфигурирование 
		//BasicConfigurator.configure();
		
		
		field_logger.setLevel(Level.DEBUG);
	}
	public static void main(String[] args){
		new Log4jExample();
	}
	
	public Log4jExample(){
		field_logger.debug(" Log4jExample ");
	}
}
