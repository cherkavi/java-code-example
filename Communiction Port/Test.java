package modbus_example;

import java.util.Enumeration;


import javax.comm.CommPort;
import javax.comm.CommPortIdentifier;
import javax.comm.NoSuchPortException;

import net.wimpi.modbus.Modbus;

public class Test {
	@SuppressWarnings({ "unchecked", "restriction" })
	public static void main(String args[]) throws Exception {
		System.out.println("begin");
		// List portNames
		Enumeration ports = CommPortIdentifier.getPortIdentifiers();
		while (ports.hasMoreElements()) {
			CommPortIdentifier portId = (CommPortIdentifier)ports.nextElement();
			String s = getPortInfo(portId);
			System.out.println(s);
		}	

		// test open-close methods on each port
		ports = CommPortIdentifier.getPortIdentifiers();
		while (ports.hasMoreElements()) {
			CommPortIdentifier portId = (CommPortIdentifier)ports.nextElement();
			CommPort port = null;
			try {
				System.out.print("open " + portId.getName());
				port = portId.open(portId.getName(), 2000);
				port.close();
				System.out.println("...closed");
			} catch (Exception ex) {
				ex.printStackTrace();
				if (port != null)
					try { port.close(); } catch (Exception e) { }
			}
	    }
		System.out.println("-end-");
	}

	private static String getPortInfo(CommPortIdentifier portid) {
		StringBuffer sb = new StringBuffer();
		sb.append(portid.getName());
		sb.append(", ");
		sb.append("portType: ");
		if (portid.getPortType() == CommPortIdentifier.PORT_SERIAL)
			sb.append("serial");
		else if (portid.getPortType() == CommPortIdentifier.PORT_PARALLEL)
			sb.append("parallel");
		else
			sb.append("unknown");
		return sb.toString();
	}
	
}
