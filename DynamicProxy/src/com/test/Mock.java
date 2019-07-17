package com.test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

public class Mock implements IMock{
	private int intValue;
	private String stringValue;
	
	public Mock(){
		
	}
	
	public Mock(int intValue, String stringValue) {
		this.intValue = intValue;
		this.stringValue = stringValue;
	}
	
	public int getIntValue() {
		return intValue;
	}
	public void setIntValue(int intValue) {
		this.intValue = intValue;
	}
	public String getStringValue() {
		return stringValue;
	}
	public void setStringValue(String stringValue) {
		this.stringValue = stringValue;
	}
	
}

