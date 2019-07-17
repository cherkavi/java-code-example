package com.test.xml_generator.values_generator.impl;

import com.test.xml_generator.values_generator.ISequenceValues;

/**
 * generator for 
 * aaaa, aaab, aaac, aaad ... 
 */
public class LetterSequenceGenerator implements ISequenceValues{
	private char[] arrayOfChars=null;
	private int minBorder=65;
	private int maxBorder=90;
	
	public LetterSequenceGenerator(){
		this(4);
	}
	
	public LetterSequenceGenerator(int length) {
		this(length, 65, 90);
	}
	
	public LetterSequenceGenerator(int length, int minBorder, int maxBorder){
		this.arrayOfChars=new char[length];
		this.minBorder=minBorder;
		this.maxBorder=maxBorder;
		this.reset();
	}
	
	private void reset(){
		for(int index=0;index<arrayOfChars.length;index++){
			arrayOfChars[index]=(char)minBorder;
		}
	}

	private String arrayToString(){
		return String.copyValueOf(this.arrayOfChars);
	}
	
	private void increaseTheValues(){
		this.arrayOfChars[this.arrayOfChars.length-1]++;
		
		for(int index=(this.arrayOfChars.length-1);index>=0;index--){
			if(this.arrayOfChars[index]>maxBorder){
				if(index==0){
					reset();
					return;
				}
				this.arrayOfChars[index-1]++;
				this.arrayOfChars[index]=(char)minBorder;
			}
		}
	}
	
	@Override
	public String next() {
		increaseTheValues();
		// System.out.println(arrayToString());
		return arrayToString();
	}
	
}
