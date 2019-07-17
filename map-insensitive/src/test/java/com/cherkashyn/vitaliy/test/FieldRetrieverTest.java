package com.cherkashyn.vitaliy.test;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

public class FieldRetrieverTest {

	@Test
	public void givenObjectGetFields(){
		List<String> fields=FieldRetriever.getFields(new TestClass());
		Assert.assertEquals(2, fields.size());
		Assert.assertTrue(fields.contains("fieldOne"));
		Assert.assertTrue(fields.contains("fieldTwo"));
	}
	

	public static class TestClass{
		private String fieldOne;
		Integer fieldTwo;
		
		@Override
		public String toString() {
			return "TestClass [fieldOne=" + fieldOne + ", fieldTwo=" + fieldTwo + "]";
		}
		
	}
}
