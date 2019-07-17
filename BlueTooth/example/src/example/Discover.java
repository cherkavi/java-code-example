/*
 * Discover.java
 *
 * Created on 9 квітня 2008, 8:58
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package example;

import java.io.IOException;
import java.util.Vector;
import javax.bluetooth.*;
/**
 *
 * @author Technik
 */
public class Discover {
    public static final Vector/*<RemoteDevice>*/ devicesDiscovered = new Vector();
    
    public void do_it() throws IOException, InterruptedException {
        final Object inquiryCompletedEvent = new Object();

        devicesDiscovered.clear();

        DiscoveryListener listener = new DiscoveryListener() {

            public void deviceDiscovered(RemoteDevice btDevice, DeviceClass cod) {
                System.out.println("Device " + btDevice.getBluetoothAddress() + " found");
                devicesDiscovered.addElement(btDevice);
                try {
                    System.out.println("     name " + btDevice.getFriendlyName(false));
                } catch (IOException cantGetDeviceName) {
                    System.out.println("c'ant get device name");
                }
            }

            public void inquiryCompleted(int discType) {
                System.out.println("Device Inquiry completed!");
                synchronized(inquiryCompletedEvent){
                    inquiryCompletedEvent.notify();// inquiryCompletedEvent.notifyAll();
                }
            }

            public void serviceSearchCompleted(int transID, int respCode) {
            }

            public void servicesDiscovered(int transID, ServiceRecord[] servRecord) {
            }
        };

        synchronized(inquiryCompletedEvent) {
            boolean started = LocalDevice.getLocalDevice().getDiscoveryAgent().startInquiry(DiscoveryAgent.GIAC, 
                                                                                            listener);
            if (started) {
                System.out.println("wait for device inquiry to complete...");
                inquiryCompletedEvent.wait();
                System.out.println(devicesDiscovered.size() +  " device(s) found");
            }
        }
        
    }
    /** конструктор для класса*/
    public Discover(){
        try{
            do_it();
        }catch(IOException io_ex){
            System.out.println("Discover Error IOException:"+io_ex.getMessage());
        }catch(InterruptedException ie_ex){
            System.out.println("Discover Error InterupterException:"+ie_ex.getMessage());
        }
        
        
    }
}
