package com.cherkashyn.snmp.catcher;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.snmp4j.CommandResponder;
import org.snmp4j.CommandResponderEvent;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.transport.DefaultUdpTransportMapping;

public class SnmpTrapCatcher extends Thread{
	private final static String DEFAULT_HOST="192.168.0.10";
	private final static int DEFAULT_PORT=162;
	private Snmp snmp;
	private Address targetAddress;
	private TransportMapping transport ;	
	
	public SnmpTrapCatcher(){
		this(DEFAULT_HOST, DEFAULT_PORT);
	}

	private String host;
	private int port;
	
	public SnmpTrapCatcher(String host, int port) {
		this.host=host;
		this.port=port;
	}
	
	void init() throws IOException{
		targetAddress = GenericAddress.parse("udp:"+host+"/"+port);
		transport = new DefaultUdpTransportMapping();
	    snmp = new Snmp(transport);		
	    CommandResponder trapPrinter = new CommandResponder() {
			public synchronized void processPdu(CommandResponderEvent commandEvent){
				PDU command = commandEvent.getPDU();
                if (command != null) {
					System.out.println("Trap was catched: " +command.toString());
				}else{
					System.out.println("CommandEvent:"+commandEvent);
				}
			}
		};
		snmp.addNotificationListener(targetAddress, trapPrinter);		
		// transport.listen();
		// snmp.listen();
	}
	
	@Override
	public void run() {
		try {
			init();
		} catch (IOException e) {
			throw new RuntimeException("Can't start SNMP Catcher:"+e.getMessage());
		}

		// nothing to do, only stay alive
		while(true){
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		}
	}
}
