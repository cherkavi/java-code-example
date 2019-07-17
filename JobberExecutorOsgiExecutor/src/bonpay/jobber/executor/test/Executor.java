package bonpay.jobber.executor.test;

import java.text.SimpleDateFormat;
import java.util.Date;

import bonpay.osgi.service.interf.IExecutor;

/** объект-заглушка для Executor */
public class Executor implements IExecutor{
	private SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");
	
	private void debug(Object value){
		System.out.println("DEBUG Executor : "+value);
	}
	
	@Override
	public void execute() {
		debug("Executor.execute: "+sdf.format(new Date()));
	}

	@Override
	public void stop() {
		debug("Executor.stop: "+sdf.format(new Date()));
	}
}
