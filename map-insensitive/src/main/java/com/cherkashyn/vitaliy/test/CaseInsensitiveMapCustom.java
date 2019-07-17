package com.cherkashyn.vitaliy.test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

/**
 * case insensitive realization <br />
 * approach - key to LowerCase
 * 
 */
public class CaseInsensitiveMapCustom<K extends Object> extends HashMap<String, K>{

	private static final long	serialVersionUID	= 1L;
	
	/**
	 * map which contains &lt surrogate key &gt, &lt real key &gt
	 */
	private Map<String, String> realKey=new HashMap<String, String>();

	@Override
	public K put(String key, K value) {
		String surrogateKey=this.transformToSurrogate(key);
		if(realKey.containsKey(surrogateKey)){
			realKey.remove(surrogateKey);
			realKey.put(surrogateKey, key);
		}
		return super.put(surrogateKey, value);
	}
	
	@Override
	public K get(Object key) {
		return super.get(transformToSurrogate(key));
	}
	
	@Override
	public Set<String> keySet() {
		Set<String> returnValue=new HashSet<String>(this.realKey.size());
		for(Map.Entry<String, String> eachEntry: this.realKey.entrySet()){
			returnValue.add(eachEntry.getValue());
		}
		return returnValue;
	}
	
	@Override
	public boolean containsKey(Object key) {
		return super.containsKey(transformToSurrogate(key));
	}
	

	private String transformToSurrogate(Object value){
		if(value==null){
			return null;
		}
		return StringUtils.lowerCase(value.toString());
	}
	
}
