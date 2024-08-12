package com.cherkashyn.vitaliy.bpmn.xml_extender;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.cherkashyn.vitaliy.bpmn.utility.XmlUtility;

public class FillAttributesXmlProcessor implements XmlProcessor{
	private String xpath;
	private Map<String, String> attributes;
	
	public FillAttributesXmlProcessor(String elementXpath, Map<String, String> attributes){
		this.xpath=StringUtils.trimToEmpty(elementXpath);
		this.attributes=attributes;
	}
	
	@Override
	public Document processDocument(Document document, String fileName) throws Exception {
		XmlUtility xmlUtility=new XmlUtility();
		Element element=(Element)xmlUtility.getNodeFromDocument(document, xpath);
		if(element!=null){
			Iterator<Entry<String, String>> iterator=this.attributes.entrySet().iterator();
			while(iterator.hasNext()){
				Entry<String, String> next=iterator.next();
				element.setAttribute(StringUtils.trim(next.getKey()) , StringUtils.trim(next.getValue()));
			}
		}
		return document;
	}

}
