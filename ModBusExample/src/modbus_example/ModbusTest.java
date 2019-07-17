package modbus_example;

import java.io.InputStream;

import java.io.OutputStream;

//import javax.comm.CommPort;
//import javax.comm.CommPortIdentifier;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

public class ModbusTest {

	/**
	 * ѕопытка воссоздать последовательность:
	000000-Tx:3A 30 31 30 33 30 30 30 30 30 30 30 41 46 32 0D 0A
	000001-Rx:3A 30 31 30 33 31 34 30 30 30 31 30 30 30 36 33 30 34 36 33 31 33 30 33 31 35 36 33 31 32 45 30 30 30 31 30 30 30 31 30 30 30 31 30 30 30 30 32 31 0D 0A
	000002-Tx:3A 30 31 30 33 30 30 30 30 30 30 30 41 46 32 0D 0A
	000003-Rx:3A 30 31 30 33 31 34 30 30 30 31 30 30 30 36 33 30 34 36 33 31 33 30 33 31 35 36 33 31 32 45 30 30 30 31 30 30 30 31 30 30 30 31 30 30 30 30 32 31 0D 0A
	000004-Tx:3A 30 31 30 33 30 30 30 30 30 30 30 41 46 32 0D 0A
	000005-Rx:3A 30 31 30 33 31 34 30 30 30 31 30 30 30 36 33 30 34 36 33 31 33 30 33 31 35 36 33 31 32 45 30 30 30 31 30 30 30 31 30 30 30 31 30 30 30 30 32 31 0D 0A
	 * @param args
	 */
	
	public static void main(String[] args){
		// request 
		// byte[] request=new byte[]{0x01,0x04,0x00,0x00,0x00,0x01,0x0D,0x0A}; 
		byte[] request=new byte[]{0x3A,0x30,0x31,0x30,0x33,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x41,0x46,0x32,0x0D,0x0A};
		debug("init com port ");
		
		CommPort port = null;
		try {
			CommPortIdentifier portIdentifier=CommPortIdentifier.getPortIdentifier("COM19");
			
			port=portIdentifier.open("Temp program", 2000);
			OutputStream outputStream=port.getOutputStream();
			outputStream.write(request);
			outputStream.flush();
			InputStream inputStream=port.getInputStream();
			byte[] buffer=new byte[1024];
			int readCount=0;
			if( (readCount=inputStream.read(buffer)) >0){
				for(int counter=0;counter<readCount;counter++){
					System.out.print(Integer.toString(buffer[counter], 16));
					System.out.print(" ");
				}
				System.out.println("");
				System.out.println("Read OK:"+readCount);
			}else{
				System.err.println("Read Error");
			}
			port.close();
			System.out.println("...closed");
		} catch (Exception ex) {
			ex.printStackTrace();
			if (port != null)
				try { port.close(); } catch (Exception e) { }
		}
		System.exit(0);
	}
	
	private static void debug(String message){
		System.out.println("DEBUG: "+message);
	}
}
