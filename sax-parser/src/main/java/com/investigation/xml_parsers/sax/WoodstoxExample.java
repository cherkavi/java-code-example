
package com.investigation.xml_parsers.sax;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class WoodstoxExample {
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException{
		System.out.println(" hello ");
		javax.xml.parsers.SAXParser parser=com.ctc.wstx.sax.WstxSAXParserFactory.newInstance().newSAXParser();
		parser.parse(new File("c:\\temp.xml"), new DefaultHandler(){
			@Override
			public void characters(char[] ch, int start, int length)
					throws SAXException {
				super.characters(ch, start, length);
			}
			@Override
			public void startElement(String uri, String localName,
					String qName, Attributes attributes) throws SAXException {
				super.startElement(uri, localName, qName, attributes);
			}
		});
	}
}
