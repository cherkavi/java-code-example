package com.cherkashyn.vitalii.automation.home.snmp;

import java.io.IOException;

import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.Target;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.Variable;
import org.snmp4j.smi.VariableBinding;

/**
 * http://www.jayway.com/2010/05/21/introduction-to-snmp4j/
 */
public class SnmpReader {
	private Snmp snmp;
	
	public SnmpReader(Snmp value){
		this.snmp=value;
	}
	
	/**
	 * path to SNMP device ( like
	 * @param snmpPath path into MIB file ( like "1.3.6.1.4.1.35160.1" )
	 * @return
	 * @throws IOException 
	 */
	public boolean readOutput(String snmpPath) throws IOException{
		PDU request = new PDU();
		request.setType(PDU.GET);
		request.add(new VariableBinding(new OID(snmpPath)));
		this.snmp.send(request, getTarget());
		return true;
	}
	
	private Target getTarget() {
	    Address targetAddress = GenericAddress.parse("udp:192.168.0.103/161");
	    CommunityTarget target = new CommunityTarget();
	    target.setCommunity(new OctetString("public"));
	    // target.setAddress(targetAddress);
	    target.setRetries(2);
	    target.setTimeout(1500);
	    target.setVersion(SnmpConstants.version2c);
	    return target;
	}
}
