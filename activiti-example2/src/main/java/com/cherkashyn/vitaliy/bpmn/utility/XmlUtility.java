package com.cherkashyn.vitaliy.bpmn.utility;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * this is XML utility class for simple operations
 * <ul>
 * 	<li> String convert to XML </li>
 * 	<li> XML convert to String </li>
 * 	<li> set value to XML </li>
 * </ul>
 * !!! NO THREAD SAFE !!! 
 */
public class XmlUtility {
	public XmlUtility(){
	}
	
	/**
	 * XML document builder or XML parser
	 * @uml.property  name="parser"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private javax.xml.parsers.DocumentBuilder parser;
	{
		// get factory
		javax.xml.parsers.DocumentBuilderFactory documentBuilderFactory=javax.xml.parsers.DocumentBuilderFactory.newInstance();
		// schema checker switch OFF 
		documentBuilderFactory.setValidating(false);
	    // get Parser 
	    try {
			parser=documentBuilderFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @uml.property  name="fieldXPath"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private XPath fieldXPath=XPathFactory.newInstance().newXPath();

	/**
	 * get string from XML 
	 * @param document
	 * @return
	 */
	public String getStringFromXml(Document document){
		Writer out=null;
		try{
			javax.xml.transform.TransformerFactory transformer_factory = javax.xml.transform.TransformerFactory.newInstance();  
			javax.xml.transform.Transformer transformer = transformer_factory.newTransformer();
			// transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "testing.dtd");
	        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			javax.xml.transform.dom.DOMSource dom_source = new javax.xml.transform.dom.DOMSource(document); // Pass in your document object here  
			out=new StringWriter();
			//string_writer = new Packages.java.io.StringWriter();  
			javax.xml.transform.stream.StreamResult stream_result = new javax.xml.transform.stream.StreamResult(out);  
			transformer.transform(dom_source, stream_result);  
		}catch(Exception ex){
			System.err.println("getStringFromXmlDocument:"+ex.getMessage());
		}
		return (out==null)?"":out.toString();
	}
	
	/**
	 * get document from String
	 * @param xmlString
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	public Document getDocumentFromString(String xmlString) throws UnsupportedEncodingException, SAXException, IOException, ParserConfigurationException{
		parser.reset();
        return parser.parse(new ByteArrayInputStream(xmlString.getBytes("UTF-8")));
	}
	
	/**
	 * set value to document 
	 * @param document
	 * @param xpath
	 * @param value
	 * @throws XPathExpressionException
	 * @throws ClassCastException
	 */
	public void setValueToDocument(Document document, String xpath, String value) throws XPathExpressionException, ClassCastException{
		Node node=this.getNodeFromDocument(document, xpath);
		if(node!=null){
			node.setTextContent(value);
		}
	}
	

	/**
	 * get Node by XPath 
	 * @param document
	 * @param xpath
	 * @return
	 * @throws XPathExpressionException
	 * @throws ClassCastException
	 */
	public Node getNodeFromDocument(Document document, String xpath) throws XPathExpressionException, ClassCastException{
		fieldXPath.reset();
		return (Node)fieldXPath.evaluate(xpath, document, XPathConstants.NODE);
	}

	
	/**
	 * get Node by XPath 
	 * @param document
	 * @param xpath
	 * @return
	 * @throws XPathExpressionException
	 * @throws ClassCastException
	 */
	public NodeList getNodeListFromDocument(Document document, String xpath) throws XPathExpressionException, ClassCastException{
		fieldXPath.reset();
		return (NodeList)fieldXPath.evaluate(xpath, document, XPathConstants.NODESET);
	}
	
}
