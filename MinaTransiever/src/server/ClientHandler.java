package server;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import org.apache.mina.common.IoSession;
import org.apache.mina.handler.StreamIoHandler;

import common_object.Transport;

public class ClientHandler extends StreamIoHandler{

	@Override
	protected void processStreamIo(IoSession session, 
								   InputStream in,
								   OutputStream out) {
		// processing *MUST* be done on a new thread
		new Worker(in,out);
	}

}

/** класс, который создает новый поток и обрабатывает данные в этом новом потоке */
class Worker extends Thread{
	private InputStream in;
	private OutputStream out;
	
	/** класс, который создает новый поток и обрабатывает данные в этом новом потоке 
	 * @param in - входящие данные
	 * @param out - исходящие данные 
	 */
	public Worker(InputStream in, OutputStream out){
		this.in=in;
		this.out=out;
		this.start();
	}
	
	@Override
	public void run(){
		try{
			System.out.println("ClientHandler.Worker#run Begin");
			// read from port
			ObjectInputStream input=new ObjectInputStream(in);
			Object object=input.readObject();
			System.out.println("ClientHandler.Worker#run Прочитан объект: "+object);
			// write answer
			if(object instanceof Transport){
				System.out.println("getValue:"+((Transport)object).getValue());
				ObjectOutputStream output=new ObjectOutputStream(out);
				output.writeObject(new Transport("server answer"));
			}else{
				System.err.println("ClientHandler.Worker#run object is not Transport");
			}
			System.out.println("ClientHandler.Worker#run End");
		}catch(Exception ex){
			System.err.println("ClientHandler.Worker#run Exception: "+ex.getMessage());
		}finally{
			try{
				in.close();
			}catch(Exception ex){};
			try{
				out.flush();
				out.close();
			}catch(Exception ex){};
		}
	}
}
