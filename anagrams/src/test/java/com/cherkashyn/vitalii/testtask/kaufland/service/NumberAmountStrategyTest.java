package com.cherkashyn.vitalii.testtask.kaufland.service;

import org.junit.Assert;
import org.junit.Test;

public class NumberAmountStrategyTest {
	
	@Test
	public void checkAnagram(){
		// given
		String value="abcdefgh";
		
		// when 
		NumberAmountStrategy service=new NumberAmountStrategy(value);
		
		// then
		Assert.assertTrue(service.isAnagram("habcdefg"));
		Assert.assertTrue(service.isAnagram("ghabcdef"));
		Assert.assertTrue(service.isAnagram("gfhabcde"));
		Assert.assertTrue(service.isAnagram("efghabcd"));
		Assert.assertTrue(service.isAnagram("cdefghab"));
		Assert.assertTrue(service.isAnagram("bcdefgha"));
		Assert.assertFalse(service.isAnagram("bbdefgha"));
		Assert.assertFalse(service.isAnagram("bceefgha"));
		Assert.assertFalse(service.isAnagram("bcddfgha"));
		Assert.assertFalse(service.isAnagram("bcdeffha"));
	}
	
	@Test
	public void checkHash(){
		// given
		String value1="abcdefgh";
		String value2="habcdefg";
		String value3="ghabcdef";
		String value4="gfhabcde";
		
		// when
		// then 
		Assert.assertTrue(new NumberAmountStrategy(value1).getHash().equals(new NumberAmountStrategy(value2).getHash()));
		Assert.assertTrue(new NumberAmountStrategy(value1).getHash().equals(new NumberAmountStrategy(value3).getHash()));
		Assert.assertTrue(new NumberAmountStrategy(value1).getHash().equals(new NumberAmountStrategy(value4).getHash()));
		Assert.assertTrue(new NumberAmountStrategy(value2).getHash().equals(new NumberAmountStrategy(value3).getHash()));
		Assert.assertTrue(new NumberAmountStrategy(value2).getHash().equals(new NumberAmountStrategy(value4).getHash()));
		Assert.assertTrue(new NumberAmountStrategy(value3).getHash().equals(new NumberAmountStrategy(value4).getHash()));
	}
	
}
