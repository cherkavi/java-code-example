package com.investigation.xml_parsers;

import java.text.MessageFormat;

import org.apache.commons.lang3.StringUtils;

public class StringXmlCutter {
	private final static String XPATH_DELIMITER="/";
	private final static int DEEP=2;
	
	private String checkXpathGetTag(String xpath) throws IllegalArgumentException{
		String clearXpath=StringUtils.trimToNull(xpath);
		if(clearXpath==null){
			throw new IllegalArgumentException(" xpath is empty");
		}
		String[] values=StringUtils.split(clearXpath);
		if(values.length!=DEEP){
			throw new IllegalArgumentException(MessageFormat.format("need to have {0} LEVEL(s) of XPath only, but:{1} ", DEEP, xpath));
		}
		String tagForReturn=StringUtils.trimToNull(values[DEEP-1]);
		if(tagForReturn==null){
			throw new IllegalArgumentException("check Leaf of Xpath:"+xpath);
		}
		return tagForReturn;
	}
	
	private final static String XML_PREAMBULA="<";
	private final static String XML_POSTAMBULA="</";
	
	private String buildStartTag(String tagName){
		return XML_PREAMBULA+tagName;
	}
	
	private String buildEndTag(String tagName){
		return XML_POSTAMBULA+tagName;
	}
	
	public String removeXmlTag(String xmlString, String xpath){
		String tagName=checkXpathGetTag(xpath);
		String beginString=buildStartTag(tagName);
		String endString=buildEndTag(tagName);
		int indexOfStart=getIndexOfTag(xmlString, beginString, 0);
		if(indexOfStart>0){
			// xml tag was found
			int indexOfEnd=getIndexOfTag(xmlString, endString, indexOfStart);
			if(indexOfEnd<indexOfStart){
				// can't find xml close tag - BAD XML 
				return xmlString;
			}
			return StringUtils.substring(xmlString, 0, indexOfStart-1)+StringUtils.substring(xmlString, indexOfEnd+XML_PREAMBULA.length()+tagName.length()+XML_POSTAMBULA.length());
		}else{
			return xmlString;
		}
	}

	private final static String CDATA_BEGIN="<![CDATA[";
	private final static String CDATA_END="]]>";
	
	private int getIndexOfTag(String xmlString, String searchString, int startPosition) {
		int tagPosition=StringUtils.indexOf(xmlString, searchString, startPosition);
		while(true){
			int cdataPosition=StringUtils.indexOf(CDATA_BEGIN, startPosition);
			if(cdataPosition>0){
				// TODO
			}else{
				return tagPosition;
			}
		}
	}
}
