import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * This class implements a simple bundle that utilizes the OSGi
 * framework's event mechanism to listen for service events. Upon
 * receiving a service event, it prints out the event's details.
**/
public class Activator implements BundleActivator{
	private GuiEnterPoint gui;
	
    /**
     * Implements BundleActivator.start(). Prints
     * a message and adds itself to the bundle context as a service
     * listener.
     * @param context the framework context for the bundle.
    **/
    public void start(BundleContext context)
    {
    	System.out.println("Starting to listen for service events.");
    	gui=new GuiEnterPoint();
    }

    /**
     * Implements BundleActivator.stop(). Prints
     * a message and removes itself from the bundle context as a
     * service listener.
     * @param context the framework context for the bundle.
    **/
    public void stop(BundleContext context)
    {
        System.out.println("Stopped listening for service events.");

        // Note: It is not required that we remove the listener here,
        // since the framework will do it automatically anyway.
        gui.setVisible(false);
        gui.dispose();
    }


}