package com_port_controller;

import java.io.OutputStream;
import java.util.ArrayList;
import org.apache.log4j.Logger;

import com_port_controller.listener.ISocketPortListener;
import com_port_controller.listener.SocketHeartBeatProgramRunner;
import com_port_controller.listener.SocketPortListener;

/** класс который создает иллюзию работы с реальным SerialPort, который запускается в отдельном процессе и контроллируется только посредством TCP портов
 * (автоматический запуск процесса, в случае не обнаружения сигнала сердцебиения)*/
public class SerialPortProxy implements ISocketPortListener {
	private Logger logger=Logger.getLogger(this.getClass());
	private SerialPortProxyOutputStream outputStream;
	private ArrayList<IDataNotify> listOfListener=new ArrayList<IDataNotify>();
	
	/** класс который создает иллюзию работы с реальным SerialPort, который запускается в отдельном процессе и контроллируется только посредством TCP портов
	 * (автоматический запуск процесса, в случае не обнаружения сигнала сердцебиения)
	 * @param portWrite - порт, в который будут записываться данные для работы с портом (20100)
	 * @param portRead - порт из которого будут читаться данные, посланные программой работы с портом (20101)
	 * @param portHeartBeat - порт, который будет прослушиваться на предмет наличия сигнала "сердцебиение" (20102) 
	 * @param heartBeatDelay - максимальное время ожидания сигнала "сердцебиение" (5000)
	 * @param executeProgram - строка для запуска программы в отдельном потоке для прослушивания COM порта {@link com_port.ComPort}
	 *  java -jar ComPortReaderWriter.jar COM4 9600 8 1 none 127.0.0.1 20100 20101 20102 4000
	 *  <br>
	 *  для программы общения с SerialPort зеркально отражены порты приема-посылки данных, т.к. это более правильно в контексте другой программы ( выход этой - вход той ),
	 *  время посылки сигнала уменьшено на 1000 мс - для реакции порта на прием данных и оповещение самой программы
	 *   @throws - если не удалось установить прослушивание портов 
	 */
	public SerialPortProxy(int portRead, int portWrite, int portHeartBeat, int heartBeatDelay, String executeProgram, IDataNotify ... listeners) throws Exception{
		if(listeners!=null){
			for(int counter=0;counter<listeners.length;counter++){
				listOfListener.add(listeners[counter]);
			}
		}
		if((listeners==null)||(listeners.length==0)){
			System.err.println("Слушатели входящих данных не подключены!!!");
		}
		// поток, который будет записывать данные в порт
		this.outputStream=new SerialPortProxyOutputStream(portWrite);
		(new SocketPortListener(portRead)).addDataListener(this);
		new SocketHeartBeatProgramRunner(portHeartBeat, heartBeatDelay, executeProgram);
	}
	@Override
	public void notifyDataFromPort(byte[] data) {
		if(data!=null){
			logger.debug("получены данных с порта"+data.length);
			for(IDataNotify listener:listOfListener){
				listener.notifyDataFromPort(data);
			}
		}
	}
	
	public OutputStream getOutputStream(){
		return this.outputStream;
	}
}
