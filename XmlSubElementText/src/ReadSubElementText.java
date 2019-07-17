import java.io.File;

import org.w3c.dom.Document;
import org.w3c.dom.Element;


/**
 * ÷ель данного проекта - предоставить пример плохой реализации
 * данных в XML формате

 <root>
	this is text of root element
	<sub_element>
		this is text of sub element
	</sub_element>
</root>

 ¬ывод:
 прочесть текст из элемента root отдельно от текста sub_element
 не представл€етс€ возможным ( нет четкой грани между этой информацией
 */
public class ReadSubElementText {
	public static void main(String[] args) throws Exception{
		System.out.println("begin");
		Document file=getXmlByPath("temp.xml");
		Element element=(Element)file.getFirstChild();
		System.out.println("NodeName:"+element.getNodeName()
		+"\n  NodeType:"+element.getNodeType()
		+"\n  NodeValue:"+element.getNodeValue()
		+"\n  TagName:"+element.getTagName()
		+"\n  TextContent:"+element.getTextContent()
		+"\n  BaseUri:"+element.getBaseURI()
		);
		System.out.println("-end-");
	}
	
	/** получить XML файл из указанного абсолютного пути */
	private static Document getXmlByPath(String path_to_xml) throws Exception{
        Document return_value=null;
		javax.xml.parsers.DocumentBuilderFactory document_builder_factory=javax.xml.parsers.DocumentBuilderFactory.newInstance();
        // установить непровер€емое порождение Parser-ов
        document_builder_factory.setValidating(false);
        try {
            // получение анализатора
            javax.xml.parsers.DocumentBuilder parser=document_builder_factory.newDocumentBuilder();
            return_value=parser.parse(new File(path_to_xml));
        }catch(Exception ex){
        	System.err.println("XmlExchange.java ERROR:"+ex.getMessage());
        	throw ex;
        }
		return return_value;
	}
}
