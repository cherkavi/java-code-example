package com.cherkashyn.vitalii.testtask.kaufland.service;

public class NumberAmountStrategy extends CompareStrategy{
	
	private int originalHash;
	
	public NumberAmountStrategy(String value) {
		super(value);
		this.originalHash=calculateAmountOfChars(value);
	}
	
	@Override
	public boolean isAnagram(String compareValue) {
		return this.originalHash==calculateAmountOfChars(compareValue);
	}
	
	private static int calculateAmountOfChars(String firstValue){
		return firstValue.chars().sum();
	}

	@Override
	public Integer getHash() {
		return this.originalHash;
	}

	
}
