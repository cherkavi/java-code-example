package com.test.xml_generator;

import com.test.xml_generator.values_generator.ISequenceValues;

public abstract class AXmlGenerator implements IXmlGenerator{
	
	private String xmlRootElement;
	protected final ISequenceValues iterator;
	
	public AXmlGenerator(String xmlRootElement, ISequenceValues iterator){
		this.xmlRootElement=xmlRootElement;
		this.iterator=iterator;
	}
	
	protected StringBuilder getHeader(){
		StringBuilder returnValue=new StringBuilder();
		returnValue
			.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>	<")
			.append(xmlRootElement)
			.append(">\n");
		return returnValue;
	}

	/** get next Xml File */
	public String getNextXml(){
		StringBuilder body=new StringBuilder();
		body.append(getHeader());
		body.append(getBody());
		body.append(getFooter());
		return body.toString();
	}
	
	protected abstract StringBuilder getBody();
	
	protected StringBuilder getFooter(){
		StringBuilder returnValue=new StringBuilder();
		returnValue.append("</").append(this.xmlRootElement).append(">");
		return returnValue;
	}

	/**
	 * return open tag for XML Element 
	 * @param tagName - name of element 
	 * @param listOfAttributes - {@nullable} attributes 
	 * @return
	 */
	public static StringBuilder getElementOpenTagString(String tagName, IXmlGenerator.XmlAttribute  ... listOfAttributes){
		StringBuilder returnValue=new StringBuilder();
		returnValue
		.append("<")
		.append(tagName)
		.append(IXmlGenerator.XmlAttribute.arrayAsString(listOfAttributes))
		.append(">");
		
		return returnValue;
	}
	
	/**
	 * return close tag for XML Element 
	 * @param tagName - name of element 
	 * @return
	 */
	public static StringBuilder getElementCloseTagString(String tagName){
		StringBuilder returnValue=new StringBuilder();
		returnValue
		.append("</")
		.append(tagName)
		.append(">")
		.append("\n");
		return returnValue;
	}
	
	/**
	 * get String representation of XML element 
	 * @param tagName - name of XML.Tag
	 * @param value - value of XML leaf 
	 * @param listOfAttributes - attributes {@link XmlAttribute} of XML element 
	 * @return
	 */
	public static StringBuilder getElementAsString(String tagName,
											   String value,
											   IXmlGenerator.XmlAttribute  ... listOfAttributes){
		StringBuilder returnValue=new StringBuilder();
		returnValue
		.append(getElementOpenTagString(tagName, listOfAttributes))
		.append(value)
		.append(getElementCloseTagString(tagName));
		return returnValue;
	}
	
}
