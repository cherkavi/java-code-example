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


public class ProcessExecutionListenerXmlProcessorTest extends CommonXmlProcessorTest{
	final String ID="xxxyyyzzz";
	final String fileClassPath= "/diagrams/TestEmptyDiagram.bpmn20.xml";
	
	@Test
	public void givenXmlFileCheckChangeId() throws XPathExpressionException, ClassCastException, IOException, SAXException, ParserConfigurationException{
		ProcessIdAsFileNameXmlProcessor processor=new ProcessIdAsFileNameXmlProcessor();
		Document document=getDocument(fileClassPath);
		Document changedDocument=processor.processDocument(document, ID);
		String stringRepresentation=documentToString(changedDocument);
		Assert.assertTrue(StringUtils.contains(stringRepresentation, MessageFormat.format("id=\"{0}\"", ID))); 
	}

}
