package com.cherkashyn.vitalii.testtask.meteogroup.parser.impl;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.cherkashyn.vitalii.testtask.meteogroup.domain.ResultElement;
import com.cherkashyn.vitalii.testtask.meteogroup.domain.StringElement;
import com.cherkashyn.vitalii.testtask.meteogroup.parser.Element;

abstract class ParserStrategy {
	static final String PROTOCOL_DELIMITER=":";
	static final String URL_DELIMITER="/";
	
	private String[] protocolNames;
	
	ParserStrategy(String ... protocolName){
		if(protocolName==null){
			throw new IllegalArgumentException("protocol should be specified");
		}
		this.protocolNames=protocolName;
	}
	
	boolean forProtocol(String value){
		return Arrays.binarySearch(protocolNames, value)>=0;
	}
	
	abstract List<ResultElement<?>> parse(String url);
	
	/**
	 * bite string from original string ( from the tail )
	 * @param value - source string
	 * @param delimiter - delimiter
	 * @param elementName - name of element for result
	 * @param result - result collection
	 * @return
	 */
	protected String biteSuffix(String value, String delimiter, Element elementName, List<ResultElement<?>> result) {
		return biteSuffix(value, delimiter, elementName, result, false, null);
	}

	/**
	 * bite string from original string ( from the tail )
	 * @param value - source string
	 * @param delimiter - delimiter
	 * @param elementName - name of element for result
	 * @param result - result collection
	 * @param strong - need to bite even if delimiter was not found
	 * @return
	 */
	protected String biteSuffix(String value, String delimiter, Element elementName, List<ResultElement<?>> result, boolean strong) {
		return biteSuffix(value, delimiter, elementName, result, strong, null);
	}
	
	/**
	 * bite string from original string ( from the tail )
	 * @param value - source string
	 * @param delimiter - delimiter
	 * @param elementName - name of element for result
	 * @param result - result collection
	 * @param strong - need to bite even if delimiter was not found
	 * @param converter - convert value before add to result
	 * @return
	 */
	protected <T> String biteSuffix(String value, String delimiter, Element elementName, List<ResultElement<?>> result, boolean strong, TypeConverter<T> converter) {
		int index=value.indexOf(delimiter);
		if(index>0){
			addResultElement(result, elementName, value.substring(index+1), converter);
			return value.substring(0, index);
		}
		
		if(!strong){
			return value;
		}else{
			addResultElement(result, elementName, value, converter);
			return StringUtils.EMPTY;
		}
		
		
	}
	
	private <T> void  addResultElement(List<ResultElement<?>> result, Element element, String value, TypeConverter<T> converter){
		if(converter!=null){
			result.add(new ResultElement<T>(element, converter.convert(value)));
		}else{
			result.add(new StringElement(element, value));
		}
	}

	protected String bitePrefix(String leftOver, int index) {
		if(index<0){
			return leftOver;
		}
		if(index>leftOver.length()){
			return "";
		}
		return leftOver.substring(index);
	}
	
	protected String bitePrefix(String value, String protocolDelimiter, Element element, List<ResultElement<?>> result) {
		int index=value.indexOf(protocolDelimiter);
		if(index<0){
			return value;
		}
		result.add(new StringElement(element, value.substring(0, index)));
		return value.substring(index+protocolDelimiter.length());
	}
	
	protected interface TypeConverter<T>{
		T convert(String value);
	}
	
	protected class IntegerConverter implements TypeConverter<Integer>{

		@Override
		public Integer convert(String value) {
			if(value==null || value.length()==0){
				return null;
			}
			return Integer.parseInt(value);
		}
		
	}
}
