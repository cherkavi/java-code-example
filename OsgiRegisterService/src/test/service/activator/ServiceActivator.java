package test.service.activator;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import test.service.interfaces.IService;

public class ServiceActivator implements BundleActivator{

	@Override
	public void start(BundleContext context) throws Exception {
		IService service=new Service();
		context.registerService(IService.class.getName(), service, null);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
