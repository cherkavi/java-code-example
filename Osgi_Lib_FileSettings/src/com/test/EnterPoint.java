package com.test;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class EnterPoint implements BundleActivator{
	private Logger logger=Logger.getLogger("com.test.EnterPoint");	
	static{
		String loggerPath="com.test";
		Logger.getLogger(loggerPath).setLevel(Level.DEBUG);
		Logger.getLogger(loggerPath).addAppender(new ConsoleAppender(new PatternLayout()));
	}
	private HeartBeat heartBeat;
	
	@Override
	public void start(BundleContext context) throws Exception {
		logger.debug(" start ");
		Properties properties=new Properties();
		try{
			properties.load(new FileInputStream("temp.settings"));
		}catch(Exception ex){
			System.err.println("File temp.settings does not found: ");
		};
		Set<Object> keys=properties.keySet();
		for(Object key: keys){
			System.out.println("Key:"+key+"     Value:"+properties.getProperty((String)key));
		}
		// start thread
		this.heartBeat=new HeartBeat("temp_file.out");
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		this.heartBeat.stopThread();
		// stop thread 
		logger.debug(" stop ");
	}
}

class HeartBeat extends Thread{
	private BufferedWriter writer=null;
	public HeartBeat(String pathToFile){
		this.flagRun=true;
		try{
			writer=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(pathToFile)));
		}catch(Exception ex){};
		this.start();
	}
	
	private boolean flagRun=false;
	
	public void stopThread(){
		this.flagRun=false;
	}
	
	private SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");
	@Override
	public void run(){
		while(flagRun){
			String outputString=" heartBeat: "+sdf.format(new Date());
			System.out.println(outputString);
			try{
				writer.write(outputString);
				writer.write(outputString+"\n");
				writer.flush();
			}catch(Exception ex){
				System.err.println("write to file Error");
			}
			try{
				Thread.sleep(2000);
			}catch(Exception ex){};
		}
		try{
			writer.close();
		}catch(Exception ex){};
	}
}