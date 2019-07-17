package com.cherkashyn.snmp;

import com.cherkashyn.snmp.catcher.SnmpTrapCatcher;

public class SnmpCatcher {
	public static void main(String[] args){
		new SnmpTrapCatcher().start();
	}
}
