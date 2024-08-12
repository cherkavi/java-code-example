package com.cherkashyn.vitaliy.bpmn.xml_extender;

import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.cherkashyn.vitaliy.bpmn.utility.XmlUtility;

public class ProcessIdAsFileNameXmlProcessor implements XmlProcessor{
	
	@Override
	public Document processDocument(Document document, String fileName) throws XPathExpressionException, ClassCastException {
		XmlUtility xmlUtility=new XmlUtility();
		Element element=(Element)xmlUtility.getNodeFromDocument(document, "/definitions/process");
		element.setAttribute("id", fileName);
		return document;
	}

}
