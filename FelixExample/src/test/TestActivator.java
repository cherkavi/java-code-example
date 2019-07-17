package test;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;


public class TestActivator implements BundleActivator{

	@Override
	public void start(BundleContext context) throws Exception {
		System.out.println("TestActivator started ");
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		System.out.println("TestActivator stopped");
	}

}
