import java.io.StringReader;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;


public class Main {
	public static void main(String[] args){
		XMLReader xmlReader=getSaxReader();
		try{
			// задать парсер 
			xmlReader.setContentHandler(new DefaultHandler(){
				private boolean flagRead=false;
				@Override
				public void startElement(String uri, 
										 String localName,
										 String qName, 
										 Attributes attributes)throws SAXException {
					if(qName.equalsIgnoreCase("type")){
						flagRead=true;
					}
				}
				@Override
				public void endElement(String uri, 
									   String localName,
									   String qName) throws SAXException {
										// TODO Auto-generated method stub
					if(qName.equalsIgnoreCase("type")){
						flagRead=false;
					}
				}
				@Override
				public void characters(char[] ch, int start, int length)
						throws SAXException {
					// TODO Auto-generated method stub
					if(flagRead==true){
						System.out.println(new String(ch,start,length));
					}
				}
			});
			
			// распарсить документ
			xmlReader.parse(new InputSource(new StringReader(getXmlString())));
			System.out.println("OK");
		}catch(Exception ex){
			
		}
	}
	
	private static XMLReader getSaxReader(){
		XMLReader xmlReader = null;
	    try {
	      // получить фабрику SAX парсеров 
	      SAXParserFactory spfactory = SAXParserFactory.newInstance();
	      // не проверять DTD
	      spfactory.setValidating(false);
	      //получить Парсер
	      SAXParser saxParser = spfactory.newSAXParser();
	      // получить Reader 
	      xmlReader = saxParser.getXMLReader();
	      return xmlReader;
	    }catch (Exception e) {
	    	return null;
	    }		
	}
	
	/** получить DOM document на основании строки текста */ 
	private static Document getDocumentFromString(String xmlString){
		try{
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
	        Transformer transformer = transformerFactory.newTransformer(); 
	        DOMResult result=new DOMResult();
	        transformer.transform(new StreamSource(new StringReader(xmlString)), result);
	        return (Document)result.getNode();
		}catch(Exception ex){
			System.err.println("Order#getDocumentFromString Exception:"+ex.getMessage());
			return null;
		}
	}

	
	private static String getXmlString(){
		StringBuffer buffer=new StringBuffer();
		buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>");
		buffer.append("	<function>	");
		buffer.append("	<name>NewClient</name>"	);
		buffer.append("<security>");
		buffer.append("<request_key>server key</request_key>");
		buffer.append("<response_key>resource key</response_key>");
		buffer.append("</security>");
		buffer.append("<parameters>");
		buffer.append("<parameter>");
		buffer.append("<name>parameter name 1</name>");
		buffer.append("<type>integer</type>");
		buffer.append("<value>123</value>");
		buffer.append("</parameter>");
		buffer.append("<parameter>");
		buffer.append("<name>parameter name 2</name>");
		buffer.append("<type>string</type>");
		buffer.append("<value>value for save parameter 2</value>");
		buffer.append("</parameter>");
		buffer.append("<parameter>");
		buffer.append("			<name>parameter name 3</name>");
		buffer.append("				<type>float</type>");
		buffer.append("				<value>123.234</value>");
		buffer.append("			</parameter>");
		buffer.append("		</parameters>");
		buffer.append("	</function>	");
		return buffer.toString();
	}
}
