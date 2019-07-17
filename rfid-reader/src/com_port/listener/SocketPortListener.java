package com_port.listener;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import org.apache.log4j.Logger;

/** объект, который получает уведомления о принятых с порта данных */
public class SocketPortListener extends Thread{
	private Logger logger=Logger.getLogger(this.getClass());
	private ArrayList<ISocketPortListener> listOfListeners=new ArrayList<ISocketPortListener>();
	private ServerSocket serverSocket;
	private int port;

	public SocketPortListener(int port) throws Exception {
		this.port=port;
		serverSocket=new ServerSocket(port);
		this.start();
	}
	
	byte[] buffer=new byte[1024];
	@Override 
	public void run(){
		Socket socket=null;
		InputStream is=null;
		while(true){
			try{
				logger.debug("wait for signal on port:"+port);
				socket=serverSocket.accept();
				logger.debug("socket have been connected ");
				// однопоточная система - для многопоточной создать отдельный поток 
				ByteArrayOutputStream baos=new ByteArrayOutputStream();
				is=socket.getInputStream();
				int readCount=0;
				while( (readCount=is.read(buffer))>0){
					baos.write(buffer,0,readCount);
				}
				notifyData(baos.toByteArray());
				is.close();
				logger.debug("socket successful readed ");
			}catch(Exception ex){
				logger.error("ComPortListener#run: "+ex.getMessage());
			}finally{
				try{
					is.close();
				}catch(Exception ex){};
				try{
					socket.close();
				}catch(Exception ex){};
			}
		}
	}
	
	private void notifyData(byte[] data){
		logger.debug("notify data listener");
		for(ISocketPortListener dataListener:listOfListeners){
			dataListener.notifyDataFromPort(data);
		}
	}

	/** добавить слушателя данных с порта  */
	public void addDataListener(ISocketPortListener dataListener){
		logger.debug("add data listener");
		this.listOfListeners.add(dataListener);
	}
	
	/**  удалить слушателя данных с порта */
	public void removeDataListener(ISocketPortListener dataListener){
		logger.debug("remove data listener");
		this.listOfListeners.remove(dataListener);
	}
}
