package bonpay.osgi.launcher.executor.jobber;

import java.text.SimpleDateFormat;

import java.util.Date;

import bonpay.osgi.service.interf.IExecutor;

public class TestJobber implements IExecutor{

	@Override
	public void execute() {
		SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");
		System.out.println("test jobber: begin "+sdf.format(new Date()));
		try{
			Thread.sleep(1000);
		}catch(Exception ex){};
		System.out.println("test jobber: end"+sdf.format(new Date()));
	}

	@Override
	public void stop(){
		
	}
}
