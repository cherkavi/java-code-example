import org.jivesoftware.*;
import org.jivesoftware.util.*;
import org.jivesoftware.whack.ExternalComponentManager;
import org.xmpp.component.Component;
import org.xmpp.component.ComponentException;
import org.xmpp.component.ComponentManager;
import org.xmpp.packet.JID;
import org.xmpp.packet.Packet;

public class EnterPoint {
	public static void main(String[] args){
		
		// Create a manager for the external components that will connect to the server 		
		ExternalComponentManager manager=new ExternalComponentManager("talk.google.com",5222);
		String subdomain= "technik7job@gmail.com";
		manager.setSecretKey(subdomain, "");
		manager.setMultipleAllowed(subdomain, true);
		try{
			manager.addComponent(subdomain,new MyComponent() );
		}catch(Exception ex){};
		
		while (true) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
		}
	}		
}

class MyComponent implements Component {

    public String getName() {
        return "MyComponent";
    }

    public String getDescription() {
        return "MyComponent - An experiment";
    }

    public void processPacket(Packet packet) {
        System.out.println("Received package:" + packet.toXML());
    }

    public void initialize(JID jid, ComponentManager componentManager) throws ComponentException {
        System.out.println("initialize");
    }

    public void shutdown() {
        System.out.println("shutdown");
    }

    public void start() {
        System.out.println("start");
    }

}