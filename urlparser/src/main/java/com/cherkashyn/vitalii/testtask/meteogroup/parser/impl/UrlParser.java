package com.cherkashyn.vitalii.testtask.meteogroup.parser.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.cherkashyn.vitalii.testtask.meteogroup.domain.ResultElement;

public class UrlParser {
	private UrlParser(){
	}
	
	private final static Set<ParserStrategy> PARSERS=new HashSet<ParserStrategy>();
	static{
		PARSERS.add(new HttpParser());
		PARSERS.add(new FtpParser());
		PARSERS.add(new FileParser());
		PARSERS.add(new TelParser());
		PARSERS.add(new SmsParser());
	}
	
	static final ParserStrategy DEFAULT_STRATEGY=new HttpParser();
	
	
	public static List<ResultElement<?>> parse(String url){
		String protocol=getProtocol(url);
		if(protocol==null){
			throw new IllegalArgumentException("url without Protocol !!!");
		}
		ParserStrategy parser=null;
		for(ParserStrategy eachStrategy:PARSERS){
			if(eachStrategy.forProtocol(protocol)){
				parser=eachStrategy;
				break;
			}
		}
		if(parser==null){
			parser=DEFAULT_STRATEGY;
		}
		return parser.parse(url);
	}


	private final static String PROTOCOL_DELIMITER=":";

	/**
	 * get protocol from url
	 * @param url
	 * @return null, when it is not specified
	 */
	private static String getProtocol(String url) {
		int index=url.indexOf(PROTOCOL_DELIMITER);
		if(index<0){
			return null;
		}
		return url.substring(0, index).toLowerCase();
	}
	
}
