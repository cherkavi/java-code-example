package com.cherkashyn.vitaliy.thrift.server;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;

import com.cherkashyn.vitaliy.thrift.exchange.EmployeeInfo;


public class Server {
	private final static int portNumber=1980;

    public static void main(String args[]){
	      Server srv = new Server();
	      srv.start();
	}

	private void start(){
		try {
			// create arguments 
			TThreadPoolServer.Args arguments=new TThreadPoolServer.Args(new TServerSocket(portNumber)); 
			arguments.inputProtocolFactory(new TBinaryProtocol.Factory(true, true));
			arguments.processor(new EmployeeInfo.Processor<EmployeeInfo.Iface>(new FunctionImpl()));

			TServer server = new TThreadPoolServer(arguments);
	 
			System.out.println("Server on port "+portNumber+" ...");
	         
			server.serve();
		} catch (TTransportException e) {
			e.printStackTrace();
		} 
	}
   
}
