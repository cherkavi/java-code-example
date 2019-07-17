package com_port;


import java.io.OutputStream;
import java.net.Socket;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

/** объект, который представляет COMPORT */
public class ComPort implements SerialPortEventListener, IEventNotify{
	private Logger logger=Logger.getLogger(this.getClass());
	/** объект, который представляет SerialPort */
	private SerialPort serialPort;
	/** порт, на который следует отправлять данные, которые были получены с порта */
	private int portData;
	/** имя порта, которое следует слать в качестве сигнала "сердцебиение" */
	private String portName;
	/** имя сервера, на который нужно слать данные */
	private String serverName;
	
	public static void main(String[] args){
		Logger.getRootLogger().setLevel(Level.ERROR);
		try{
			Logger.getRootLogger().addAppender(new FileAppender(new PatternLayout("%d{ABSOLUTE} %5p %c{1}:%L - %m%n"), "ComPort.debug"));
		}catch(Exception ex){
			Logger.getRootLogger().addAppender(new ConsoleAppender(new PatternLayout()));
		};
		
		if(args.length<9){
			System.err.println(" <port_name> <rate> <databit> <stopbit> <parity> <server_name> <tcp_port_data> <tcp_port_heartbeat> <heart_beat_pause_ms>");
			System.out.println("#COM1, COM2, COM3.....");
			System.out.println("port_name=COM1");
			System.out.println("#2400, 4800, 9600, 19200, 38400, 56000, 115200");
			System.out.println("rate=9600");
			System.out.println("#5, 6, 7, 8");
			System.out.println("databit=8");
			System.out.println("#1,1 .5, 2");
			System.out.println("stopbit=1");
			System.out.println("#even, mark, none, odd, space");
			System.out.println("parity=none");
			System.out.println("#имя сервера, на который нужно слать данные ");
			System.out.println("server_name=127.0.0.1");
			System.out.println("#порт, на который нужно посылать данные");
			System.out.println("tcp_port_data=20155");
			System.out.println("#порт, на который нужно посылать сигнал HeartBeat");
			System.out.println("tcp_port_heartbeat=20156");
			System.out.println("#задержка перед посылкой сигнала сердцебиение");
			System.out.println("heart_beat_pause_ms=5000");
			System.exit(1);
		}
		try{
			new ComPort(args[0], 
					args[1], 
					args[2], 
					args[3], 
					args[4],
					args[5],
					Integer.parseInt(args[6]),
					Integer.parseInt(args[7]),
					Integer.parseInt(args[8])
					);
		}catch(Exception ex){
			System.err.println("Port not open: "+ex.getMessage());
			System.exit(1);
		}
	}
	
	/**
	 * параметры COM-порта
	 * @param comPort - полное имя порта COM4
	 * @parma rate 
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
	 * @param serverName - имя сервера, на которое нужно слать данные
	 * @param portData - порт, на который нужно слать данные 
	 * @partam portHeartBeat - порт, на который нужно слать сигнал "сердцебиение" (посылать имя порта)
	 * @param heartBeatPause - время ожидания перед следующей посылкой сигнала "сердцебиение" 
	 * @throws Exception
	 */
	public ComPort(String comPort, 
				   String rate, 
				   String databit, 
				   String stopBit, 
				   String parity, 
				   String serverName, 
				   int portData, 
				   int portHeartBeat, 
				   int heartBeatPause) throws Exception{
		this.portName=comPort;
		this.serverName=serverName;
		this.portData=portData;
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
		TcpHeartBeat heartBeat=new TcpHeartBeat(serverName, portHeartBeat, heartBeatPause, this.portName.getBytes(),this);
		heartBeat.setDaemon(true);
		heartBeat.start();
		logger.error("start");
	}

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
	
	private byte[] buffer=new byte[256];
	
	@Override
	public void serialEvent(SerialPortEvent event) {
		try{
			byte[] array=null;
			if(event.getEventType()==SerialPortEvent.DATA_AVAILABLE){
				int count=serialPort.getInputStream().read(buffer);
				logger.debug("serialEvent send to reader object");
				array=this.subArray(buffer, 0, count);
			}
			this.notifyData(array);
		}catch(Exception ex){
			logger.error("Read data Exception: "+ex.getMessage());
			logger.error("System.exit");
			System.exit(0);
			/*this.listOfListener.clear();
			try{
				this.serialPort.close();
			}catch(Error error){}
			finally{
				System.out.println("finally");
			};
			this.destroyNotifier.comPortNeedDestroy();*/
		}
	}
	
	private void notifyData(byte[] array){
		Socket socket=null;
		OutputStream os=null;
		try{
			// открыть порт
			socket=new Socket(this.serverName,this.portData);
			// послать данные
			os=socket.getOutputStream();
			os.write(array);
			os.flush();
		}catch(Exception ex){
			logger.error("Notify Data:"+ex.getMessage());
		}finally{
			// закрыть поток
			try{
				os.close();
			}catch(Exception inEx){};
			// закрыть порт
			try{
				socket.close();
			}catch(Exception inEx){};
		}
	}
	
	private byte[] subArray(byte[] array, int indexBegin, int indexEnd){
		byte[] returnValue=new byte[indexEnd-indexBegin];
		for(int counter=indexBegin;counter<indexEnd;counter++){
			returnValue[counter-indexBegin]=array[counter];
		}
		return returnValue;
	}

	@Override
	public void needClosePort() {
		
		System.exit(1);
	}
}

