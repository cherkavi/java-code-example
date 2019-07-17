package com_port;

import java.io.OutputStream;
import java.net.Socket;

import org.apache.log4j.Logger;

/** объект, который отправляет на указанный порт данные в виде заданной последовательности через указанные промежутки времени */
public class TcpHeartBeat extends Thread{
	private Logger logger=Logger.getLogger(this.getClass());
	private String serverName;
	private int port;
	private int delay;
	private byte[] arrayForSend;
	private IEventNotify eventNotify;
	
	/** объект, который отправляет на указанный порт данные в виде заданной последовательности через указанные промежутки времени
	 * @param serverName - IP адрес, или же имя сервера
	 * @param port - порт, на который следует отправлять данные 
	 * @param delay - задержка перед последующей отправкой  
	 * @param arrayForSend - данные для отправки 
	 */
	public TcpHeartBeat(String serverName, int port, int delay, byte[] arrayForSend, IEventNotify eventNotify){
		this.serverName=serverName;
		this.port=port;
		this.delay=delay;
		this.arrayForSend=arrayForSend;
		this.eventNotify=eventNotify;
	}
	
	private boolean flagRun=false;
	
	/** остановить выполнение потока */
	public void stopThread(){
		this.flagRun=false;
	}
	
	@Override
	public void run(){
		logger.debug("start heart beat");
		this.flagRun=true;
		Socket socket=null;
		OutputStream os=null;
		while(this.flagRun){
			try{
				logger.debug("attempt to send HeartBeatData into port:"+this.port);
				// открыть порт
				socket=new Socket(this.serverName, this.port);
				// залить данные
				os=socket.getOutputStream();
				os.write(this.arrayForSend);
				os.flush();
				os.close();
			}catch(Exception ex){
				logger.error("TcpHeartBeat pulse Exception:"+ex.getMessage());
				logger.error("ComPort will be closed, System.Exit");
				this.eventNotify.needClosePort();
			}finally{
				// закрыть поток
				try{
					os.close();
				}catch(Exception innerException){};
				// закрыть порт
				try{
					socket.close();
				}catch(Exception innerException){};
			}
			try{
				Thread.sleep(this.delay);
			}catch(Exception ex){}
		}
	}
}
