package com.cherkashyn.vitalii.testtask.kaufland.service;

/**
 * compare two strings for defining anagram
 */
public abstract class CompareStrategy {
	protected final String originalValue;
	
	public CompareStrategy(String value){
		this.originalValue=value;
	}
	
	public abstract boolean isAnagram(String value);
	
	public abstract Integer getHash();
}
