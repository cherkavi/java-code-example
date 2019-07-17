import java.io.File;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;


public class XPathTest {

	private static void debug(Object object){
		System.out.print(" DEBUG ");
		System.out.println(object);
	}
	
	private static void error(Object object){
		System.out.print(" ERROR ");
		System.out.println(object);
	}
	
	public static void main(String[] args){
		new XPathTest();
	}
	
	public XPathTest(){
		try{
			Document document=getXmlByPath("c:\\temp.xml");
			setParameterForGet(document);
		}catch(Exception ex){
			
		}
	}
	
	
	private void setParameterForGet(Document document){
		try{
			XPathFactory xpathFactory=XPathFactory.newInstance();
			XPath xpath=xpathFactory.newXPath();
			XPathExpression expression=xpath.compile("//response/results/*");
			NodeList nodeSet=(NodeList)expression.evaluate(document, XPathConstants.NODESET);
			debug("//response/results/ length:"+nodeSet.getLength());
			if(nodeSet.getLength()>0){
				
/*				this.parametersForGet.clear();
				for(int counter=0;counter<nodeSet.getLength();counter++){
					this.parametersForGet.add(new Parameter(nodeSet.item(counter)));
				}
*/				
			}
		}catch(Exception ex){
			error("setParameterForGet: set parameter's Exception:"+ex.getMessage());
		}
	}
	
	/** получить XML файл из указанного абсолютного пути */
	private Document getXmlByPath(String path_to_xml) throws Exception{
        Document return_value=null;
		javax.xml.parsers.DocumentBuilderFactory document_builder_factory=javax.xml.parsers.DocumentBuilderFactory.newInstance();
        // установить непроверяемое порождение Parser-ов
        document_builder_factory.setValidating(false);
        try {
            // получение анализатора
            javax.xml.parsers.DocumentBuilder parser=document_builder_factory.newDocumentBuilder();
            return_value=parser.parse(new File(path_to_xml));
        }catch(Exception ex){
        	error("XmlExchange.java ERROR");
        	throw ex;
        }
		return return_value;
	}

}
