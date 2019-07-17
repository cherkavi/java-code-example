import org.apache.log4j.Logger;

import logger.utility.LoggerActivator;


public class Test {
	public static void main(String[] args){
		System.out.println("begin");
		
		if(args.length==0){
			System.out.println("Program arguments");
			System.out.println("<iteration>  <delay>");
			System.out.println("    120       5000");
			System.out.println("-----------------");
			System.exit(1);
		}
		
		int iterationCount=120;
		if(args.length>0){
			try{
				iterationCount=Integer.parseInt(args[0]);
			}catch(Exception ex){};
		}
		System.out.println("IterationCount:"+iterationCount);

		int delay=5000;
		if(args.length>1){
			try{
				delay=Integer.parseInt(args[1]);
			}catch(Exception ex){};
		}
		System.out.println("Delay:"+delay);
		
// --->>> место активации  
		new LoggerActivator(null, "Settings.properties", delay);
		
		Logger logger=Logger.getLogger("Test");
		for(int counter=0;counter<iterationCount;counter++){
			logger.debug(counter+"  debug message");
			if((counter%3)==0){
				logger.info(counter+"  info message");
			}
			if((counter%5)==0){
				logger.warn(counter+"  warn message");
			}
			if((counter%10)==0){
				logger.error(counter+"  error message");
			}
			try{
				Thread.sleep(100);
			}catch(Exception ex){};
		}
		System.out.println("end");
		System.exit(0);
	}
}
