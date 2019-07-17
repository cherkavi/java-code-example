package com_port;

import java.io.FileInputStream;
import java.util.Properties;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;

import com.cherkashin.vitaliy.rfid.timing.core_interface.IInputData;

public class ComPortBundle implements BundleActivator, Runnable, IDataListener, ServiceListener, IDestroyEvent{
	private boolean flagRun=false;
	private BundleContext context;
	private IInputData inputData=null;
	@Override
	public void start(BundleContext context) throws Exception {
		this.context=context;
		this.context.addServiceListener(this);
		// в этой точке может произойти прерывание, и зарегестрироваться сервис
		synchronized(this){
			// проверка на регистрацию
			if(inputData==null){
				try{
					inputData=(IInputData)context.getService(context.getServiceReference(IInputData.class.getName()));
				}catch(Exception ex){
					System.out.println("IModuleExecutor yet not register");
				}
			}
		}
		(new Thread(this)).start();
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		this.flagRun=false;
		context.removeServiceListener(this);
	}

	public static void main(String[] args){
		System.out.println("begin");
		(new Thread(new ComPortBundle())).start();
		System.out.println(" end ");
	}
	
	public void run(){
		// подсоединиться к порту, и запустить слушатели данного порта
		Properties properties=new Properties();
		String comPort=null;
		String rate=null;
		String databit=null;
		String stopBit=null;
		String parity=null;
		ComPort port=null;
		try{
			properties.load(new FileInputStream("com_port.properties"));
			comPort=properties.getProperty("port_name");
			rate=properties.getProperty("rate");
			databit=properties.getProperty("databit");
			stopBit=properties.getProperty("stopbit");
			parity=properties.getProperty("parity");
			port=new ComPort(comPort, rate, databit, stopBit, parity);
			port.addDataListener(this);
			flagRun=true;
			while(flagRun){
				try{
					Thread.sleep(2000);
				}catch(InterruptedException ie){};
			}
		}catch(Exception ex){
			System.err.println("com_port.Bundle Exception:"+ex.getMessage());
		}finally{
			// port.close();
		}
	}

	@Override
	public void dataEvent(byte[] data) {
		// System.out.println("получены данных из порта - оповестить сервисы");
		try{
			if(this.inputData!=null){
				this.inputData.notifyAboutInputData(data);
			}
		}catch(Exception ex){};
	}

	
	private int indexIntoArray(Object[] objects, Object value){
		if((objects==null)||(value==null)){
			return -1;
		}
		for(int counter=0;counter<objects.length;counter++){
			if(objects[counter].equals(value)){
				return counter;
			}
		}
		return -1;
	}

	
	@Override
	public void serviceChanged(ServiceEvent event) {
		synchronized(this){
			if((event.getType()==ServiceEvent.MODIFIED)||(event.getType()==ServiceEvent.REGISTERED)){
				// произошла регистрация 
				String[] services=(String[])event.getServiceReference().getProperty("objectClass");
				if(indexIntoArray(services,IInputData.class.getName())>=0){
					synchronized(this){
						inputData=(IInputData)context.getService(context.getServiceReference(IInputData.class.getName()));
					}
				}
			}else{
				// удаление регистрации 
				String[] services=(String[])event.getServiceReference().getProperty("objectClass");
				if(indexIntoArray(services,IInputData.class.getName())>=0){
					this.inputData=null;
				}
			}
		}
	}

	@Override
	public void comPortNeedDestroy() {
		try{
			this.flagRun=false;
			this.context.getBundle().uninstall();
		}catch(Throwable ex){};
	}
}
