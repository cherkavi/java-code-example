package com.cherkashyn.vitaliy.bpmn.xml_extender;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.cherkashyn.vitaliy.bpmn.utility.XmlUtility;

class CommonXmlProcessorTest {
	/** convert document to String  */
	protected String documentToString(Document changedDocument) {
		return new XmlUtility().getStringFromXml(changedDocument);
	}

	
	/** find document and load it */
	protected Document getDocument(String fileClassPath) throws IOException, SAXException, ParserConfigurationException {
		InputStream inputStream=new ClassPathResource(fileClassPath).getInputStream();
		List<String> lines=IOUtils.readLines(inputStream);
		return new XmlUtility().getDocumentFromString(getStringFromLines(lines));
	}
	
	
	/** convert lines to String  */
	protected String getStringFromLines(List<String> lines){
		StringBuilder returnValue=new StringBuilder();
		Iterator<String> iterator=lines.iterator();
		while(iterator.hasNext()){
			returnValue.append(iterator.next());
		}
		return returnValue.toString();
	}

}
