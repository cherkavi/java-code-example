package com.ubs.omnia.tools.generator.csv.value;

import org.apache.commons.lang3.StringUtils;

public class IntegerGenerator implements ValueNextGenerator{
	private final int length;
	private static final char ZERO='0';
	private int currentValue;
	
	public IntegerGenerator(int stringLength){
		this(stringLength, 0);
	}
	
	public IntegerGenerator(int stringLength, int initValue){
		this.length=stringLength;
		this.currentValue=initValue;
	}
	
	@Override
	public void next(){
		increase(1);
	}

	public void increase(int step){
		this.currentValue+=step;
	}
	
	
	@Override
	public String getValue() {
		return StringUtils.leftPad(Integer.toString(currentValue), length, ZERO);
	}

}
