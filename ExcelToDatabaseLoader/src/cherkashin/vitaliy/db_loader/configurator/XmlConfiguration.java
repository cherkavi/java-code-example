package cherkashin.vitaliy.db_loader.configurator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cherkashin.vitaliy.db_loader.configurator.configuration.Configuration;
import cherkashin.vitaliy.db_loader.configurator.configuration.elements.Connector;
import cherkashin.vitaliy.db_loader.configurator.configuration.elements.File;
import cherkashin.vitaliy.db_loader.configurator.configuration.elements.column.Column;
import cherkashin.vitaliy.db_loader.configurator.configuration.elements.column.IColumnListHolder;
import cherkashin.vitaliy.db_loader.configurator.configuration.elements.dictionary.Dictionary;
import cherkashin.vitaliy.db_loader.configurator.configuration.elements.format.Format;
import cherkashin.vitaliy.db_loader.configurator.configuration.elements.format.FormatFactory;
import cherkashin.vitaliy.db_loader.configurator.configuration.elements.sheet.ALoaderSheet;
import cherkashin.vitaliy.db_loader.configurator.configuration.elements.sheet.LoaderSheetBuilder;
import cherkashin.vitaliy.db_loader.exception.EDbLoaderException;

/** object for read configuration from XML file  */
@SuppressWarnings("unchecked")
public class XmlConfiguration extends ConfigurationFactoryMethod{
	private Logger logger=Logger.getLogger(this.getClass().getName());
	/** XPath factory */
	private XPath xpath = XPathFactory.newInstance().newXPath();
	
	@Override
	public Configuration getConfiguration(String pathToFile) throws EDbLoaderException {
		logger.debug("check file for exists");
		if(fileNotExists(pathToFile))throw new EDbLoaderException("Configuration file does not founded: "+pathToFile);
		logger.debug("open file");
		Document document=getXmlByPath(pathToFile);
		logger.debug("read elements");
		Configuration configuration=new Configuration();
		logger.debug("read element 'connector' ");
		configuration.setConnector(new Connector(this.getElementAsString(document, "/db_loader/connector/url", "Get Element from XML File Exception: /db_loader/connector/url"),
												 this.getElementAsString(document, "/db_loader/connector/login", "Get Element from XML File Exception: /db_loader/connector/login"),
												 this.getElementAsString(document, "/db_loader/connector/password", "Get Element from XML File Exception: /db_loader/connector/password")
												));
		logger.debug("read configuration data:");
		configuration.setDictionaries(convertNodeListToDictionary(getNodeSet(document, 
																			 "//db_loader/dictionaries/dictionary",
																			 "Error read element /db_loader/dictionaries/dictionary")));
		configuration.setFormats(convertNodeListToFormats(getNodeSet(document, 
																	 "//db_loader/formats/format",
																	 "Error read element /db_loader/formats/format")));
		configuration.setFiles(convertNodeListToFiles(getNodeSet(document, 
																 "//db_loader/files/file",
																 "Error read element /db_loader/files/file"), 
													  configuration.getFormats(),
													  configuration.getDictionaries()));
		return configuration;
	}
	
	private List<Dictionary> convertNodeListToDictionary(NodeList nodeSet) {
		ArrayList<Dictionary> returnValue=new ArrayList<Dictionary>();
		for(int index=0;index<nodeSet.getLength();index++){
			Dictionary dictionary=getDictionaryFromNode(nodeSet.item(index));
			if(dictionary!=null){
				logger.debug("Dictionary: "+dictionary);
				returnValue.add(dictionary);
			}
		}
		return returnValue;
	}
	
	private Dictionary getDictionaryFromNode(Node node){
		if(node instanceof Element){
			Element element=(Element)node;
			if(element.hasChildNodes()){
				String name=element.getAttribute(Dictionary.DICTIONARY_ATTR_NAME);
				NodeList nodeList=element.getChildNodes();
				Element table=this.getElementFromNodeList(nodeList, Dictionary.DICTIONARY_TABLE);
				Element column=this.getElementFromNodeList(nodeList, Dictionary.DICTIONARY_COLUMN);
				if((name!=null)&&(table!=null)&&(column!=null)){
					return new Dictionary(name, 
										  table.getTextContent().trim(), 
										  column.getTextContent().trim());
				}else{
					logger.info("check dictionary by name (<"+Dictionary.DICTIONARY_TABLE+">, <"+Dictionary.DICTIONARY_COLUMN+">): "+name);
				}
			}
		}
		return null;
	}
	
	private Element getElementFromNodeList(NodeList list, String elementName){
		if(list==null)return null;
		if(elementName==null)return null;
		for(int index=0;index<list.getLength();index++){
			Node node=list.item(index);
			if(node instanceof Element){
				if(((Element)node).getNodeName().equals(elementName)){
					return (Element)node;
				}
			}
		}
		return null;
	}

	private ArrayList<Format> convertNodeListToFormats(NodeList nodeList) throws EDbLoaderException{
		ArrayList<Format> returnValue=new ArrayList<Format>();
		if(nodeList==null)return returnValue;
		
		for(int index=0;index<nodeList.getLength();index++){
			Format format=parseFormatNode(nodeList.item(index));
			if(format!=null){
				returnValue.add(format);
			}
		}
		return returnValue;
	}

	/** get Format element from node  
	 * @param node - for get Format element
	 * @return - format 
	 */
	private Format parseFormatNode(Node node) throws EDbLoaderException{
		if(node instanceof Element){
			Element element=(Element)node;
			if(!element.getNodeName().trim().toLowerCase().equals("format"))return null;
			String name=element.getAttribute("name");
			// get name;
			HashMap<String,String> attr=new HashMap<String,String>();
			// get attr
			if(element.hasChildNodes()){
				NodeList list=element.getChildNodes();
				for(int index=0;index<list.getLength();index++){
					Node currentNode=list.item(index);
					if((currentNode!=null)&&(currentNode instanceof Element)){
						Element currentElement=(Element)currentNode;
						attr.put(currentElement.getNodeName(), currentElement.getTextContent());
					}
				}
			}
			return FormatFactory.getFormat(name, attr);
		}else{
			return null;
		}
	}
	
	
	
	/** convert NodeList to List of File 
	 * @param list 
	 * @param list - formats list 
	 * */
	private ArrayList<File> convertNodeListToFiles(NodeList nodeList, 	
												   List<Format> formatList,
												   List<Dictionary> dictionaryList) throws EDbLoaderException{
		if(nodeList==null)throw new EDbLoaderException("File elements does not found, check XML File");
		ArrayList<File> returnValue=new ArrayList<File>();
		for(int counter=0;counter<nodeList.getLength();counter++){
			returnValue.add(readFileFromNode(nodeList.item(counter), formatList, dictionaryList));
		}
		return returnValue;
	}
	
	private File readFileFromNode(Node node, List<Format> formatList, List<Dictionary> dictionaryList) throws EDbLoaderException{
		try{
			Element element=(Element)node;
			String filePath=element.getAttribute("path");
			File.EType fileType=File.EType.valueOf(element.getAttribute("type"));
			logger.debug("parse File filePath:"+filePath+"   fileType:"+fileType);
			return new File(filePath, // full path to file 
							fileType, // file type
							getSheetsFromNode(fileType, 
											  node, 
											  formatList,
											  dictionaryList
											  ) // all sheets for file 
							);
		}catch(EDbLoaderException ex){
			throw ex;
		}catch(Exception ex){
			throw new EDbLoaderException("get File from Node Exception:"+ ex.getMessage());
		}
	}
	
	private List<ALoaderSheet> getSheetsFromNode(File.EType fileType, 
												 Node node, 
												 List<Format> listOfFormats,
												 List<Dictionary> listOfDictionary) throws EDbLoaderException {
		if(node.hasChildNodes()){
			ArrayList<ALoaderSheet> returnValue=new ArrayList<ALoaderSheet>();
			NodeList list=node.getChildNodes();
			for(int counter=0;counter<list.getLength();counter++){
				Node currentNode=list.item(counter);
				if(currentNode instanceof Element){
					Element currentElement=(Element)currentNode;
					if(currentElement.getNodeName().equals("sheet")){
						returnValue.add(getSheetFromElement(fileType, 
															currentElement, 
															listOfFormats,
															listOfDictionary));
					}else{
						logger.warn("why you have element "+currentElement.getNodeName()+" in XML file ?");
					}
				}else{
					// this is not element
				}
			}
			return returnValue;
		}else{
			return new ArrayList<ALoaderSheet>();
		}
	}

	/**      sheet name="table_one" table_name="test_table" start_row="1"   
	 * @param fileType - type of File 
	 * @param listOfFormats */
	private ALoaderSheet getSheetFromElement(File.EType fileType, 
											 final Element currentElement, 
											 final List<Format> listOfFormats,
											 final List<Dictionary> listOfDictionary) throws EDbLoaderException{
		try{
			LoaderSheetBuilder builder=new LoaderSheetBuilder();
			builder.setProperty(ALoaderSheet.ESheetAttributes.file_type, fileType);
			builder.setProperty(ALoaderSheet.ESheetAttributes.name, currentElement.getAttribute(ALoaderSheet.ESheetAttributes.name.name()));
			builder.setProperty(ALoaderSheet.ESheetAttributes.table_name, currentElement.getAttribute(ALoaderSheet.ESheetAttributes.table_name.name()));
			builder.setProperty(ALoaderSheet.ESheetAttributes.start_row, Integer.parseInt(currentElement.getAttribute(ALoaderSheet.ESheetAttributes.start_row.name()).trim()));
			builder.setProperty(ALoaderSheet.ESheetAttributes.dictionary_name, currentElement.getAttribute(ALoaderSheet.ESheetAttributes.dictionary_name.name()));
			builder.setProperty(ALoaderSheet.ESheetAttributes.dictionary_value, currentElement.getAttribute(ALoaderSheet.ESheetAttributes.dictionary_value.name()));
			logger.debug("    Sheet "+builder.getDebugInfoAboutProperty());
			return builder.build(new IColumnListHolder() {
								@Override
								public List<Column> getColumns() throws EDbLoaderException{
									return getColumnsFromElement(currentElement, listOfFormats);
									}
								},
								listOfDictionary
			); 
		}catch(Exception ex){
			throw new EDbLoaderException("get Sheet from ");
		}
	}
	
	
	/** column number="0" table_field="id"  
	 * @param listOfFormats */
	private List<Column> getColumnsFromElement(Element element, List<Format> listOfFormats) throws EDbLoaderException{
		if(element.hasChildNodes()){
			ArrayList<Column> returnValue=new ArrayList<Column>();
			NodeList list=element.getChildNodes();
			for(int counter=0;counter<list.getLength();counter++){
				Node currentNode=list.item(counter);
				if(currentNode instanceof Element){
					try{
						Element currentElement=(Element)currentNode;
						if(currentElement.getNodeName().equals("column")){
							int number=Integer.parseInt(currentElement.getAttribute(Column.NUMBER).trim());
							String tableField=currentElement.getAttribute(Column.TABLE_FIELD);
							Format format=Format.getFormatByName(listOfFormats, currentElement.getAttribute(Column.FORMAT));
							// logger.debug("        Column number:"+number+"   tableField:"+tableField+"   format_name:"+format);
							returnValue.add(new Column(number,tableField, format));
						}else{
							logger.warn("what the Element with name "+currentElement.getNodeName()+" into Sheet Node ?");
						}
					}catch(Exception ex){
						throw new EDbLoaderException(ex.getMessage());
					}
				}
			}
			return returnValue;
		}else{
			// element has no child nodes
			return new ArrayList<Column>();
		}
	}

	private NodeList getNodeSet(Document document, String path, String throwsMessage)throws EDbLoaderException{
		try{
			return (NodeList)xpath.evaluate(path, document,XPathConstants.NODESET);
		}catch(DOMException ex){
			logger.error("DOMException: "+ex.getMessage());
			throw new EDbLoaderException(throwsMessage);
		}catch(Exception ex){
			logger.error("Exception: "+ex.getMessage());
			throw new EDbLoaderException(throwsMessage);
		}
	}
	
	/** get Xml Element value from path */
	private String getElementAsString(Document document, String path, String throwsMessage ) throws EDbLoaderException{
		try{
			Element node=(Element)xpath.evaluate(path, document,XPathConstants.NODE);
			return node.getTextContent();
		}catch(DOMException ex){
			logger.error("DOMException: "+ex.getMessage());
			throw new EDbLoaderException(throwsMessage);
		}catch(Exception ex){
			logger.error("Exception: "+ex.getMessage());
			throw new EDbLoaderException(throwsMessage);
		}
	}
	
	/** получить XML файл из указанного абсолютного пути */
	private Document getXmlByPath(String path_to_xml) throws EDbLoaderException{
        Document return_value=null;
		javax.xml.parsers.DocumentBuilderFactory document_builder_factory=javax.xml.parsers.DocumentBuilderFactory.newInstance();
        document_builder_factory.setValidating(false);
        try {
            javax.xml.parsers.DocumentBuilder parser=document_builder_factory.newDocumentBuilder();
            return_value=parser.parse(new java.io.File(path_to_xml));
        }catch(Exception ex){
        	logger.error("XmlExchange.java ERROR");
        	throw new EDbLoaderException("parse XML File Exception:"+ex.getMessage());
        }
		return return_value;
	}
	
}
