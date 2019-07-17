import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;


public class EnterPoint {
	static{
		BasicConfigurator.configure();
	}
	static Logger log=Logger.getRootLogger().getLogger(EnterPoint.class);
	
	public static void main(String[] args){
		log.debug("Begin");
		
		log.debug("End");
	}
}
