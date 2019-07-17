package com.cherkashyn.vitaliy.test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.ListUtils;

public class FieldRetriever {
	private final static List<String> objectFields=getAllFields(new Object());
	
	@SuppressWarnings("unchecked")
	public static List<String> getFields(Object object){
		return ListUtils.subtract(getAllFields(object), objectFields);
	}
	
	private static List<String> getAllFields(Object object){
		List<String> returnValue=new ArrayList<String>();
		for(Field eachField: object.getClass().getDeclaredFields()){
			eachField.setAccessible(true);
			returnValue.add(eachField.getName());
		}
		return returnValue;
	}
}
