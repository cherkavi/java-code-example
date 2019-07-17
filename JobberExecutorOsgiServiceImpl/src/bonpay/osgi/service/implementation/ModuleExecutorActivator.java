package bonpay.osgi.service.implementation;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import bonpay.osgi.service.interf.IModuleExecutor;

public class ModuleExecutorActivator implements BundleActivator{
	private ServiceRegistration serviceRegistration;
	@Override
	public void start(BundleContext context) throws Exception {
		serviceRegistration=context.registerService(IModuleExecutor.class.getName(), new ModuleExecutorImpl(), null);
		
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		// Felix will be do it automatically
		context.ungetService(serviceRegistration.getReference());
	}

}
