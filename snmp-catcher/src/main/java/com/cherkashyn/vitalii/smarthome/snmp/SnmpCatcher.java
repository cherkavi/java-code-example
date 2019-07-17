package com.cherkashyn.vitalii.smarthome.snmp;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import com.cherkashyn.vitalii.smarthome.snmp.catcher.SnmpTrapCatcher;
import com.cherkashyn.vitalii.smarthome.snmp.message.processor.ConsoleProcessor;


public class SnmpCatcher {
	public static void main(String[] args) throws IOException{
		System.out.println("--- begin ---");
		
		new SnmpTrapCatcher(new ConsoleProcessor());
		
		while(true){
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		}
	}
}
