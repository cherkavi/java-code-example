package com.cherkashyn.vitalii.testtask.meteogroup.parser.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.cherkashyn.vitalii.testtask.meteogroup.domain.ResultElement;
import com.cherkashyn.vitalii.testtask.meteogroup.domain.StringElement;
import com.cherkashyn.vitalii.testtask.meteogroup.parser.Element;

class HttpParser extends ParserStrategy{
	public final static String PROTOCOL="http";
	public final static String PROTOCOL_SECURE="https";
	protected final static String PORT_DELIMITER=":";
	
	
	HttpParser() {
		super(PROTOCOL, PROTOCOL_SECURE);
	}
	
	HttpParser(String ... protocols) {
		super(protocols);
	}

	private final static String NECESSARY_PREAMBULA="//";
	private final static String QUERY_START_MARKER="?";
	private final static String FRAGMENT_START_MARKER="#";
	

	protected String checkAndBiteNecessaryPreambula(String value){
		if(!value.startsWith(NECESSARY_PREAMBULA)){
			throw new IllegalArgumentException("http like protocol should contains preambula: "+NECESSARY_PREAMBULA);
		}
		return bitePrefix(value, NECESSARY_PREAMBULA.length());
	}
	
	@Override
	List<ResultElement<?>> parse(String url) {
		List<ResultElement<?>> result=new ArrayList<ResultElement<?>>();
		String leftOver=bitePrefix(url, PROTOCOL_DELIMITER, Element.Protocol, result);
		
		leftOver=checkAndBiteNecessaryPreambula(leftOver);
		
		// Element.Host, Element.Port
		
		String hostString=StringUtils.substringBefore(leftOver, URL_DELIMITER);
		parseHostString(hostString, result);
		
		// System.out.println(leftOver);
		
		// leftOver=leftOver.substring(hostString.length()+URL_DELIMITER.length());
		leftOver=bitePrefix(leftOver, hostString.length()+URL_DELIMITER.length());
		if(leftOver.length()==0){
			return result;
		}
		leftOver=biteSuffix(leftOver, FRAGMENT_START_MARKER, Element.Fragment, result);
		leftOver=biteSuffix(leftOver, QUERY_START_MARKER, Element.Query, result);
		result.add(new StringElement(Element.Path, leftOver));
		
		return result;
	}
	
	/**
	 * parse string between {@link Element#Protocol} and {@link Element#Path}
	 * @param hostString
	 * @param elements
	 */
	protected void parseHostString(String hostString, List<ResultElement<?>> result){
		String leftOver=this.biteSuffix(hostString, PORT_DELIMITER, Element.Port, result, false, new IntegerConverter());
		result.add(new StringElement(Element.Host, leftOver));
	}

}
