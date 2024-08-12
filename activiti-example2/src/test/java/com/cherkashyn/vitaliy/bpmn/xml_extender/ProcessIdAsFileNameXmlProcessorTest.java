package com.cherkashyn.vitaliy.bpmn.xml_extender;

import java.io.IOException;
import java.text.MessageFormat;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;


public class ProcessIdAsFileNameXmlProcessorTest extends CommonXmlProcessorTest{
	final String classNameStart="xxxyyyzzz";
	final String classNameEnd="zzzyyyxxx";
	final String fileClassPath= "/diagrams/TestEmptyDiagram.bpmn20.xml";
	
	@Test
	public void givenXmlFileCheckChangeId() throws XPathExpressionException, ClassCastException, IOException, SAXException, ParserConfigurationException{
		ProcessExecutionListenerXmlProcessor processor=new ProcessExecutionListenerXmlProcessor(classNameStart, classNameEnd);
		Document document=getDocument(fileClassPath);
		Document changedDocument=processor.processDocument(document, "");
		String stringRepresentation=documentToString(changedDocument);
		System.out.println(stringRepresentation);
		Assert.assertTrue(StringUtils.contains(stringRepresentation, MessageFormat.format("class=\"{0}\"", classNameStart))); 
		Assert.assertTrue(StringUtils.contains(stringRepresentation, MessageFormat.format("class=\"{0}\"", classNameEnd))); 
	}
}
