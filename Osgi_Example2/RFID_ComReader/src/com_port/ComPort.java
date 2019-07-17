package com_port;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import java.util.ArrayList;

/** объект, который представляет COMPORT */
public class ComPort implements SerialPortEventListener{
	private SerialPort serialPort;
	
	private IDestroyEvent destroyNotifier;
	
	/**
	 * параметры COM-порта
	 * @param comPort - полное имя порта COM4
	 * @param databit - кол-во бит данных
	 * <li> 5 </li>
	 * <li> 6 </li>
	 * <li> 7 </li>
	 * <li> 8 </li>
	 * @param stopBit - кол-во стоп-бит
	 * <li> 1 </li> 
	 * <li> 1.5 </li> 
	 * <li> 2 </li> 
	 * @param parity - четность
	 * <li> even </li>
	 * <li> mark </li>
	 * <li> none </li>
	 * <li> odd </li>
	 * <li> space </li>
	 * @param reconnectMs - время ожидания перед следующей попыткой подключения к устройству 
	 * @throws Exception
	 */
	public ComPort(String comPort, 
				   String rate, 
				   String databit, 
				   String stopBit, 
				   String parity) throws Exception{
		// Settings
		String programName=ComPort.class.getClass().getName();
		// Connection
		CommPortIdentifier portIdentifier=CommPortIdentifier.getPortIdentifier(comPort);
		//System.out.println("PortIdentifier:"+portIdentifier);
		this.serialPort=(SerialPort)portIdentifier.open(programName, 2000);
		// IMPORTANT
		this.serialPort.notifyOnDataAvailable(true);
		//serialPort.notifyOnCTS(true);
		/*serialPort.notifyOnBreakInterrupt(true);
		serialPort.notifyOnCarrierDetect(true);
		serialPort.notifyOnDSR(true);
		serialPort.notifyOnFramingError(true);
		serialPort.notifyOnOutputEmpty(true);
		serialPort.notifyOnOverrunError(true);
		serialPort.notifyOnParityError(true);
		serialPort.notifyOnRingIndicator(true);*/
		//System.out.println("Port was opened: "+serialPort);
		int baudrate=Integer.parseInt(rate);
		this.serialPort.setSerialPortParams(baudrate, 
								       getDataBits(databit), 
								       getStopBits(stopBit), 
								       getParity(parity));
		this.serialPort.addEventListener(this);
	}

	/** установить элемент, который бдет оповещен о необходимости закрытия порта */
	public void setComPortDestroyNotify(IDestroyEvent destroyNotifier){
		this.destroyNotifier=destroyNotifier;
	}
	
	
	/*
	public static SerialPort reconnectComPort(SerialPort port){
		CommPortIdentifier portIdentifier=null;
		SerialPort serialPort=null;
		while(true){
			
			try{
				String programName=ComPort.class.getClass().getName();
				// Connection
				portIdentifier=CommPortIdentifier.getPortIdentifier(comPort);
				
				//System.out.println("PortIdentifier:"+portIdentifier);
				serialPort=(SerialPort)portIdentifier.open(programName, 2000);
				int baudrate=Integer.parseInt(rate);
				serialPort.setSerialPortParams(baudrate, 
										       getDataBits(databit), 
										       getStopBits(stopBit), 
										       getParity(parity));
				
				// IMPORTANT
				serialPort.notifyOnDataAvailable(true);
				// serialPort.notifyOnCTS(true);
				return serialPort;
			}catch(Exception ex){
				System.err.println("Attempt to reconnect with port "+ComPort.comPort);
				try{
					port.close();
					Thread.sleep(ComPort.reconnectMs);
				}catch(Exception exInner){
				}
			}
		}
	}
	*/
	
	/** получить контроль четности  */
	private static int getParity(String parity){
		parity=parity.trim();
		if(parity.equalsIgnoreCase("even")){
			return SerialPort.PARITY_EVEN;
		};
		if(parity.equalsIgnoreCase("mark")){
			return SerialPort.PARITY_MARK;
		};
		if(parity.equalsIgnoreCase("none")){
			return SerialPort.PARITY_NONE;
		};
		if(parity.equalsIgnoreCase("odd")){
			return SerialPort.PARITY_ODD;
		};
		if(parity.equalsIgnoreCase("space")){
			return SerialPort.PARITY_SPACE;
		};
		return SerialPort.PARITY_NONE;
	}
	
	/** получить стоп-биты на основании текстового значения */
	private static int getStopBits(String stopBits){
		if(stopBits.trim().equalsIgnoreCase("1")){
			return SerialPort.STOPBITS_1;
		}
		if((stopBits.trim().equalsIgnoreCase("1.5"))||(stopBits.trim().equalsIgnoreCase("2"))){
			return SerialPort.STOPBITS_2;
		}
		if(stopBits.trim().equalsIgnoreCase("2")){
			return SerialPort.STOPBITS_2;
		}
		return SerialPort.STOPBITS_1;
	}
	
	
	/** получить биты данных на основании текстового значения */
	private static int getDataBits(String value){
		int returnValue=8;
		try{
			returnValue=Integer.parseInt(value);
		}catch(Exception ex){
			System.err.println(" detect DataBits Exception: "+ex.getMessage());
		}
		switch(returnValue){
			case 5: returnValue=SerialPort.DATABITS_5;break;
			case 6: returnValue=SerialPort.DATABITS_6;break;
			case 7: returnValue=SerialPort.DATABITS_7;break;
			case 8: returnValue=SerialPort.DATABITS_8;break;
			default: returnValue=SerialPort.DATABITS_8;
		}
		return returnValue;
	}
	
	private ArrayList<IDataListener> listOfListener=new ArrayList<IDataListener>();
	
	public void addDataListener(IDataListener dataListener){
		if(this.listOfListener.indexOf(dataListener)<0){
			this.listOfListener.add(dataListener);
		}
	}
	
	public void removeDataListener(IDataListener dataListener){
		this.listOfListener.remove(dataListener);
	}
	
	private byte[] buffer=new byte[256];
	
	@Override
	public void serialEvent(SerialPortEvent event) {
		try{
			byte[] array=null;
			if(event.getEventType()==SerialPortEvent.DATA_AVAILABLE){
				int count=serialPort.getInputStream().read(buffer);
				// debug("data obtained from port");
				array=this.subArray(buffer, 0, count);
			}
			for(IDataListener listener:listOfListener){
				listener.dataEvent(array);
			}
		}catch(Exception ex){
			this.destroyNotifier.comPortNeedDestroy();
			// System.exit(1);
			
			/*this.listOfListener.clear();
			try{
				this.serialPort.close();
			}catch(Error error){}
			finally{
				System.out.println("finally");
			};
			*/
		}
	}
	
	private byte[] subArray(byte[] array, int indexBegin, int indexEnd){
		byte[] returnValue=new byte[indexEnd-indexBegin];
		for(int counter=indexBegin;counter<indexEnd;counter++){
			returnValue[counter-indexBegin]=array[counter];
		}
		return returnValue;
	}
	
	public void close(){
		this.serialPort.close();
	}
	
	@SuppressWarnings("unused")
	private void debug(Object message){
		System.out.print("ComPort DEBUG:");
		System.out.println(message);
	}
}

