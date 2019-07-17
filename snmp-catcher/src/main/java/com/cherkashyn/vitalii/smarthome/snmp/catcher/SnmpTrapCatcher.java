package com.cherkashyn.vitalii.smarthome.snmp.catcher;

import java.io.IOException;

import org.snmp4j.CommandResponder;
import org.snmp4j.CommandResponderEvent;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.transport.DefaultUdpTransportMapping;

import com.cherkashyn.vitalii.smarthome.snmp.message.processor.MessageProcessor;

public class SnmpTrapCatcher {
	private final static String DEFAULT_HOST="192.168.0.1";
	private final static int DEFAULT_PORT=162;
	private Snmp snmp;
	private Address targetAddress;
	private TransportMapping transport ;	
	
	public SnmpTrapCatcher(MessageProcessor ... processors) throws IOException{
		this(DEFAULT_HOST, DEFAULT_PORT, processors);
	}

	private String host;
	private int port;
	
	public SnmpTrapCatcher(String host, int port, MessageProcessor[] processors) throws IOException {
		this.host=host;
		this.port=port;
		init(processors);
	}
	
	void init(final MessageProcessor[] processors) throws IOException{
		if(processors==null){
			return;
		}
		targetAddress = GenericAddress.parse("udp:"+host+"/"+port);
		transport = new DefaultUdpTransportMapping();
	    snmp = new Snmp(transport);		
	    CommandResponder trapPrinter = new CommandResponder() {
			public synchronized void processPdu(CommandResponderEvent commandEvent){
				for(MessageProcessor eachProcessor:processors){
					eachProcessor.notify(commandEvent);
				}
			}
		};
		snmp.addNotificationListener(targetAddress, trapPrinter);		
		// transport.listen();
		snmp.listen();
	}

}
