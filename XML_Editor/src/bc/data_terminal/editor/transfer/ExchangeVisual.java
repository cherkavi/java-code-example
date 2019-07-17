package bc.data_terminal.editor.transfer;

import java.util.StringTokenizer;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import bc.data_terminal.editor.database.DBFunction;



public class ExchangeVisual extends XmlExchange{

	/** отправить XML файл на сервер и получить ответ */
	public Data sendXmlToServer(Data data){
		field_logger.debug("get data Key:"+data.getKey());
		field_logger.debug("get data Value:"+data.getValue());

		Data return_value=new Data();
		if(data.getKey().equals("client_id")){
			// изменение клиента - послать файл XML
			return_value.setKey("xml_body");
			String html_kod=this.getHtmlFromUserName(data.getValue()).toString();
			field_logger.debug(html_kod);
			return_value.setValue(html_kod);
		};
		if(data.getKey().equals("xml_body")){
			return_value.setKey("alert");
			//return_value.setValue(this.parseHtmlToXML(data.getValue()));
			return_value.setValue(this.parseHtmlToDataBase(data.getValue(),data.getValue2()));
		}
		return return_value;
	}

	/** 
	 * сохранить в базе данных информацию (файл XML),полученный от клиента в виде HTML текста 
	 * @param user_id - имя пользователя ( номер терминала )
	 * @param data данные в виде HTML 
	 */
	private String parseHtmlToDataBase(String user_id, String data){
		String return_value="Error";
		Document doc=this.getXmlUser(user_id);
		if(doc!=null){
			StringTokenizer element=new StringTokenizer(data,"<");
			String current_value;
			String element_value;
			String element_task_id;
			while(element.hasMoreElements()){
				current_value=element.nextToken();
				if(current_value.startsWith("INPUT")){
					field_logger.debug("ELEMENT:"+current_value);
					// найти значение элемента в строке
					element_value=getAttributeFromString(current_value,"checked");
					// получить значение Task_id
					element_task_id=getAttributeFromString(current_value,"task_id");
					// место сохранения данных в базу
					field_logger.warn("user_id:"+user_id+"     task_id:"+element_task_id+"    value:"+element_value);
					if(DBFunction.saveVisualXmlElement(user_id, element_task_id, element_value)){
						// save OK
					}else{
						field_logger.error("parseHtmlToDataBase save to DataBase Error ");
					}
				}else{
					// another element's
					field_logger.debug("ANOTHER_ELEMENT");
				}
			}
			
			return_value="Ok";
		}else{
			field_logger.error("parseHtmlToDataBase: user XML is not loaded:"+user_id);
		}
		return return_value;
	}
	
	
	/** распарсить полученный код на предмет соответствия HTML и XML (взять шаблон и залить на его основе данные) 
	 * вернуть результат для отображения пользователю в качестве сообщения о положительном или отрицательном сохранении
	 * */
/*	private String parseHtmlToXML(String data){
		String return_value="OK";
		StringTokenizer element=new StringTokenizer(data,"<");

		// место для получения основного-шаблонного файла
		//String path_to_xml="D:\\eclipse_workspace\\DataTerminal_Client\\XML_Files\\terminal_client.xml";
		//Document doc=this.getXmlByPath(path_to_xml);
		
		// преобразовать HTML в XML 
		Document doc=this.getXmlPattern();
		NodeList node_list;
		Node node;
        
		String current_value;
		String element_name;
		String element_value;
		String element_task_id;
		while(element.hasMoreElements()){
			current_value=element.nextToken();
			if(current_value.startsWith("INPUT")){
				field_logger.debug("ELEMENT:"+current_value);
				// найти имя элемент в строке
				element_name=getAttributeFromString(current_value,"id");
				// найти значение элемента в строке
				element_value=getAttributeFromString(current_value,"checked");
				// получить значение Task_id
				element_task_id=getAttributeFromString(current_value,"task_id");
				field_logger.debug("ID:"+element_name+"    checked:"+element_value+"  task_id:"+element_task_id);
				try{
					// найти элемент в XML файле
					node_list=doc.getElementsByTagName(element_name);
					if(node_list.getLength()>0){
						// установить элемент visible в XML файле
						field_logger.debug("Element finded:"+element_value);
						node=node_list.item(0);
						if(!element_value.equals("")){
							if(element_value.equalsIgnoreCase("false")){
								field_logger.debug("set value visible false");
								node.getAttributes().getNamedItem("visible").setTextContent("false");
							}else{
								field_logger.debug("set value visible true");
								node.getAttributes().getNamedItem("visible").setTextContent("true");
							}
						}else{
							field_logger.debug("set value visible false");
							node.getAttributes().getNamedItem("visible").setTextContent("false");
						}
						// установить элемент task_id в файле
						((Element)node).setAttribute("task_id", element_task_id);
					}else{
						// element not found
					}
				}catch(Exception ex){
					field_logger.debug("Xpath find error:"+ex.getMessage());
				}
			}else{
				// another element's
				field_logger.debug("ANOTHER_ELEMENT");
			}
		}
		// сохранить файл
        try{
            javax.xml.transform.TransformerFactory transformer_factory = javax.xml.transform.TransformerFactory.newInstance();  
            javax.xml.transform.Transformer transformer = transformer_factory.newTransformer();  
            javax.xml.transform.dom.DOMSource dom_source = new javax.xml.transform.dom.DOMSource(doc); // Pass in your document object here  
            java.io.FileWriter out=new java.io.FileWriter("c:\\out_server.xml");
            //string_writer = new Packages.java.io.StringWriter();  
            javax.xml.transform.stream.StreamResult stream_result = new javax.xml.transform.stream.StreamResult(out);  
            transformer.transform(dom_source, stream_result);  
            //xml_string = string_writer.toString(); // At this point you could use the Servoy XMLReader plugin to read your XML String.  
            //application.output(xml_string);                  
            out.flush();
            out.close();
            System.out.println("XML saved");
        }catch(Exception ex){
            System.out.println("Export XML Error");
        }

		return return_value;
	}
*/	
	/** конвертировать XML файл в HTML отображение */
	private StringBuffer convertXmlToHTML(Document document){
		StringBuffer return_value=new StringBuffer();
		return_value.append(convertNodeListToHtml("",document));
		return return_value;
	}
	

	/** получить HTML код на основании имени пользователя */
	private StringBuffer getHtmlFromUserName(String user_name){
		// получить XML документ
		Document document=getXmlUser(user_name);
		if(document!=null){
			// преобразовать XML документ в HTML код
			return convertXmlToHTML(document);
		}else{
			field_logger.error("Error in create XML");
			return new StringBuffer();
		}
	}
	/** конвертировать NODE/NODELIST в HTML */
	private StringBuffer convertNodeListToHtml(String preambule, Node node){
		StringBuffer return_value=new StringBuffer();
		if(node.hasChildNodes()){
			NodeList node_list=node.getChildNodes();
			// preambule
			if(node.getNodeType()==Node.ELEMENT_NODE){
				return_value.append(convertNodeToHtml(preambule,node,true));
				return_value.append("<span id=\"body_"+node.getNodeName()+"\">");
			}
			// body
			for(int counter=0;counter<node_list.getLength();counter++){
				if(node_list.item(counter).getNodeType()==Node.ELEMENT_NODE){
					field_logger.debug("NODE ELEMENT_NODE");
					return_value.append(convertNodeListToHtml(preambule+field_delimeter,node_list.item(counter)));
				}else{
					field_logger.debug("NODE other type");
				}
			}
			// postambule
			if(node.getNodeType()==Node.ELEMENT_NODE){
				return_value.append("</span>");
			}
			
		}else{
			return_value.append(convertNodeToHtml(preambule,node,false)); 
		}
		return return_value;
	}

	/** конвертировать NODE в HTML */
	private StringBuffer convertNodeToHtml(String preambule, Node node,boolean isRoot){
		//field_logger.debug("Preambule:["+preambule+"]");
		StringBuffer return_value=new StringBuffer();
		String node_name=node.getNodeName();
		String task_id=this.getAttrFromNode(node, "task_id");
		
		if(isRoot){
			// добавить обработчик
			if (((Attr)(node.getAttributes().getNamedItem("visible"))).getValue().equals("true")){
				//field_logger.debug(preambule+"<input onclick=\"check_root(this)\" type=\"checkbox\" name=\""+node_name+"\" id=\""+node_name+"\" checked=\"checked\" value=\""+node_name+"\" >"+node_name+"<br>");
				return_value.append(preambule+"<input onclick=\"check_root(this)\" type=\"checkbox\" name=\""+node_name+"\" id=\""+node_name+"\" checked=\"checked\" value=\""+node_name+"\" task_id=\""+task_id+"\" >"+node_name+"<br>");
			}else{
				//field_logger.debug(preambule+"<input onclick=\"check_root(this)\" type=\"checkbox\" name=\""+node_name+"\" id=\""+node_name+"\" value=\""+node_name+"\" >"+node_name+"<br>");
				return_value.append(preambule+"<input onclick=\"check_root(this)\" type=\"checkbox\" name=\""+node_name+"\" id=\""+node_name+"\" value=\""+node_name+"\" task_id=\""+task_id+"\" >"+node_name+"<br>");
			}
		}else{
			if (((Attr)(node.getAttributes().getNamedItem("visible"))).getValue().equals("true")){
				//field_logger.debug(preambule+"<input type=\"checkbox\" name=\""+node_name+"\" id=\""+node_name+"\" checked=\"checked\" value=\""+node_name+"\" >"+node_name+"<br>");
				return_value.append(preambule+"<input onclick=\"check_brother(this)\" type=\"checkbox\" name=\""+node_name+"\" id=\""+node_name+"\" checked=\"checked\" value=\""+node_name+"\" task_id=\""+task_id+"\" >"+node_name+"<br>");
			}else{
				//field_logger.debug(preambule+"<input type=\"checkbox\" name=\""+node_name+"\" id=\""+node_name+"\" value=\""+node_name+"\" >"+node_name+"<br>");
				return_value.append(preambule+"<input onclick=\"check_brother(this)\" type=\"checkbox\" name=\""+node_name+"\" id=\""+node_name+"\" value=\""+node_name+"\" task_id=\""+task_id+"\" >"+node_name+"<br>");
			}
		}
		return return_value;
	}

	/** получить XML документ на основании имени пользователя  из хранилища 
	 * @param user_name уникальное имя пользователя, по которому нужно загрузить XML файл из хранилища 
	 * */
	private Document getXmlUser(String user_name){
		// TODO место для получения файла клиента
		// необходимо читать базовый-главный XML, затем заполнять все элементы базового элементами из прочитанного по данному пользователю
		// (необходимо из-за хранения в хранилище-базе_данных "старых" версий XML )
		//String path_to_xml="D:\\eclipse_workspace\\DataTerminal_Client\\XML_Files\\terminal_client.xml";
		//Document user=getXmlByPath(path_to_xml);
		//Document pattern=getXmlPattern();
		// преобразовать файл, полученный из хранилища с файлом-оригиналом ( последняя версия ) и выдать пользователю последнюю версию с данными из хранилища
		//refreshUserXmlByPatternXml(pattern, user);
		return DBFunction.getVisualXmlByUser(user_name);
	}

	/** получить шаблонный документ-последнюю версию XML, в которую уже нужно заливать данные из файлов, прочитанных из хранилища */
/*	protected Document getXmlPattern(){
		String path_to_xml="D:\\eclipse_workspace\\DataTerminal_Client\\XML_Files\\terminal_visual_main.xml";
		return getXmlByPath(path_to_xml);
	}
*/
	/** установить все элементы XML файла, полученные из хранилища в последнюю версию шаблона 
	 * @param pattern_xml шаблон ( последняя версия ) которая является законодателем для последующих изменений
	 * @param user_xml прочитанный документ из хранилища, с сохраненными настройками пользователей
	 * @return спроэцированный на шаблонный XML пользовательских данных ( из хранилища ) 
	 * */
/*	private Document refreshUserXmlByPatternXml(Document pattern_xml, Document user_xml){
		// перебрать все элементы из "шаблона"
		getFromUserPutToPattern(pattern_xml,user_xml);
		return pattern_xml;
	}
*/	
	/** взять Node(полученный из Pattern) и проверить его состояние в User */
/*	private void getFromUserPutToPattern(Node pattern, Document user){
		if(pattern.hasChildNodes()){
			// is NodeList
			field_logger.debug("is NodeList:"+pattern.getNodeName());
			setPatternNodeFromUserXML(pattern, user.getElementsByTagName(pattern.getNodeName()).item(0),"visible");
			NodeList node_list=pattern.getChildNodes();
			for(int counter=0;counter<node_list.getLength();counter++){
				getFromUserPutToPattern(node_list.item(counter),user);
			}
		}else{
			// is Node
			// получить элемент из пользовательского XML
			String node_name=pattern.getNodeName();
			field_logger.debug("is Node:"+node_name);
			NodeList node_list=(NodeList)user.getElementsByTagName(node_name);
			Node node;
			for(int counter=0;counter<node_list.getLength();counter++){
				node=node_list.item(counter);
				setPatternNodeFromUserXML(pattern, node,"visible");
			}
		}
	}

*/	
}
