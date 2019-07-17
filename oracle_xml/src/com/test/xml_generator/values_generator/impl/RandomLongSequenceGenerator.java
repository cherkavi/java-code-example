package com.test.xml_generator.values_generator.impl;

import java.util.Random;

import com.test.xml_generator.values_generator.ISequenceValues;

/**
 * generate sequences of integer values 
 */
public class RandomLongSequenceGenerator implements ISequenceValues{
	private long additionalValue;
	private long range;
	private Random random=null;
	
	public RandomLongSequenceGenerator(long minRange, long maxRange){
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
	
	
	private long clearLong(long dirtyValue){
		if(dirtyValue<0){
			dirtyValue=(-1)*dirtyValue;
		}
		if(dirtyValue>range){
			int counter=0;
			while(dirtyValue>range){
				dirtyValue=dirtyValue/(++counter*4);
			}
			return dirtyValue;
		}else{
			return dirtyValue;
		}
	}
	
	@Override
	public String next() {
		return Long.toString(this.additionalValue+clearLong(this.random.nextLong()));
	}

}
