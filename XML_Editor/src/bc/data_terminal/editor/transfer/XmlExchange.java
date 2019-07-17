package bc.data_terminal.editor.transfer;

import java.io.File;

import org.apache.log4j.Logger;
import org.w3c.dom.*;

/** класс, который вызываетс€ с помощью DWR (JavaScript) и получает ответы */
public class XmlExchange {
	static Logger field_logger;
	/** эмул€ци€ пробелов(пустых симоволов) на странице HTML */
	static String field_delimeter="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
	static {
		field_logger=Logger.getLogger("XmlExchange");
	}
	
	/** получить аттрибут из строки текста, аттрибутом считать заключенные attribute_name="attribute_value" */
	protected String getAttributeFromString(String source, String attribute){
		String return_value="";
		int index=source.indexOf(attribute);
		if(index>=0){
			int index_end=source.indexOf("\"",index+attribute.length()+2);
			return_value=source.substring(index+2+attribute.length(),index_end);
		}
		return return_value;
	}

	/** получить первый аттрибут из NODE либо же вернуть null
	 * @return empty string, is attribute is not exists
	 * */
	protected String getFirstAttributeValue(Node node){
		if(node.hasChildNodes()){
			return ((Attr)node.getAttributes().item(0)).getValue(); 
		}else{
			return "";
		}
	}

	/** получить первый аттрибут из NODE либо же вернуть null
	 * @return empty string, is attribute is not exists
	 * */
	protected String getFirstAttributeKey(Node node){
		if(node.hasChildNodes()){
			return ((Attr)node.getAttributes().item(0)).getName(); 
		}else{
			return "";
		}
	}
	
	/** получить XML файл из указанного абсолютного пути */
	protected Document getXmlByPath(String path_to_xml){
        Document return_value=null;
		javax.xml.parsers.DocumentBuilderFactory document_builder_factory=javax.xml.parsers.DocumentBuilderFactory.newInstance();
        // установить непровер€емое порождение Parser-ов
        document_builder_factory.setValidating(false);
        try {
            // получение анализатора
            javax.xml.parsers.DocumentBuilder parser=document_builder_factory.newDocumentBuilder();
            //print_file(path_to_file);
            return_value=parser.parse(new File(path_to_xml));
        }catch(Exception ex){
        	field_logger.error("XmlExchange.java ERROR");
        }
		return return_value;
		
	}

	/** 
	 * —охранить объект Document во внешнее хранилище 
	 * @param doc документ XML
	 * @param path_to_file путь к файлу, в котором нужно сохранить данные
	 * */
	protected boolean saveXmlToFile(Document doc, String path_to_file){
		boolean return_value=false;
        try{
            javax.xml.transform.TransformerFactory transformer_factory = javax.xml.transform.TransformerFactory.newInstance();  
            javax.xml.transform.Transformer transformer = transformer_factory.newTransformer();  
            javax.xml.transform.dom.DOMSource dom_source = new javax.xml.transform.dom.DOMSource(doc); // Pass in your document object here  
            java.io.FileWriter out=new java.io.FileWriter(path_to_file);
            //string_writer = new Packages.java.io.StringWriter();  
            javax.xml.transform.stream.StreamResult stream_result = new javax.xml.transform.stream.StreamResult(out);  
            transformer.transform(dom_source, stream_result);  
            //xml_string = string_writer.toString(); // At this point you could use the Servoy XMLReader plugin to read your XML String.  
            //application.output(xml_string);                  
            out.flush();
            out.close();
            return_value=true;
        }catch(Exception ex){
        	return_value=false;
        	field_logger.error("saveXmlToFile Exception:"+ex.getMessage());
        }
		return return_value;
	}

	
	/** установить в шаблонный Node значение из пользовательского XML 
	 * @param pattern - Node from pattern
	 * @param user_node - Node from user_node
	 * @param attribute_name - им€ аттрибута
	 * */
	protected void setPatternNodeFromUserXML(Node pattern,
										   Node user_node,
										   String attribute_name){
		String visible_attribute;
		if(user_node!=null){
			// получить аттрибут из пользовательского XML
			try{
				//field_logger.debug("NodeName:"+user_node.getNodeName());
				//field_logger.debug("NodeValue:"+user_node.getNodeValue());
				//field_logger.debug("Attribute Length:"+user_node.getAttributes().getLength());
				visible_attribute=((Attr)(user_node.getAttributes().getNamedItem(attribute_name))).getValue();
				pattern.getAttributes().getNamedItem(attribute_name).setTextContent(visible_attribute);
/*				field_logger.debug("Visble attribute:"+visible_attribute);
				if (visible_attribute.equals(attribute_value)){
					// установить аттрибут в шаблоне
					
				}else{
					// установить аттрибут в шаблоне по умолчанию
					if(attribute_default!=null){
						pattern.getAttributes().getNamedItem(attribute_name).setTextContent(attribute_default);
					}
				}*/
			}catch(NullPointerException ex){
				// если аттрибут не найден в каком-либо из файлов
				field_logger.debug(attribute_name+" attribute: is not found ");
			};
		}
	}
	
	/** получить значение аттрибута по его имени из Node
	 * @param node элемент, из которого необходимо получить аттрибут
	 * @param attribute_name им€ аттрибута, которое нужно получить
	 * @return null если произошла кака€-либо ошибка, либо же аттрибут не существует 
	 * */
	protected String getAttrFromNode(Node node, String attribute_name){
		String return_value=null;
		try{
			return_value=((Attr)node.getAttributes().getNamedItem(attribute_name)).getValue();
		}catch(Exception ex){
			return_value=null;
		}
		return return_value;
	}

}

















