package modbus_example;

import java.util.ArrayList;
import java.util.Enumeration;

public class PortDetect2 {
    public static String[] get_aviable_ports(){
        ArrayList temp_list=new ArrayList();
        Enumeration port_list=javax.comm.CommPortIdentifier.getPortIdentifiers();
        while(port_list.hasMoreElements()){
            temp_list.add(((javax.comm.CommPortIdentifier)port_list.nextElement()).getName());
        }
        return (String[])temp_list.toArray(new String[]{});
    }
    
    public static void main(String[] args){
    	System.out.println("begin");
    	String[] portsName=get_aviable_ports();
    	for(int counter=0;counter<portsName.length;counter++){
    		System.out.println(counter+" : "+portsName[counter]);
    	}
    	System.out.println("-end-");
    }

}
