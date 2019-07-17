package com.cherkashyn.vitalii.testtask.meteogroup.parser.impl;

import java.util.ArrayList;
import java.util.List;

import com.cherkashyn.vitalii.testtask.meteogroup.domain.ResultElement;
import com.cherkashyn.vitalii.testtask.meteogroup.domain.StringElement;
import com.cherkashyn.vitalii.testtask.meteogroup.parser.Element;

class SmsParser extends ParserStrategy{
	public final static String PROTOCOL_SMS="sms";
	private final static String QUERY_START_MARKER="?";
	
	SmsParser() {
		super(PROTOCOL_SMS);
	}

	@Override
	List<ResultElement<?>> parse(String url) {
		List<ResultElement<?>> result=new ArrayList<ResultElement<?>>();
		String leftOver=bitePrefix(url, PROTOCOL_DELIMITER, Element.Protocol, result);
		
		leftOver=biteSuffix(leftOver, QUERY_START_MARKER, Element.Query, result);
		
		result.add(new StringElement(Element.Path, leftOver));
		return result;
	}

}
