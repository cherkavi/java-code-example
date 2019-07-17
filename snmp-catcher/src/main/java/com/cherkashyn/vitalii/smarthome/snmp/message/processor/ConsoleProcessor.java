package com.cherkashyn.vitalii.smarthome.snmp.message.processor;

import org.snmp4j.CommandResponderEvent;
import org.snmp4j.PDU;

public class ConsoleProcessor implements MessageProcessor{

	@Override
	public void notify(CommandResponderEvent commandEvent) {
		PDU command = commandEvent.getPDU();
        if (command != null) {
			System.out.println("Trap was catched: " +command.toString());
		}else{
			System.out.println("CommandEvent:"+commandEvent);
		}
	}

}
