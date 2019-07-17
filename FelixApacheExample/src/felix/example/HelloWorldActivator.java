package felix.example;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class HelloWorldActivator implements BundleActivator{

	@Override
	public void start(BundleContext context) throws Exception {
		System.out.println("Start bundle :"+context.getBundle().getSymbolicName());
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		System.out.println("Stop bundle :"+context.getBundle().getSymbolicName());
	}
	
}
