package com.cherkashyn.vitalii.accounting.domain;

public enum RowStatus {
	
	ACTIVE((short)1), 
	INACTIVE((short)0);
	
	private short intValue;
	
	RowStatus(short value){
		this.intValue=value;
	}
	
	public short getValue(){
		return intValue;
	}
}
