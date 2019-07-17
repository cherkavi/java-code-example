import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.SimpleLayout;


public class TempDetectMethod {
	
	public static void main(String[] args){
		BasicConfigurator.configure();
		Logger.getRootLogger().setLevel(Level.DEBUG);
		
		try{
			Logger.getRootLogger().addAppender(new FileAppender(new PatternLayout("---%m%n"),"c:\\out.txt"));
		}catch(Exception ex){
			System.out.println("Logger was not connected");
		}
	
		Example example=new Example();
		example.setString("this is value");
		System.out.println(example.getString());
	}
}


class Example{
	private Logger logger=Logger.getLogger(this.getClass());
	
	
	private void debug(Object information){
		StackTraceElement element=(new Throwable()).getStackTrace()[1];
		logger.debug("Class: "+element.getClassName()+"  Method:"+element.getMethodName()+"   "+information);
	}
	
	
	private String value;

	public void setString(String value){
		debug("set value");
		this.value=value;
	}
	
	public String getString(){
		debug("get value");
		return this.value;
	}
	
	public Example(){
	}
}