package tools.transformer.utility;

import java.io.File;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlParser {
	
	/** получить XML файл из указанного абсолютного пути */
	public static Document getXmlByPath(String path_to_xml) throws Exception{
        Document return_value=null;
		javax.xml.parsers.DocumentBuilderFactory document_builder_factory=javax.xml.parsers.DocumentBuilderFactory.newInstance();
        // установить непроверяемое порождение Parser-ов
        document_builder_factory.setValidating(false);
        try {
            // получение анализатора
            javax.xml.parsers.DocumentBuilder parser=document_builder_factory.newDocumentBuilder();
            return_value=parser.parse(new File(path_to_xml));
        }catch(Exception ex){
        	throw ex;
        }
		return return_value;
	}
	
	/** получить подэлемент из корневого элемента на основании XPath пути
	 * @param parentElement - родительский элемент 
	 * @param path - путь XPath
	 * @return - найденный Element или null
	 */
	public static Element getSubElement(Node parentElement, String path){
		try{
			XPath xpath=XPathFactory.newInstance().newXPath();
			Object returnValue=xpath.evaluate(path, parentElement,XPathConstants.NODE);
			if(returnValue instanceof Element){
				return (Element)returnValue;
			}else{
				return null;
			}
		}catch(Exception ex){
			return null;
		}
	}	

	/** получить подэлемент из корневого элемента на основании XPath пути
	 * @param parentElement - родительский элемент 
	 * @param path - путь XPath
	 * @return - найденный Element или null
	 */
	public static NodeList getSubElements(Node parentElement, String path){
		try{
			XPath xpath=XPathFactory.newInstance().newXPath();
			return (NodeList)xpath.evaluate(path, parentElement,XPathConstants.NODESET);
		}catch(Exception ex){
			return null;
		}
	}	

}
