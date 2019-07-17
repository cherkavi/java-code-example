package com.test;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;

import com.api.IMessage;

public class Example implements BundleActivator{
	private BundleContext context;
	@Override
	public void start(BundleContext context) throws Exception {
		this.context=context;
		context.addServiceListener(new ServiceListener(){
			@Override
			public void serviceChanged(ServiceEvent serviceEvent) {
				String[] keys=serviceEvent.getServiceReference().getPropertyKeys();
				for(int counter=0;counter<keys.length;counter++){
					System.out.println("Key:"+keys[counter]+"    Value:"+serviceEvent.getServiceReference().getProperty(keys[counter]));
				}
				String[] services=(String[])serviceEvent.getServiceReference().getProperty("objectClass");
				for(int counter=0;counter<services.length;counter++){
					System.out.println(counter+":"+services[counter]);
				}
				//serviceEvent.getSource().getClass().getName()
				//org.apache.felix.framework.ServiceReferenceImpl
				System.out.println("event.getServiceReference: "+serviceEvent.getServiceReference().getClass().getName());
				/*if(serviceEvent.getSource().getClass().getName().equals(IMessage.class.getClass().getName())){
					// this is source class
					if(serviceEvent.getType()==ServiceEvent.REGISTERED){
						
					}

				}else{
					// this is another class
				}*/
			}
		});
	}

	@Override
	public void stop(BundleContext context) throws Exception {
	
	}

}
