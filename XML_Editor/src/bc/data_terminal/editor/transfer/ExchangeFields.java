package bc.data_terminal.editor.transfer;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.apache.log4j.*;

import bc.data_terminal.editor.database.*;
import bc.data_terminal.editor.utility.toHtmlConverter;


/** класс, который отвечает за информационный обмен графического клиента с сервером <br>
 * основная задача - создавание и редактирование файлов (XML представлений задач) по определенному пользователю 
 * */
public class ExchangeFields extends XmlExchange{
	private Logger field_logger=Logger.getLogger(this.getClass());
/*	{
		field_logger.setLevel(Level.DEBUG);
		if(!field_logger.getAllAppenders().hasMoreElements()){
			BasicConfigurator.configure();
		}
	}
*/	
	private static ArrayList<ExchangeFileHeadElement> field_rules=new ArrayList<ExchangeFileHeadElement>();
	static{
		field_rules.add(new ExchangeFileHeadElement("DELIMETER",
								       new ExchangeFileHeadAttribute("value",new String[]{",",".",";"}))
		                );
		field_rules.add(new ExchangeFileHeadElement("COMMENT",
			       					   new ExchangeFileHeadAttribute("exists",new String[]{"true","false"}))
						);
		field_rules.add(new ExchangeFileHeadElement("HEADER",
			       						new ExchangeFileHeadAttribute("exists",new String[]{"true","false"}))
						);
		field_rules.add(new ExchangeFileHeadElement("CHECKER",
									   new ExchangeFileHeadAttribute("full_log",new String[]{"true","false"}))
						);
		field_rules.add(new ExchangeFileHeadElement("FIELD",
				   					   new ExchangeFileHeadAttribute("field_name"),
				   					   new ExchangeFileHeadAttribute("can_empty",new String[]{"true","false"}),
									   new ExchangeFileHeadAttribute("class",new String[]{"java.lang.Integer","java.lang.String","java.lang.Float","java.util.Date"})
									  )
						);
		
		
	}
	/** отправить XML файл на сервер и получить ответ 
	 * место обмена HTML клиента с сервером 
	 * */
	public Data sendXmlToServer(Data data){
		field_logger.debug("get data Key:"+data.getKey());
		field_logger.debug("get data Value:"+data.getValue());
		// Функции клиента на стороне сервера 
		Data return_value=new Data();

		// произошло изменение клиента - загрузить задачи по данному клиенту 
		if(data.getKey().equals("user_change")){
			return_value.setKey(data.getKey());
			String html_kod=toHtmlConverter.convertMapToSelectBody(DBFunction.getTasks(data.getValue()));
			field_logger.debug(html_kod);
			return_value.setValue(html_kod);
		}
		// произошло изменение задачи ( выбран клиент, выбрана задача - загрузить файл XML) 
		if(data.getKey().equals("task_change")){
			// изменение клиента - послать файл XML
			return_value.setKey("xml_body");
			String html_kod=this.getHtmlFromUserFromTask(data.getValue(),
														 data.getValue2()
														 ).toString();
			field_logger.debug(html_kod);
			return_value.setValue(html_kod);
		};
		
		// получен запрос на сохранение данных на сервере 
		if(data.getKey().equals("xml_body")){
			return_value.setKey("alert");
			if(this.saveXmlToStore(data.getValue2(),data.getValue3(),data.getValue())==true){
				return_value.setValue("OK");			
			}else{
				return_value.setValue("Error");
			}
			
		}
		
		// получен запрос на создание нового элемента Fields
		if(data.getKey().equals("create_field")){
			field_logger.debug("Create_field: key:"+data.getKey());
			field_logger.debug("Create_field: value:"+data.getValue());
			return_value.setKey("create_field");
			ExchangeFileHeadElement field_element=this.getElementByName("FIELD");
			if(field_element!=null){
				return_value.setValue( field_element.getHtmlWithManager("field_"+data.getValue()).toString() );
			}
		}
		return return_value;
	}


	/**
	 * получить HTML содержимое для отправки клиенту на основании "имени пользователя" и "пароля"
	 */
	private StringBuffer getHtmlFromUserFromTask(String user_name, String task_name){
		StringBuffer return_value;
		// прочитать XML по данному пользователю
		Document doc=this.getXmlByPath("D:\\eclipse_workspace\\DataTerminal_Client\\XML_Files\\client_pattern_csv.xml ");
		// пропустить его через фильтр, который выведет последнюю версию файла XML на него отображенную
		// преобразовать XML в HTML
		return_value=this.getHtmlFromXml(doc);		
		return return_value;
	}
	
	/** получить HTML на основании XML */
	private StringBuffer getHtmlFromXml(Document doc){
		StringBuffer return_value=new StringBuffer();
		// алгоритм преобразования - if Node has not Child - <b>Node_name</b><br><Input type="text">
		// if child exists - is FIELDS
		if(doc.hasChildNodes()){
			NodeList list=(NodeList)doc.getChildNodes();
			Node node;
			ExchangeFileHeadElement current_element;
			for(int counter=0;counter<list.getLength();counter++){
				node=list.item(counter);
				if(node.getNodeName().equals("PATTERN")){
					NodeList pattern_list=(NodeList)node.getChildNodes();
					Node pattern_node;
					for(int pattern_counter=0;pattern_counter<pattern_list.getLength();pattern_counter++){
						pattern_node=pattern_list.item(pattern_counter);
						if(pattern_node.getNodeName().equals("FIELDS")){
							// element FIELDS 
							return_value.append(this.getHtmlFieldsFromNode(pattern_node));
						}else{
							// element not FIELDS
							current_element=this.getElementByName(pattern_node.getNodeName());
							if(current_element!=null){
								return_value.append(current_element.getHtml(pattern_node));
							}
						}
					}
				}
			}
		}else{
			field_logger.error("XML document ERROR ");
		}
		return return_value;
	}
	
	
	
	/** вернуть HTML текст для Node FIELD (и только FIELD)*/
	private StringBuffer getHtmlFieldsFromNode(Node node){
		StringBuffer return_value=new StringBuffer();
		NodeList list=node.getChildNodes();
		ExchangeFileHeadElement field_element;
		return_value.append("<TABLE id=\"fields\" border=1 >");
		for(int counter=0;counter<list.getLength();counter++){
			if(list.item(counter).getNodeName().equals("FIELD")){
				return_value.append("<TR>");
				return_value.append("<TD>");
				field_element=this.getElementByName("FIELD");
				if(field_element!=null){
					return_value.append(field_element.getHtmlWithManager("field_"+counter,list.item(counter)));
				}
				return_value.append("</TD>");
				return_value.append("</TR>");
			}
		}
		return_value.append("</TABLE>");
		return_value.append("<INPUT style=\"text-align: right;\" TYPE=\"button\" value=\"append\" id=\"button_append\" onClick=\"add_field_element()\" >");
		return return_value;
	}
	
	/** возвращает элемент по его имени, если оно существует, иначе null*/
	private ExchangeFileHeadElement getElementByName(String name){
		for(int counter=0;counter<ExchangeFields.field_rules.size();counter++){
			if(ExchangeFields.field_rules.get(counter).getName().equals(name)){
				return ExchangeFields.field_rules.get(counter);
			}
		}
		return null;
	}

	/** метод, который парсит полученный HTML-XML файл и сохраняет его на диске 
	 * @param user_name - имя пользователя 
	 * @param task_name - имя задачи 
	 * @param xml_text - XML содержимое из файла
	 * 
	 * */
	private boolean saveXmlToStore(String user_name, 
								   String task_name, 
								   String xml_text) {
		boolean return_value=false;
		try{
			javax.xml.parsers.DocumentBuilderFactory document_builder_factory=javax.xml.parsers.DocumentBuilderFactory.newInstance();
	        // установить непроверяемое порождение Parser-ов
	        document_builder_factory.setValidating(false);
	        try {
	            // получение анализатора
	            javax.xml.parsers.DocumentBuilder parser=document_builder_factory.newDocumentBuilder();
	            
	            //print_file(path_to_file);
	            Document document_html=parser.parse(new ByteArrayInputStream(xml_text.getBytes()));
	            Document document_for_save=this.convertHtmlToXml(document_html);
	            if(document_for_save!=null){
	            	// TODO место сохранения полученного XML документа на диск
	            	return_value=this.saveXmlToFile(document_for_save, "c:\\index_fields.xml");
	            }else{
	            	return_value=false;
	            }
	            
	        }catch(Exception ex){
	        	return_value=false;
	        	field_logger.error("XmlExchange.java ERROR");
	        }
		}catch(Exception ex){
			return_value=false;
			field_logger.error("saveXmlToStore Exception:"+ex.getMessage());
		}
		
		return return_value;
	}
	
	/** метод, который возвращает на основании текста XML-HTML, полученного от клиента в формат, доступный для сохранения */
	private Document convertHtmlToXml(Document source){
		Document destination_document=null;
		Element destination=null;
		try{
	        // create empty XML document
			javax.xml.parsers.DocumentBuilderFactory document_builder_factory=javax.xml.parsers.DocumentBuilderFactory.newInstance();
	        document_builder_factory.setValidating(false);
	        document_builder_factory.setIgnoringComments(true);
	        javax.xml.parsers.DocumentBuilder document_builder=document_builder_factory.newDocumentBuilder();
	        destination_document=document_builder.newDocument();
	        destination=destination_document.createElement("PATTERN");
	        // parse XML document
	        try{
		        if(source.hasChildNodes()){
		        	/** list of Root Node*/
		        	
		        	NodeList list=source.getChildNodes().item(0).getChildNodes();
		        	/** Node of XML Document*/
		        	Node node;
		        	/** value of Attribute */
		        	String attribute_value;
		        	Element destination_element;
		        	for(int counter=0;counter<list.getLength();counter++){
		        		node=list.item(counter);
		        		if(node.getNodeName().equals("TABLE")){
		        			attribute_value=this.getAttrFromNode(node, "id");
		        			if(attribute_value!=null){
		        				if(attribute_value.equals("fields")){
		        					field_logger.debug("FIELDS finded");
		        					Element destination_element_fields=destination_document.createElement("FIELDS");
		        					// Node is FIELDS
		        					NodeList field_list=((Element)node).getElementsByTagName("TABLE");
		        					if(field_list!=null){
		        						for(int field_counter=0;field_counter<field_list.getLength();field_counter++){
		        							field_logger.debug("FIELD:"+counter);
				        					destination_element=destination_document.createElement("FIELD");
				        					fillElementFromXml(destination_element, field_list.item(field_counter));
				        					destination_element_fields.appendChild(destination_element);
		        						}
		        					}else{
		        						field_logger.warn("fields not found");
		        					}
		        					destination.appendChild(destination_element_fields);
		        				}else{
		        					// Node is not FIELDS
		        					field_logger.debug("Element finded:"+attribute_value);
		        					destination_element=destination_document.createElement(attribute_value);
		        					fillElementFromXml(destination_element, node);
		        					destination.appendChild(destination_element);
		        				}
		        			}
		        		}else{
		        			// another markup
		        		}
		        	}
		        }else{
		        	throw new Exception("Root Node has no child!!!");
		        }
	        }catch(Exception ex){
	        	field_logger.error("XML parse Error:"+ex.getMessage());
	        }
	        
		}catch(Exception ex){
			field_logger.error("convertHtmlToXml:"+ex.getMessage());
			destination_document=null;
		}
		destination_document.appendChild(destination);
		return destination_document;
	}
	
	/** метод, который наполняет Node на основании Node из HTML(XML) 
	 * @param source - элемент, прочитанный из HTML(XML) кода
	 * @param destination - элемент, в который нужно добавить аттрибуты
	 * */
	private void fillElementFromXml(Element destination,Node source_node){
		try{
			Element source_element=(Element)source_node;
			// try getting SELECT element from Node source
			NodeList select_list=source_element.getElementsByTagName("SELECT");
			
			if((select_list!=null)&&(select_list.getLength()>0)){
				// SELECT
				Element select_element;
				String attribute_name;
				String selected_value;
				for(int select_counter=0;select_counter<select_list.getLength();select_counter++){
					select_element=(Element)select_list.item(select_counter);
					attribute_name=this.getAttrFromNode(select_element, "id");
					field_logger.debug("attribute_name:"+attribute_name);
					if(attribute_name!=null){
						selected_value=getOptionValueFromSelect(select_element);
						field_logger.debug("           selected_value:"+selected_value);
						if(selected_value!=null){
							destination.setAttribute(attribute_name, selected_value);
						}
					}else{
						field_logger.warn("fillElementFromXml not found: id");
					}
				}
			}
			// try getting INPUT element form Node source 
			NodeList input_list=source_element.getElementsByTagName("INPUT");
			if((input_list!=null)&&(input_list.getLength()>0)){
				// INPUT
				String attribute_name;
				String input_value;
				for(int input_counter=0;input_counter<input_list.getLength();input_counter++){
					attribute_name=this.getAttrFromNode(input_list.item(input_counter), "id");
					field_logger.debug("attribute_name:"+attribute_name);
					if(attribute_name!=null){
						input_value=getInputValueFromInput(input_list.item(input_counter));
						field_logger.debug("             input_value:"+input_value);
						if(input_value!=null){
							destination.setAttribute(attribute_name, input_value);
						}
					}else{
						field_logger.warn("fillElementFromXml not found: id");
					}
				}
			}
			
		}catch(Exception ex){
			field_logger.error("fillElementFromXml Error:"+ex.getMessage());
		}
	}
	
	/** метод, который получает из Node (SELECT) значение выделенного элемента OPTION 
	 * @param source - Node (SELECT)
	 * @return возвращает  
	 * */
	private String getOptionValueFromSelect(Node source){
		String return_value=null;
		try{
			NodeList option_list=((Element)source).getElementsByTagName("OPTION");
			for(int option_counter=0;option_counter<option_list.getLength();option_counter++){
				if(this.getAttrFromNode(option_list.item(option_counter),"selected")!=null){
					return_value=this.getAttrFromNode(option_list.item(option_counter),"value");
					break;
				}
			}
		}catch(Exception ex){
			field_logger.error("getOptionVAlueFromSelect exception:"+ex.getMessage());
		}
		return return_value;
	}
	
	/** метод, который получает из Node(INPUT) значение value 
	 * @param Node (Input)
	 * @return null если значение не найдено 
	 * */
	private String getInputValueFromInput(Node source){
		String return_value=null;
		try{
			return_value=this.getAttrFromNode(source, "value");
		}catch(Exception ex){
			field_logger.error("getInputValueFromInput exception:"+ex.getMessage());
		}
		return return_value;
	}
}
















