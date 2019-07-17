package com.cherkashyn.vitalii.jobtask.csvreader.logger;

public class Logger {
	
	
	private Class<?> outClass;
	
	public Logger(Class<?> clazz) {
		this.outClass=clazz;
	}	
	
	public void debug(String data){
		LoggerFactory.MARKER.DEBUG.out(this.outClass, data);
	}

	public void info(String data){
		LoggerFactory.MARKER.INFO.out(this.outClass, data);
	}

	public void warn(String data){
		LoggerFactory.MARKER.WARN.out(this.outClass, data);
	}

	public void error(String data){
		LoggerFactory.MARKER.ERROR.out(this.outClass, data);
	}

}
