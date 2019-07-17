package com.cherkashyn.vitalii.smarthome.snmp.message.processor;

import org.snmp4j.CommandResponderEvent;

/** */
public interface MessageProcessor {
	/**
	 * 
	 * @param commandEvent
	 */
	public void notify(CommandResponderEvent commandEvent);
}
