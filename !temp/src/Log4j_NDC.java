import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.NDC;


/** Example Log4j NDC ( Nested Diagnostic Context ) */
public class Log4j_NDC {
	
	public static void main(String[] args){
		BasicConfigurator.configure();
		Logger logger=Logger.getLogger("Log4j_NDC");
		logger.setLevel(Level.DEBUG);
		
		NDC.push("level 1");
		NDC.push("level 2");
		NDC.push("level 3");
		logger.debug("Begin:");
		logger.debug("End:");
		NDC.pop();
	}
}
