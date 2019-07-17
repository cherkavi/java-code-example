package test.service.user;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;



import java.util.Date;

import org.osgi.util.tracker.ServiceTracker;

import test.service.activator.Service;
import test.service.interfaces.IService;


public class PrintValue extends Thread{
	private ServiceTracker serviceTracker;
	private Object service;
	
	public PrintValue(ServiceTracker serviceTracker){
		this.serviceTracker=serviceTracker;
		this.start();
	}
	
	private SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");
	
	public void run(){
		while(true){
			if(service==null){
				service=this.serviceTracker.getService();
				// очень пародоксально отказывается преобразовывать полученный объект класса Service ( implements IService ) к IService
				// Run exception: test.service.activator.Service cannot be cast to test.service.activator.IService
			}
			if(service!=null){
				System.out.println("Object: "+callMethod(service)+"  Time:"+sdf.format(new Date()));
			}else{
				System.out.println("service was not found ");
			}
			try{
				Thread.sleep(3000);
			}catch(Exception ex){};
		}
	}
	
	private String callMethod(Object object){
		String returnValue=null;
		try{
			Class<?> clazz=object.getClass();
			/*Method[] methods=clazz.getMethods();
			for(int counter=0;counter<methods.length;counter++){
				System.out.println(counter+":  "+methods[counter].getName());
			}*/
			Method method=clazz.getMethod("getString");
			// System.out.println("Method getString: "+method.getName());
			returnValue=(String)method.invoke(object);
		}catch(Exception ex){
			System.err.println("CallMethod Exception: "+ex.getMessage());
		}
		return returnValue;
	}
}
