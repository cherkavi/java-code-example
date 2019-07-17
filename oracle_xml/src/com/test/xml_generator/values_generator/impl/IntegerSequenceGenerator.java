package com.test.xml_generator.values_generator.impl;

import com.test.xml_generator.values_generator.ISequenceValues;

public class IntegerSequenceGenerator implements ISequenceValues{
	private int minRange;
	private int maxRange;
	private int currentValue;
	
	public IntegerSequenceGenerator(int minRange, int maxRange){
		if(minRange>maxRange){
			throw new IllegalArgumentException("check values ");
		}
		if(minRange<0){
			throw new IllegalArgumentException("check the values - can't be a negative ");
		}
		this.minRange=minRange;
		this.maxRange=maxRange;
		this.currentValue=minRange;
	}
	
	
	@Override
	public String next() {
		this.currentValue++;
		if(this.currentValue>this.maxRange){
			this.currentValue=this.minRange;
		}
		return Integer.toString(this.currentValue);
	}

}
