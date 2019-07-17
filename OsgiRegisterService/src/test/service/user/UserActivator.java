package test.service.user;
import org.osgi.framework.BundleActivator;

import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

import test.service.interfaces.IService;


public class UserActivator implements BundleActivator{
	private ServiceTracker serviceTracker;
	@Override
	public void start(BundleContext context) throws Exception {
		serviceTracker=new ServiceTracker(context, IService.class.getName(), null);
		serviceTracker.open();
		new PrintValue(serviceTracker);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		serviceTracker.close();
	}

}
