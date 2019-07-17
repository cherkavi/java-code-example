/*
 * SearchService.java
 *
 * Created on 9 квітня 2008, 10:28
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package example;
import java.io.IOException;
import javax.bluetooth.*;
import java.util.Vector;
import java.util.Enumeration;
/**
 *
 * @author Technik
 */
public class SearchService {
    final UUID OBEX_FILE_TRANSFER = new UUID(0x1106);
    final UUID OBEX_OBJECT_PUSH = new UUID(0x1105);
    public final Vector/*<String>*/ serviceFound = new Vector();
    public Discover RemoteDeviceDiscovery;

    public void do_it() throws IOException, InterruptedException {

        // First run RemoteDeviceDiscovery and use discoved device
        RemoteDeviceDiscovery=new Discover();

        serviceFound.clear();

        UUID serviceUUID = OBEX_OBJECT_PUSH;
        
        /*if ((args != null) && (args.length > 0)) {
            serviceUUID = new UUID(args[0], false);
        }*/

        final Object serviceSearchCompletedEvent = new Object();

        DiscoveryListener listener = new DiscoveryListener() {

            public void deviceDiscovered(RemoteDevice btDevice, DeviceClass cod) {
            }

            public void inquiryCompleted(int discType) {
            }

            public void servicesDiscovered(int transID, ServiceRecord[] servRecord) {
                for (int i = 0; i < servRecord.length; i++) {
                    String url = servRecord[i].getConnectionURL(ServiceRecord.NOAUTHENTICATE_NOENCRYPT, false);
                    if (url == null) {
                        continue;
                    }
                    serviceFound.add(url);
                    DataElement serviceName = servRecord[i].getAttributeValue(0x0100);
                    if (serviceName != null) {
                        System.out.println("service " + serviceName.getValue() + " found " + url);
                    } else {
                        System.out.println("service found " + url);
                    }
                }
            }

            public void serviceSearchCompleted(int transID, int respCode) {
                System.out.println("service search completed!");
                synchronized(serviceSearchCompletedEvent){
                    serviceSearchCompletedEvent.notifyAll();
                }
            }

        };
        
        UUID[] searchUuidSet = new UUID[] { serviceUUID };
        int[] attrIDs =  new int[] {
                0x0100 // Service name
        };

        for(Enumeration en = RemoteDeviceDiscovery.devicesDiscovered.elements(); en.hasMoreElements(); ) {
            RemoteDevice btDevice = (RemoteDevice)en.nextElement();

            synchronized(serviceSearchCompletedEvent) {
                System.out.println("search services on " + btDevice.getBluetoothAddress() + " " + btDevice.getFriendlyName(false));
                LocalDevice.getLocalDevice().getDiscoveryAgent().searchServices(attrIDs, searchUuidSet, btDevice, listener);
                serviceSearchCompletedEvent.wait();
            }
        }

    }

    /** Creates a new instance of SearchService */
    public SearchService() {
        try{
            do_it();
        }catch(IOException io_ex){
            System.out.println("Discover Error IOException:"+io_ex.getMessage());
        }catch(InterruptedException ie_ex){
            System.out.println("Discover Error InterupterException:"+ie_ex.getMessage());
        }
    }
    
}
