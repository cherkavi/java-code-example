import java.io.*;
import java.net.URL;
import java.util.Enumeration;
import java.util.Properties;

import oracle.xml.parser.v2.DOMParser;
import oracle.xml.parser.v2.XMLDocument;
import oracle.xml.parser.v2.XMLDocumentFragment;
import oracle.xml.parser.v2.XSLStylesheet;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class XsltExample {
	private static void error(Object information){
		System.out.println("XsltExample ERROR:"+information);
	}
	private static void debug(Object information){
		System.out.println("XsltExample DEBUG:"+information);
	}
	
	public static void main(String[] args){
		try{
			String pathToXml="D:/eclipse_workspace/Xslt_Example/em.xml";
			String pathToXslt="D:/eclipse_workspace/Xslt_Example/em.xsl";
			/*{// ORACLE example 
				// создать файл XML
				DOMParser parser=new DOMParser();
				parser.parse(new FileInputStream(pathToXml));
				XMLDocument document=parser.getDocument();
				// создать файл XSLT
				XSLStylesheet stylesheet = new XSLStylesheet(new FileInputStream(new File(pathToXslt)), null);
				// преобразовать XML в XSLT
			    XMLDocumentFragment fragment =(XMLDocumentFragment) document.transformNode(stylesheet);
			    fragment.print(System.out);
			}*/
			{// Standart Example
				try {
				   StreamSource source = new StreamSource(new InputStreamReader(new FileInputStream(pathToXml),"WINDOWS-1251"));
				   StreamSource stylesource = new StreamSource(new InputStreamReader(new FileInputStream(pathToXslt),"WINDOWS-1251"));

				   //DOMSource source=new DOMSource(getXmlByPath(pathToXml));
				   //DOMSource stylesource=new DOMSource(getXmlByPath(pathToXslt));
				   
				   TransformerFactory factory = TransformerFactory.newInstance();
				   Transformer transformer = factory.newTransformer(stylesource);
				   transformer.setOutputProperty(OutputKeys.ENCODING,"WINDOWS-1251");

				   StreamResult result = new StreamResult(System.out);
				   Properties properties=transformer.getOutputProperties();
				   Enumeration enumeration=properties.elements();
				   while(enumeration.hasMoreElements()){
					   System.out.println("element:"+enumeration.nextElement());
				   }
				   
				   transformer.transform(source, result);				
				} catch (TransformerConfigurationException tce) {
					System.err.println("Exception: "+tce.getMessage());
				}
			}
		}catch(Exception ex){
			error(ex.getMessage());
		}
	}
	
	/** получить XML файл из указанного абсолютного пути */
	private static Document getXmlByPath(String path_to_xml) throws Exception{
        Document return_value=null;
		javax.xml.parsers.DocumentBuilderFactory document_builder_factory=javax.xml.parsers.DocumentBuilderFactory.newInstance();
        // установить непроверяемое порождение Parser-ов
        document_builder_factory.setValidating(false);
        try {
            // получение анализатора
            javax.xml.parsers.DocumentBuilder parser=document_builder_factory.newDocumentBuilder();
            return_value=parser.parse(new FileInputStream(path_to_xml));
        }catch(Exception ex){
        	error("XmlExchange.java ERROR");
        	throw ex;
        }
		return return_value;
	}
	
}
