package felix.example;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleListener;
import org.osgi.framework.FrameworkEvent;
import org.osgi.framework.FrameworkListener;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;

public class ActivatorListener implements BundleActivator,

										  BundleListener,
										  FrameworkListener,
										  ServiceListener{
	private String dependedBundle="file:helloworld.jar";
	
	@Override
	public void start(BundleContext context) throws Exception {
		System.out.println("ActivatorListener. Start bundle: ");
		context.addBundleListener(this);
		context.addFrameworkListener(this);
		context.addServiceListener(this);
		Bundle bundle=getBundleByName(context,this.dependedBundle);
		if(bundle!=null){
			bundle.stop();
		}
	}

	private Bundle getBundleByName(BundleContext context, String bundleName){
		Bundle[] bundle=context.getBundles();
		for(int counter=0;counter<bundle.length;counter++){
			if(bundle[counter].getLocation().equals(bundleName)){
				return bundle[counter];
			}else{
				System.out.println(bundleName+": not equals :"+bundle[counter].getLocation());
			}
		}
		return null;
	}
	
	@Override
	public void stop(BundleContext context) throws Exception {
		System.out.println("ActivatorListener. Stop bundle: ");
		context.removeBundleListener(this);
		context.removeFrameworkListener(this);
		context.removeServiceListener(this);
		try{
			System.out.println("install Bundle");
			Bundle bundle=context.installBundle(this.dependedBundle);
			System.out.println("start Bundle");
			bundle.start();
		}catch(Exception ex){
			System.err.println("Bundle "+this.dependedBundle+" Exception: "+ex.getMessage());
		};
	}

	@Override
	public void bundleChanged(BundleEvent event) {
		switch(event.getType()){
			case BundleEvent.INSTALLED:
				System.out.println("ActivatorListener. BundleEvent: INSTALLED");
			break;
			case BundleEvent.UPDATED:
				System.out.println("ActivatorListener. BundleEvent: UPDATED");
			break;
			
			default:
				System.out.println("ActivatorListener. BundleEvent: "+event.getType());
			break;
		}
				
	}

	@Override
	public void frameworkEvent(FrameworkEvent event) {
		switch(event.getType()){
		case FrameworkEvent.STARTED:
			System.out.println("ActivatorListener. FrameworkEvent: STARTED");
		break;
		case FrameworkEvent.STOPPED:
			System.out.println("ActivatorListener. FrameworkEvent: STOPPED");
		break;
		
		default:
			System.out.println("ActivatorListener. FrameworkEvent: "+event.getType());
		break;
		}
	}

	@Override
	public void serviceChanged(ServiceEvent event) {
		switch(event.getType()){
			case ServiceEvent.MODIFIED:
				System.out.println("ActivatorListener. ServiceEvent.MODIFIED");
			break;
			case ServiceEvent.REGISTERED:
				System.out.println("ActivatorListener. ServiceEvent.REGISTERED");
			break;
			case ServiceEvent.UNREGISTERING:
				System.out.println("ActivatorListener. ServiceEvent.UNREGISTERING");
			break;
			
			default:
				System.out.println("ActivatorListener. ServiceEvent.OTHER: "+event.getType());
			break;
		}
	}

}
