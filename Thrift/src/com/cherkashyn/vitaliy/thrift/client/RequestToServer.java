package com.cherkashyn.vitaliy.thrift.client;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import com.cherkashyn.vitaliy.thrift.exchange.Address;
import com.cherkashyn.vitaliy.thrift.exchange.EmployeeInfo;
import com.cherkashyn.vitaliy.thrift.exchange.Worker;

public class RequestToServer {
	private final static String server="127.0.0.1";
	private final static int portNumber=1980;
	

	public static void main(String[] args) {
		TTransport transport=null;
		try {
			transport = new TSocket(server, portNumber);
			EmployeeInfo.Client client = new EmployeeInfo.Client(new TBinaryProtocol(transport));
			transport.open();
			Worker worker=client.getWorkerById(1);
			
			System.out.println(">>> Worker:" + getWorkerAsString(worker));
		}  catch (TTransportException e) {
			e.printStackTrace();
		} catch (TException e) {
			e.printStackTrace();
		}finally{
			try{
				transport.close();
			}catch(Exception ex){};
		}
	}
	
	private static String getWorkerAsString(Worker worker) {
		StringBuilder returnValue=new StringBuilder();
		returnValue.append("worker.id:").append(worker.id).append("\n");
		returnValue.append("worker.name_first:").append(worker.name_first).append("\n");
		returnValue.append("worker.name_second:").append(worker.name_second).append("\n");
		returnValue.append("worker.phone:").append(worker.phone).append("\n");
		
		if(worker.home_address!=null){
			Address address=worker.home_address;
			returnValue.append("	address.post_index:").append(address.post_index).append("\n");
			returnValue.append("	address.city:").append(address.city).append("\n");
			returnValue.append("	address.street:").append(address.street).append("\n");
			returnValue.append("	address.b_number:").append(address.b_number).append("\n");
			returnValue.append("	address.flat_number:").append(address.flat_number).append("\n");
		}
		return returnValue.toString();
	}
	
}
