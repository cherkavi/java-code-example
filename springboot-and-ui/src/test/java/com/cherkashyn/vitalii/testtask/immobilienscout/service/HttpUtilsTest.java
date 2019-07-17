package com.cherkashyn.vitalii.testtask.immobilienscout.service;

import org.junit.Test;

import junit.framework.Assert;

public class HttpUtilsTest {

	@Test
	public void checkAccessibleUrl(){
		// given
		String url="http://google.com";
		
		// when
		boolean isAccessible=HttpUtils.checkUrl(url);
		
		// then
		Assert.assertTrue(isAccessible);
	}
	
	@Test
	public void checkWrongUrl(){
		// given
		String url="http://google333333333.com";
		
		// when
		boolean isAccessible=HttpUtils.checkUrl(url);
		
		// then
		Assert.assertFalse(isAccessible);
	}
}
