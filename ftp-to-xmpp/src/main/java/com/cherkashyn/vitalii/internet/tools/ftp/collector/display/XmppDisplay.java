package com.cherkashyn.vitalii.internet.tools.ftp.collector.display;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Message.Type;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;


import com.cherkashyn.vitalii.internet.tools.ftp.collector.domain.DisplayElement;

public class XmppDisplay implements Display{

	private XMPPConnection connection;
	
	private String[] recipients;
	private String login;
	
	public XmppDisplay(String server, int port, String login, String password, String ... destinationAddress) throws SmackException, IOException, XMPPException, NoSuchAlgorithmException, KeyManagementException{
		ConnectionConfiguration config = new ConnectionConfiguration(server, port);
		config.setSecurityMode(ConnectionConfiguration.SecurityMode.enabled);
		config.setSocketFactory(new DummySSLSocketFactory());
		
//		SSLContext context=SSLContext.getInstance("TLS");
//		context.init(new KeyManager[0],new TrustManager[]{new FTPSTrustManager()},new SecureRandom());
//		config.setCustomSSLContext(context);

		// config.setDebuggerEnabled(true);
		// SSLContext sc = SSLContext.getInstance ("TLSv1.2") ;
		// sc.init (null,null,null) ;
		// config.setCustomSSLContext(sc);
		
		connection = new XMPPTCPConnection(config);
		this.connection.connect();
		this.connection.login(login, password);
		this.login=login;
		this.recipients=destinationAddress;
	}
	
	
	@Override
	public void show(DisplayElement element) throws Exception {
		for(String eachAddress: this.recipients){
			sendMessageToRecipients(eachAddress, element.toString());
		}
	}
	
	
	private void sendMessageToRecipients(String address, String text) throws NotConnectedException{
		Message message=new Message();
		message.setType(Type.chat);
		message.setBody(text);
		message.setTo(address);
		message.setFrom(this.login);
		connection.sendPacket(message);
	}
	
	
	protected void finalize() throws Throwable {
		this.connection.disconnect();
		
		super.finalize();
	};

}
