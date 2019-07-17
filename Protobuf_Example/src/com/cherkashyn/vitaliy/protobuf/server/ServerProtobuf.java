package com.cherkashyn.vitaliy.protobuf.server;

import java.util.concurrent.Executors;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import com.google.protobuf.BlockingService;
import com.google.protobuf.Message;
import com.google.protobuf.RpcCallback;
import com.google.protobuf.RpcController;
import com.google.protobuf.Service;
import com.google.protobuf.ServiceException;
import com.google.protobuf.Descriptors.MethodDescriptor;
import com.google.protobuf.Descriptors.ServiceDescriptor;
import com.googlecode.protobuf.socketrpc.RpcServer;
import com.googlecode.protobuf.socketrpc.ServerRpcConnectionFactory;
import com.googlecode.protobuf.socketrpc.SocketRpcConnectionFactories;

public class ServerProtobuf {
	static{
		BasicConfigurator.configure();
	}
	private final static Logger logger=Logger.getLogger(ServerProtobuf.class);
	
	private static final int port=1980;
	
	public static void main(String[] args){
		logger.info("create factory");
		ServerRpcConnectionFactory rpcConnectionFactory 
			= SocketRpcConnectionFactories.createServerRpcConnectionFactory(port);
		logger.info("create server");
		RpcServer server = new RpcServer(rpcConnectionFactory, 
		    Executors.newFixedThreadPool(10), true);
		// server.registerService(new ServiceImpl()); // For non-blocking impl
		logger.info("register ");
		server.registerBlockingService(new ServiceImplBlocking()); // For blocking impl
		logger.info("Start server");
		server.run();
	}

	/**
	 * Blocking service  
	 */
	static class ServiceImplBlocking implements BlockingService{
		private Logger logger=Logger.getLogger(ServiceImplBlocking.class);
		
		@Override
		public Message callBlockingMethod(MethodDescriptor methodDescription,
										  RpcController rpcController, 
										  Message message) throws ServiceException {
			logger.info("callBlockingMethod:");
			return null;
		}

		@Override
		public ServiceDescriptor getDescriptorForType() {
			logger.info("getDescriptorForType:");
			return null;
		}

		@Override
		public Message getRequestPrototype(MethodDescriptor methodDescription) {
			logger.info("getRequestPrototype:");
			return null;
		}

		@Override
		public Message getResponsePrototype(MethodDescriptor methodDescription) {
			logger.info("getResponsePrototype:");
			return null;
		}
		
	}
	
	/**
	 * Non-blocking service 
	 */
	static class ServiceImpl implements Service{
		@Override
		public void callMethod(MethodDescriptor methodDescription, 
							   RpcController controller,
							   Message message, 
							   RpcCallback<Message> callback) {
		}

		@Override
		public ServiceDescriptor getDescriptorForType() {
			return null;
		}

		@Override
		public Message getRequestPrototype(MethodDescriptor methodDescription) {
			return null;
		}

		@Override
		public Message getResponsePrototype(MethodDescriptor methodDescription) {
			return null;
		}
	}
}
