package com_port;

import java.io.ByteArrayOutputStream;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import org.apache.log4j.Logger;

/** объект, который принимает вход€щие данных с порта TCP и оповещает слушателей о пришедших данных */
public class TcpPortListener extends Thread{
	private Logger logger=Logger.getLogger(this.getClass());
	private int tcpPort=0;
	private ServerSocket serverSocket;
	private Notifyer notifyer;
	private int limitQueueByte=2048;
	private ArrayList<byte[]> queue=new ArrayList<byte[]>();
	
	/** объект, который принимает вход€щие данных с порта TCP и оповещает слушателей о пришедших данных 
	 * @param tcpPort - порт, который следует прослушивать
	 * @param listeners - слушатели, которых следует оповещать 
	 * @throws IOException - если невозможно установить прослушку TCP порта
	 */
	public TcpPortListener(int tcpPort, ITcpReadData ... listeners) throws IOException{
		this.tcpPort=tcpPort;
		this.notifyer=new Notifyer(queue, listeners);
		serverSocket=new ServerSocket(this.tcpPort);
	}
	
	private boolean flagRun=true;
	
	public void stopThread(){
		this.flagRun=false;
	}
	
	byte[] buffer=new byte[256];
	@Override
	public void run(){
		this.notifyer.start();
		logger.debug("Listener Data for write to SerialPort started: ("+tcpPort+")");
		this.flagRun=true;
		Socket socket=null;
		while(flagRun){
			try{
				socket=serverSocket.accept();
				logger.debug("Data exists");
				InputStream is=socket.getInputStream();
				ByteArrayOutputStream baos=new ByteArrayOutputStream();
				int readCount=0;
				while((readCount=is.read(buffer))>0){
					baos.write(buffer,0,readCount);
					baos.flush();
				}
				is.close();
				byte[] recievedData=baos.toByteArray();
				logger.debug("Notify Data:"+recievedData.length);
				notifyData(recievedData);
			}catch(Exception ex){
				logger.error("TcpPortListener Exception:"+ex.getMessage());
				try{
					Thread.sleep(500);
				}catch(Exception exInner){};
				try{
					if(serverSocket==null){
						logger.error("socket is null - attempt to OpenPort");
						serverSocket=new ServerSocket(this.tcpPort);
					}
					if(serverSocket.isClosed()){
						logger.error("socket is closed - attempt to OpenPort");
						serverSocket=new ServerSocket(this.tcpPort);
					}
				}catch(Exception exInner){
					logger.error("TcpPortListener ReOpen Exception:"+ex.getMessage());
					System.exit(1);
				}
			}finally{
				try{
					socket.close();
				}catch(Exception ex){};
			}
		}
	}
	
	/** оповестить всех слушателей о наличии данных, прочитанных из TCP порта */
	private void notifyData(byte[] data){
		logger.debug("notify data listener:");
		/*for(ITcpReadData listener:listOfListeners){
		listener.readDataFromTcpPort(array);
		}*/
		// добавить в буффер, если буффер превышен - очистить
		synchronized(this.queue){
			this.queue.add(data);
			while(this.getQueueSize()>this.limitQueueByte){
				this.logger.warn("TcpPortListener out of limit");
				this.queue.remove(0);
			}
			this.queue.notify();
		}
	}

	/** получить размер очереди */
	private int getQueueSize(){
		int value=0;
		for(int counter=0;counter<this.queue.size();counter++)value+=this.queue.get(counter).length;
		return value;
	}
	
}

/** оповещает слушателей о по€влении данных с порта */
class Notifyer extends Thread {
	private ArrayList<ITcpReadData> listOfListeners=new ArrayList<ITcpReadData>();
	private Logger logger=Logger.getLogger(this.getClass());
	private ArrayList<byte[]> queue;
	
	/** оповещает слушателей о по€влении данных с порта */
	public Notifyer(ArrayList<byte[]> queue, ITcpReadData ... listeners){
		this.queue=queue;
		for(int counter=0;counter<listeners.length;counter++){
			listOfListeners.add(listeners[counter]);
		}
	}

	@Override
	public void run(){
		byte[] forSend;
		while(true){
			forSend=null;
			synchronized(this.queue){
				logger.debug("ѕроверить очередь на наличие данных ");
				if(queue.size()>0){
					logger.debug("получить первый пакет дл€ отправки");
					forSend=this.queue.remove(0);
				}else{
					logger.debug("нет данных - заснуть");
					try{
						queue.wait();
					}catch(Exception ex){};
				}
			}
			if(forSend!=null){
				logger.debug("есть данные дл€ отправки - оповестить всех слушателей:"+forSend.length);
				for(ITcpReadData listener:listOfListeners){
					listener.readDataFromTcpPort(forSend);
				}
			}
		}
	}
}