package com.cherkashyn.vitalii.automation.home.snmp;

import java.io.IOException;
import java.net.InetAddress;

import org.junit.Test;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.transport.DefaultUdpTransportMapping;

public class SnmpReaderTest {

	@Test
	public void givenAddressCheckGpio() throws IOException{
		UdpAddress udpAddress=new UdpAddress("192.168.0.103");
		TransportMapping transport=new DefaultUdpTransportMapping(udpAddress);
		SnmpReader reader=new SnmpReader(new Snmp(transport));
		reader.readOutput("1.3.6.1.4.1.35160.1");
	}
	
}
