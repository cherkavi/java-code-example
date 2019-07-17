package modbus_example;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

public class RxTx {
	public static void main(String[] args){
		try{
	        CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier("COM19");
	        if ( portIdentifier.isCurrentlyOwned() )
	        {
	            System.out.println("Error: Port is currently in use");
	        }
	        else
	        {
	            CommPort commPort = portIdentifier.open("temp value",2000);
	            commPort.close();
	        }
	        
		}catch(Exception ex){
			System.err.println("Exception: "+ex.getMessage());
		}
	}
}
