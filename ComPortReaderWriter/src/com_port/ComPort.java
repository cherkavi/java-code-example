package com_port;


import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

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
public class ComPort implements SerialPortEventListener, IEventNotify, ITcpReadData{
	private Logger logger=Logger.getLogger(this.getClass());
	/** объект, который представляет SerialPort */
	private SerialPort serialPort;
	/** порт, который следует прослушивать на наличие данных, которые следует отправлять в порт */
	private int portInputData;
	/** имя порта, которое следует слать в качестве сигнала "сердцебиение" */
	private String portName;
	/** объект, который отправляет данные на сервер */
	private Sender sender=null;
	/** предел лимита, выше которого будут удаляться первоначальные данные  */
	private int limitBuffer=2048;
	
	public static void main(String[] args){
		Logger.getRootLogger().setLevel(Level.ERROR);
		try{
			Logger.getRootLogger().addAppender(new FileAppender(new PatternLayout("%d{ABSOLUTE} %5p %c{1}:%L - %m%n"), "ComPort.debug"));
		}catch(Exception ex){
			Logger.getRootLogger().addAppender(new ConsoleAppender(new PatternLayout()));
		};
		
		if(args.length<10){
			System.err.println(" <port_name> <rate> <databit> <stopbit> <parity> <server_name> <tcp_port_output_data> <tcp_port_input_data>  <tcp_port_heartbeat> <heart_beat_pause_ms>");
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
			System.out.println("tcp_port_output_data=20154");
			System.out.println("#порт, который следует прослушивать для получения данных ");
			System.out.println("tcp_port_input_data=20155");
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
					Integer.parseInt(args[8]),
					Integer.parseInt(args[9])
					);
		}catch(Exception ex){
			System.err.println("Serial Port not open: "+ex.getMessage());
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
	 * @param portOutputData - порт, на который нужно слать данные 
	 * @param portInputData - порт, который нужно слушать и переправлять данные в порт 
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
				   int portOutputData,
				   int portInputData, 
				   int portHeartBeat, 
				   int heartBeatPause) throws Exception{
		this.portName=comPort;
		this.portInputData=portInputData;
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

		this.sender=new Sender(this.queue, serverName, portOutputData);
		this.sender.start();
		
		// запуск оповещателя о нахождении данной программы "на связи"
		TcpHeartBeat heartBeat=new TcpHeartBeat(serverName, portHeartBeat, heartBeatPause, this.portName.getBytes(),this);
		heartBeat.setDaemon(true);
		heartBeat.start();
		// запуск слушателя входящих данных на TCP порт для передачи этих данных в SerialPort 
		TcpPortListener portListener=new TcpPortListener(this.portInputData, this);
		portListener.setDaemon(true);
		portListener.start();
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
		synchronized(this.serialPort){
			try{
				byte[] array=null;
				if(event.getEventType()==SerialPortEvent.DATA_AVAILABLE){
					ByteArrayOutputStream baos=new ByteArrayOutputStream();
					int count=0;
					while((count=serialPort.getInputStream().read(buffer))>0){
						baos.write(buffer,0,count);
					}
					logger.debug("serialEvent send to reader object");
					array=baos.toByteArray();
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
	}
	
	@SuppressWarnings("unused")
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

	@Override
	public void readDataFromTcpPort(byte[] array) {
		synchronized(this.serialPort){
			try{
				this.serialPort.getOutputStream().write(array);
				this.serialPort.getOutputStream().flush();
				logger.debug("данные записаны в порт: "+array.length);
			}catch(Exception ex){
				logger.error("Write Data to Port Exception:"+ex.getMessage());
			}
			
		}
	}

	private ArrayList<byte[]> queue=new ArrayList<byte[]>();
	
	private void notifyData(byte[] array){
		synchronized(this.queue){
			if(array!=null){
				this.queue.add(array);
				while(this.getQueueSize()>this.limitBuffer){
					logger.warn("ComPort limit out of range "+this.getQueueSize());
					this.queue.remove(0);
				}
				this.queue.notify();
			}
		}
		/*
		Socket socket=null;
		OutputStream os=null;
		try{
			// открыть порт
			socket=new Socket(this.serverName,this.portOutputData);
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
		*/
	}
	
	private int getQueueSize(){
		int returnValue=0;
		for(int counter=0;counter<this.queue.size();counter++){
			returnValue+=this.queue.get(counter).length;
		}
		return returnValue;
	}
}

/** объект, который отправляет очередь из байт на сервер  */
class Sender extends Thread{
	private Logger logger=Logger.getLogger(this.getClass());
	private ArrayList<byte[]> queue;
	/** порт, на который следует отправлять данные, полученные с порта */
	private int portOutputData;
	/** имя сервера, на который нужно слать данные */
	private String serverName;
	
	/** объект, который отправляет очередь из байт на сервер  
	 * @param queue - очередь, которая находится в состоянии обработки 
	 * @param serverName - имя сервера, на который должен быть отправлен 
	 * @param portOutputData - порт, на который будут отправлены данные
	 */
	public Sender(ArrayList<byte[]> queue, String serverName,int portOutputData){
		this.queue=queue;
		this.serverName=serverName;
		this.portOutputData=portOutputData;
	}
	
	@Override
	public void run(){
		byte[] forSend=null;
		while(true){
			forSend=null;
			synchronized(this.queue){
				if(this.queue.size()>0){
					logger.debug("есть элементы для отправки");
					forSend=this.queue.remove(0);
				}else{
					logger.debug("нет элементов ");
					this.closePort();
					try{
						this.queue.wait();
					}catch(InterruptedException ex){};
				}
			}
			if(forSend!=null){
				this.sendData(forSend);
			}else{
				this.closePort();
			}
		}
	}
	private Socket socket=null;
	private OutputStream os=null;
	
	/** отправить элементы */
	private void sendData(byte[] data){
		logger.debug("отправить данные");
		try{
			if((this.socket==null)||(this.socket.isClosed())){
				logger.debug("открыть порт");
				socket=new Socket(this.serverName,this.portOutputData);
				os=socket.getOutputStream();
			}
			logger.debug("послать данные");
			os.write(data);
			os.flush();
		}catch(Exception ex){
			logger.error("Send data Error: "+ex.getMessage());
		};
	}
	
	/** закрыть удаленное соединение */
	private void closePort(){
		logger.debug("close port");
		if((this.socket!=null)&&(this.socket.isClosed()==false)){
			/*try{
				os.flush();
			}catch(Exception ex){
				logger.warn("flush OutputStream Exception: "+ex.getMessage());
			};*/
			try{
				this.os.close();
			}catch(Exception ex){
				logger.warn("close OutputStream Exception: "+ex.getMessage());
			};
			try{
				this.socket.close();
			}catch(Exception ex){
				logger.warn("close OutputStream Exception: "+ex.getMessage());
			};
		}
	}
}



