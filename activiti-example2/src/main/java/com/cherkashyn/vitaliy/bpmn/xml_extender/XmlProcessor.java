package com.cherkashyn.vitaliy.bpmn.xml_extender;

import org.w3c.dom.Document;

public interface XmlProcessor {
	public Document processDocument(Document document, String fileName) throws Exception;
}
