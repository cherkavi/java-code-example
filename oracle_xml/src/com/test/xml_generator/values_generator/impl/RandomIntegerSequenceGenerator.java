package com.test.xml_generator.values_generator.impl;

import java.util.Random;

import com.test.xml_generator.values_generator.ISequenceValues;

/**
 * generate sequences of integer values 
 */
public class RandomIntegerSequenceGenerator implements ISequenceValues{
	private int additionalValue;
	private int range;
	private Random random=null;
	
	public RandomIntegerSequenceGenerator(int minRange, int maxRange){
		if(minRange>maxRange){
			System.err.println("Check values:   -minRange:"+minRange+"     -maxRange:"+maxRange);
			throw new IllegalArgumentException("check values ");
		}
		if(minRange<0){
			throw new IllegalArgumentException("check the values - can't be a negative ");
		}
		this.additionalValue=minRange;
		this.range=maxRange-minRange;
		this.random=new Random();
	}
	
	
	@Override
	public String next() {
		return Integer.toString(this.additionalValue+this.random.nextInt(this.range));
	}

}
