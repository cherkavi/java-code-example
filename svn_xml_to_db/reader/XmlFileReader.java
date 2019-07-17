package svn_xml_to_db.reader;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import svn_xml_to_db.transit_format.LogEntry;
import svn_xml_to_db.transit_format.LogEntryFile;

public class XmlFileReader implements ISourceReader{
	private Logger logger=Logger.getLogger(this.getClass());
	private Document document=null;
	
	public XmlFileReader(String pathToFile) throws Exception{
		logger.trace("try to read file:"+pathToFile);
		try{
			document=getXmlByPath(pathToFile);
			logger.trace("file readed");
		}catch(Exception ex){
			logger.error("read from file Error:"+ex.getMessage());
			throw new Exception("read from file Error ");
		}
	}
	
	
	/** получить XML файл из указанного абсолютного пути */
	private Document getXmlByPath(String path_to_xml) throws Exception{
        Document return_value=null;
		javax.xml.parsers.DocumentBuilderFactory document_builder_factory=javax.xml.parsers.DocumentBuilderFactory.newInstance();
        // установить непроверяемое порождение Parser-ов
        document_builder_factory.setValidating(false);
        // получение анализатора
        javax.xml.parsers.DocumentBuilder parser=document_builder_factory.newDocumentBuilder();
        return_value=parser.parse(new File(path_to_xml));
		return return_value;
	}


	@Override
	public List<LogEntry> getLogEntry() throws ESourceReader {
		logger.trace("get list of LogEntry from XML file ");
		try{
			ArrayList<LogEntry> returnValue=new ArrayList<LogEntry>();
			NodeList nodeList=this.getSubNodeList(document, "/log/logentry");
			for(int counter=0;counter<nodeList.getLength();counter++){
				logger.trace("parse next element  "+(counter+1)+"/"+nodeList.getLength());
				Node currentNode=nodeList.item(counter);
				if(currentNode instanceof Element){
					LogEntry entry=null;
					try{
						entry=parseLogEntry((Element)currentNode);
					}catch(Exception ex){
						logger.error("parse element Error "+ex.getMessage()+"\n "+currentNode.getTextContent());
					}
					if(entry!=null){
						returnValue.add(entry);
					}else{
						logger.error("LogEntry does not recognize in element ");
					}
				}
			}
			logger.trace("list of log entry OK ");
			return returnValue;
		}catch(Exception ex){
			throw new ESourceReader(ex.getMessage());
		}
	}
	

	/** parse {@link LogEntry} from XML Element */
	private LogEntry parseLogEntry(Element element) throws Exception{
		logger.trace("try to parse LogEntry");
		if(element==null)return null;
		LogEntry returnValue=new LogEntry();
		returnValue.setAuthor(this.getTextContentOfSubElement(element, "author"));
		returnValue.setDate(parseDate(this.getTextContentOfSubElement(element, "date")));
		returnValue.setMessage(this.getTextContentOfSubElement(element, "msg"));
		returnValue.setRevision(element.getAttribute("revision"));
		returnValue.setTask(parseTask(returnValue.getMessage()));
		returnValue.setListOfFile(parseListOfFile(this.getSubNodeList(element, "paths/path")));
		logger.trace("LogEntry parsed");
		return returnValue;
	}

	/** parse list of Path */
	private List<LogEntryFile> parseListOfFile(NodeList subNodeList) {
		ArrayList<LogEntryFile> returnValue=new ArrayList<LogEntryFile>();
		if(subNodeList!=null){
			for(int counter=0;counter<subNodeList.getLength();counter++){
				Node currentNode=subNodeList.item(counter);
				if(currentNode instanceof Element){
					LogEntryFile logEntryFile=parseLogEntryFile((Element)currentNode);
					if(logEntryFile!=null){
						returnValue.add(logEntryFile);
					}
				}
			}
		}
		return returnValue;
	}


	/**  parse {@link LogEntryFile}  from XML Element */
	private LogEntryFile parseLogEntryFile(Element currentNode) {
		LogEntryFile returnValue=new LogEntryFile();
		returnValue.setAction(currentNode.getAttribute("action"));
		returnValue.setKind(currentNode.getAttribute("kind"));
		returnValue.setPath(currentNode.getTextContent().trim());
		return returnValue;
	}



	/** parse task "IT-" if exists  */
	private String parseTask(String message) {
		if(message==null){
			return null;
		}
		String clearMessage=message.toUpperCase();
		int controlPoint=clearMessage.indexOf("IT-");
		if(controlPoint>=0){
			// message text in middle string
			int beginIndex=controlPoint;
			while(beginIndex>0){
				beginIndex--;
				char controlChar=clearMessage.charAt(beginIndex);
				if((controlChar==' ')||(controlChar==',')||(controlChar=='.')||(controlChar==';')||(controlChar=='\t')||(controlChar=='\n')){
					beginIndex++;
					break;
				}
			}
			
			int endIndex=controlPoint;
			while(endIndex<(message.length())){
				char controlChar=clearMessage.charAt(endIndex);
				if((controlChar==' ')||(controlChar==',')||(controlChar=='.')||(controlChar==';')||(controlChar=='\t')||(controlChar=='\n')){
					break;
				}
				endIndex++;
			}
			return message.substring(beginIndex, endIndex).toUpperCase();
		}else{
			// not found
			return null;
		}
	}

	private SimpleDateFormat dateConverter=new SimpleDateFormat("yyyy-MM-ddHH:mm:ss");
	/** convert to {@link java.util.Date } from 2011-06-17T09:01:29.782950Z */
	private Date parseDate(String textContentOfSubElement) {
		try{
			String stringForParse=textContentOfSubElement.substring(0, 20).replaceAll("[^0-9^\\-^:]+", "").trim();
			return dateConverter.parse(stringForParse);
		}catch(Exception ex){
			return null;
		}
	}



	public String getTextContentOfSubElement(Node parentElement, String path){
		String returnValue=null;
		Element element=getSubElement(parentElement, path);
		if(element!=null){
			return element.getTextContent().trim();
		}
		return returnValue;
	}
	
	/** получить подэлемент из корневого элемента на основании XPath пути
	 * @param parentElement - родительский элемент 
	 * @param path - путь XPath
	 * @return - найденный Element или null
	 */
	public Element getSubElement(Node parentElement, String path){
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
	public NodeList getSubNodeList(Node parentElement, String path){
		try{
			XPath xpath=XPathFactory.newInstance().newXPath();
			Object returnValue=xpath.evaluate(path, parentElement,XPathConstants.NODESET);
			if(returnValue instanceof NodeList){
				return (NodeList)returnValue;
			}else{
				return null;
			}
		}catch(Exception ex){
			return null;
		}
	}
	
	
	
}
