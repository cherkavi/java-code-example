package com.cherkashyn.vitaliy.test;

import java.util.Map;

import org.apache.commons.collections.map.CaseInsensitiveMap;
import com.cherkashyn.vitaliy.test.CaseInsensitiveMapCustom;
import org.junit.Test;

import junit.framework.Assert;

/**
 * Unit test for unsensitive Case
 */
public class CaseInsensitiveMapCustomTest{
	
	@Test
	public void testApacheCommons(){
		@SuppressWarnings("unchecked")
		Map<String,Object> map=new CaseInsensitiveMap();
		test(map);
	}
	
	@Test
	public void testCustom(){
		Map<String, Object> map=new CaseInsensitiveMapCustom<Object>();
		test(map);
	}

	@Test
	public void testCustom2(){
		Map<String, Object> map=new CaseInsensitiveMapCustom2<Object>();
		test(map);
	}
	
	public void test(Map<String, Object> map){
		map.clear();
		map.put("abc", "1");
		map.put("Abc", "2");
		map.put("aBc", "3");
		map.put("abC", "4");
		Assert.assertEquals(1, map.size());
		
		Assert.assertTrue(map.containsKey("abc"));
		Assert.assertTrue(map.containsKey("Abc"));
		Assert.assertTrue(map.containsKey("aBc"));
		Assert.assertTrue(map.containsKey("abC"));
		
		map.entrySet().iterator().next().getKey().equals("abC");
	}
}
