package cherkashin.vitaliy.db_loader.configurator;

import java.util.ArrayList;
import java.util.List;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cherkashin.vitaliy.db_loader.configurator.configuration.Configuration;
import cherkashin.vitaliy.db_loader.configurator.configuration.elements.Column;
import cherkashin.vitaliy.db_loader.configurator.configuration.elements.Connector;
import cherkashin.vitaliy.db_loader.configurator.configuration.elements.File;
import cherkashin.vitaliy.db_loader.configurator.configuration.elements.Sheet;
import cherkashin.vitaliy.db_loader.exception.EDbLoaderException;

/** object for read configuration from XML file  */
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
		logger.debug("read files");
		configuration.setFiles(convertNodeListToFiles(getNodeSet(document, "//db_loader/file","Error read element /db_loader/file")));
		return configuration;
	}

	/** convert NodeList to List of File */
	private ArrayList<File> convertNodeListToFiles(NodeList nodeList) throws EDbLoaderException{
		if(nodeList==null)throw new EDbLoaderException("File elements does not found, check XML File");
		ArrayList<File> returnValue=new ArrayList<File>();
		for(int counter=0;counter<nodeList.getLength();counter++){
			returnValue.add(readFileFromNode(nodeList.item(counter)));
		}
		return returnValue;
	}
	
	private File readFileFromNode(Node node) throws EDbLoaderException{
		try{
			Element element=(Element)node;
			String filePath=element.getAttribute("path");
			String fileType=element.getAttribute("type");
			logger.debug("parse File filePath:"+filePath+"   fileType:"+fileType);
			return new File(filePath, fileType, getSheetsFromNode(node));
		}catch(EDbLoaderException ex){
			throw ex;
		}catch(Exception ex){
			throw new EDbLoaderException("get File from Node Exception:"+ ex.getMessage());
		}
	}
	
	private List<Sheet> getSheetsFromNode(Node node) throws EDbLoaderException {
		if(node.hasChildNodes()){
			ArrayList<Sheet> returnValue=new ArrayList<Sheet>();
			NodeList list=node.getChildNodes();
			for(int counter=0;counter<list.getLength();counter++){
				Node currentNode=list.item(counter);
				if(currentNode instanceof Element){
					Element currentElement=(Element)currentNode;
					if(currentElement.getNodeName().equals("sheet")){
						returnValue.add(getSheetFromElement(currentElement));
					}else{
						logger.warn("why you have element "+currentElement.getNodeName()+" in XML file ?");
					}
				}else{
					// this is not element
				}
			}
			return returnValue;
		}else{
			return new ArrayList<Sheet>();
		}
	}

	/**      sheet name="table_one" table_name="test_table" start_row="1"   */
	private Sheet getSheetFromElement(Element currentElement) throws EDbLoaderException{
		try{
			String name=currentElement.getAttribute(Sheet.NAME);
			String tableName=currentElement.getAttribute(Sheet.TABLE_NAME);
			int startRow=Integer.parseInt(currentElement.getAttribute(Sheet.START_ROW).trim());
			logger.debug("    Sheet name:"+name+"   tableName:"+tableName+"   startRow:"+startRow);
			return new Sheet(name, tableName, startRow, getColumnFromElement(currentElement));
		}catch(Exception ex){
			throw new EDbLoaderException("get Sheet from ");
		}
	}
	
	
	/** column number="0" table_field="id"  */
	private List<Column> getColumnFromElement(Element element) throws EDbLoaderException{
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
							logger.debug("        Column number:"+number+"   tableField:"+tableField);
							returnValue.add(new Column(number,tableField));
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
