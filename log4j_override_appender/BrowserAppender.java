package ua.cetelem.model_loader.browser_appender;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.spi.LoggingEvent;

/**
 * class for wrap messages in console appender to  
 */
public class BrowserAppender extends ConsoleAppender{

	public void append(LoggingEvent event) {
		super.append(event);
		// create event for add "<br />" to console output  
	    LoggingEvent newEvent=new LoggingEvent(event.getFQNOfLoggerClass(),
											   event.getLogger(),
											   event.timeStamp,
											   event.getLevel(),
											   "<br />",
											   null);
		super.append(newEvent);
	}
}
