package com.cherkashyn.vitalii.jobtask.csvreader.logger;

public class LoggerFactory {
	
	public static MARKER threshold=MARKER.DEBUG;
	
	static enum MARKER{
		DEBUG,
		INFO,
		WARN,
		ERROR;
		
		private final static String SPACE="  ";
		
		void out(Class<?> clazz, String data){
			if(threshold.ordinal()>this.ordinal()){
				return;
			}
			StringBuilder outputData=new StringBuilder();
			outputData.append(this.name());
			outputData.append(SPACE);
			outputData.append(this.name());
			outputData.append(SPACE);
			outputData.append(data);
			System.out.println(outputData.toString());
		}
	}
	
	
	public static Logger getLogger(Class<?> clazz){
		return new Logger(clazz);
	}
}
