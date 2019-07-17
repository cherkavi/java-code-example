package process_exchange.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class Client {
	public static void main(String[] args) throws Exception {
		System.out.println("begin");
		
		Client client=new Client("127.0.0.1", 2010);
		
		Future<ResultWrap> result=client.sendCommand("command from server unblocking");
		ResultWrap connectResult=result.get();
		if(connectResult!=null){
			System.out.println("ReturnResult: "+connectResult.getValue());
		}
		// Client.EConnectResult connectResult=client.sendCommandBlocking("command on the server", result);
		if(connectResult!=null){
			switch(connectResult.getConnectResult()){
				case RESULT_OK: {
					System.out.println("Result: "+connectResult.getValue());
				};break;
				case CONNECT_ERROR:{
					System.out.println("Connect Error:");
				}; break;
				case EXCHANGE_ERROR: {
					System.out.println("Exchange Error: ");
				};break;
				default:break;
			}
		}else{
			// connect result is null
		}
		System.out.println("-end-");
	}

	private String hostName;
	private int port;
	
	/** синхронный обмен с Socket-ным соединением  */
	public Client(String hostName, int port){
		this.hostName=hostName;
		this.port=port;
	}
	
	/** послать команду на удаленный Socket и получить ответ (блокирующий, синхронный ) */
	public EConnectResult sendCommandBlocking(String command, ResultWrap resultWrap ){
		try{
			Socket socket=new Socket(hostName,port);
			PrintWriter out=new PrintWriter(socket.getOutputStream(),true);
			BufferedReader reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out.println(command);
			String backFromServer=reader.readLine();
			resultWrap.setValue(backFromServer);
			socket.close();
			// System.out.println("Back from Server:"+backFromServer);
			return EConnectResult.RESULT_OK;
		}catch(ConnectException ex){
			System.err.println("server maybe does not work ("+ex.getMessage()+")");
			return EConnectResult.CONNECT_ERROR;
		}catch(Exception ex){
			System.err.println("Client#sendCommandBlocking: "+ex.getMessage());
			return EConnectResult.EXCHANGE_ERROR;
		}
	}
	
	/** послать команду на удаленный  */
	public Future<ResultWrap> sendCommand(String command){
		final String commandValue=command;
		ExecutorService service=new ScheduledThreadPoolExecutor(2);
		return service.submit(new Callable<ResultWrap>(){
			@Override
			public ResultWrap call() throws Exception {
				ResultWrap returnValue=new ResultWrap();
				try{
					Socket socket=new Socket(hostName,port);
					PrintWriter out=new PrintWriter(socket.getOutputStream(),true);
					BufferedReader reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
					out.println(commandValue);
					String backFromServer=reader.readLine();
					returnValue.setValue(backFromServer);
					socket.close();
					returnValue.setConnectResult(EConnectResult.RESULT_OK);
				}catch(ConnectException ex){
					System.err.println("server maybe does not work ("+ex.getMessage()+")");
					returnValue.setValue(null);
					returnValue.setConnectResult(EConnectResult.CONNECT_ERROR);
				}catch(Exception ex){
					System.err.println("Client#sendCommandBlocking: "+ex.getMessage());
					returnValue.setValue(null);
					returnValue.setConnectResult(EConnectResult.EXCHANGE_ERROR);
				}
				return returnValue;
			}
		});
	}
	
	public enum EConnectResult{
		/** соединение прошло успешно  */
		RESULT_OK,
		/** ошибка содединения с удаленным хостом, возможно не запущен */
		CONNECT_ERROR,
		/** произошла ошибка информационного обмена данными с севером */
		EXCHANGE_ERROR;
		
	}
}
