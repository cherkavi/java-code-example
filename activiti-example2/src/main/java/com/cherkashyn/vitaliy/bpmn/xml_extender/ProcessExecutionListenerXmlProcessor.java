package com.cherkashyn.vitaliy.bpmn.xml_extender;

import javax.xml.xpath.XPathExpressionException;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.cherkashyn.vitaliy.bpmn.utility.XmlUtility;

public class ProcessExecutionListenerXmlProcessor implements XmlProcessor{
	
	private static final String xpath="/definitions/process";
	private final static String rootElementTag="extensionElements";
	private final static String elementTag="activiti:executionListener";
	private final static String attributeEventName="event";
	private final static String attributeClassName="class";
	
	
	private String startClassName;
	private String endClassName;
	
	public ProcessExecutionListenerXmlProcessor(String startClassName, String endClassName){
		this.startClassName=StringUtils.trimToEmpty(startClassName);
		this.endClassName=StringUtils.trimToEmpty(endClassName);
	}
	
	@Override
	public Document processDocument(Document document, String fileName) throws XPathExpressionException, ClassCastException {
//	    <extensionElements>
//	      <activiti:executionListener event="start" class="com.test.activiti.listeners.ProcessStartListener"></activiti:executionListener>
//	      <activiti:executionListener event="end" class="com.test.activiti.listeners.ProcessEndListener"></activiti:executionListener>
//	    </extensionElements>
		XmlUtility xmlUtility=new XmlUtility();

		removeElementIfExists(document, xmlUtility);
		addElement(document, xmlUtility);
		
		Element element=(Element)xmlUtility.getNodeFromDocument(document, "/definitions/process");
		element.setAttribute("id", fileName);
		return document;
	}

	private void addElement(Document document, XmlUtility xmlUtility) throws XPathExpressionException, ClassCastException {
		Element rootElement=(Element) xmlUtility.getNodeFromDocument(document, xpath);
		Element extension=document.createElement(rootElementTag);
		rootElement.appendChild(extension);
		
		Element startElement=document.createElement(elementTag);
		startElement.setAttribute(attributeEventName, "start");
		startElement.setAttribute(attributeClassName, this.startClassName);
		startElement.setTextContent("");
		extension.appendChild(startElement);
		
		Element endElement=document.createElement(elementTag);
		endElement.setAttribute(attributeEventName, "end");
		endElement.setAttribute(attributeClassName, this.endClassName);
		endElement.setTextContent("");
		extension.appendChild(endElement);
		
	}

	private void removeElementIfExists(Document document, XmlUtility xmlUtility) throws XPathExpressionException, ClassCastException {
		Node extensionElements=xmlUtility.getNodeFromDocument(document, xpath+"/"+rootElementTag);
		if(extensionElements!=null){
			Node rootNode=xmlUtility.getNodeFromDocument(document, xpath);
			rootNode.removeChild(extensionElements);
		}
	}

}
