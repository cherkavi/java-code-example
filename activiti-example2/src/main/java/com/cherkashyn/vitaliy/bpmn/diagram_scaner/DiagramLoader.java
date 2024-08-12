package com.cherkashyn.vitaliy.bpmn.diagram_scaner;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.DeploymentBuilder;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.cherkashyn.vitaliy.bpmn.utility.InputStreamIterator;
import com.cherkashyn.vitaliy.bpmn.utility.XmlUtility;
import com.cherkashyn.vitaliy.bpmn.xml_extender.XmlProcessor;

public class DiagramLoader {
	private static final Logger logger=Logger.getLogger(DiagramLoader.class);
	@Autowired(required=true)
	RepositoryService repositoryService;
	
	@Autowired(required=true)
	InputStreamIterator inputStreamIterator;
	
	private final static String magicStringForActiviti="bpmn20.xml";
	
	public void init() throws Exception, IOException, SAXException, ParserConfigurationException {
		DeploymentBuilder builder= repositoryService.createDeployment();
		for(Pair<String, InputStream> eachFile:inputStreamIterator){
			logger.debug(MessageFormat.format("Load data from file: {0}", eachFile.getKey()));
			Document document=convertStringToXml(getStringFromInputStream(eachFile.getValue()));
			String key=StringUtils.removeEnd(getFileName(eachFile.getKey()),".");
			document=filterIntercepter(document, key);
			logger.debug(getStringFromXml(document));
			builder.addInputStream(key+magicStringForActiviti, 
								   IOUtils.toInputStream(getStringFromXml(document)));
		}
		builder.deploy();
	}
	
	private String getStringFromXml(Document document) {
		return this.xmlUtility.getStringFromXml(document);
	}

	private String getFileName(String fileName) {
		return StringUtils.substringBeforeLast(fileName, "bpmn20.xml");
	}

	private XmlProcessor[] xmlProcessor;
	public void setXmlProcessors(XmlProcessor[] xmlProcessors){
		this.xmlProcessor=xmlProcessors;
	}
	
	private Document filterIntercepter(Document document, String fileName) throws Exception{
		if(xmlProcessor!=null && xmlProcessor.length>0){
			for(XmlProcessor processor:xmlProcessor){
				document=processor.processDocument(document, fileName);
			}
		}
		return document;
	}

	private XmlUtility xmlUtility=new XmlUtility();
	
	private Document convertStringToXml(String xmlText) throws UnsupportedEncodingException, SAXException, IOException, ParserConfigurationException{
		return xmlUtility.getDocumentFromString(xmlText);
	}
	
	private String getStringFromInputStream(InputStream inputStream) throws IOException{
		return linesToString(IOUtils.readLines(inputStream));
	}
	
	private String linesToString(List<String> list){
		StringBuilder returnValue=new StringBuilder();
		if(list!=null && list.size()>0){
			for(String line:list){
				returnValue.append(line);
			}
		}
		return returnValue.toString();
	}
	
}
