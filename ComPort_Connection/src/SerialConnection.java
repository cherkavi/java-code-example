import java.io.InputStream;
import java.io.OutputStream;

import javax.comm.CommPortIdentifier;
import javax.comm.SerialPort;



public class SerialConnection {
	@SuppressWarnings("restriction")
	public static void main(String[] args){
		try{
			// Settings
			String portName="COM4";
			String programName="Test CommPort";
			// Connection
			CommPortIdentifier portIdentifier;
			SerialPort serialPort;
			portIdentifier=CommPortIdentifier.getPortIdentifier(portName);
			System.out.println("PortIdentifier:"+portIdentifier);
			
			serialPort=(SerialPort)portIdentifier.open(programName, 2000);
// IMPORTANT
			serialPort.notifyOnDataAvailable(true);
			System.out.println("Port was opened: "+serialPort);
			
			serialPort.setSerialPortParams(9600, 
									       SerialPort.DATABITS_7, 
									       SerialPort.STOPBITS_2, 
									       SerialPort.PARITY_NONE);
			System.out.println("parameters was setted");
			
			OutputStream os=serialPort.getOutputStream();
			byte[] request=new byte[]{0x3A,0x30,0x31,0x30,0x33,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x41,0x46,0x32,0x0D,0x0A};
			os.write(request);
			System.out.println("write request");
			
			byte[] buffer=new byte[100];
			InputStream is=serialPort.getInputStream();
			int byteCount=0;
			try{
				Thread.sleep(200);
			}catch(Exception ex){};
			while( (byteCount=is.read(buffer))>0){
				for(int counter=0;counter<byteCount;counter++){
					System.out.print(Integer.toHexString(buffer[counter])+"  ");
				}
				System.out.println();
				System.out.println(byteCount);
			}
			
			System.out.println("read response");
			
			System.out.println("close comm port ");
			serialPort.close();
		}catch(Exception ex){
			System.err.println("SerialConnection Exception:"+ex.getMessage());
		}
		
	}
}
