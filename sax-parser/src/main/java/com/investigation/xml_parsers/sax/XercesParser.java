package com.investigation.xml_parsers.sax;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

public class XercesParser {
	public static void main(String[] args) throws FileNotFoundException, SAXException, IOException{
		System.out.print("Xerces");
		org.apache.xerces.parsers.SAXParser parser;
		parser = new org.apache.xerces.parsers.SAXParser();
		parser.setContentHandler(new ContentHandler(){

			public void characters(char[] arg0, int arg1, int arg2)
					throws SAXException {
				System.out.println("characters");
			}

			public void endDocument() throws SAXException {
				// TODO Auto-generated method stub
				
			}

			public void endElement(String arg0, String arg1, String arg2)
					throws SAXException {
				// TODO Auto-generated method stub
				
			}

			public void endPrefixMapping(String arg0) throws SAXException {
				// TODO Auto-generated method stub
				
			}

			public void ignorableWhitespace(char[] arg0, int arg1, int arg2)
					throws SAXException {
				// TODO Auto-generated method stub
				
			}

			public void processingInstruction(String arg0, String arg1)
					throws SAXException {
				// TODO Auto-generated method stub
				
			}

			public void setDocumentLocator(Locator arg0) {
				// TODO Auto-generated method stub
				
			}

			public void skippedEntity(String arg0) throws SAXException {
				// TODO Auto-generated method stub
				
			}

			public void startDocument() throws SAXException {
				// TODO Auto-generated method stub
				
			}

			public void startElement(String arg0, String arg1, String arg2,
					Attributes arg3) throws SAXException {
				// TODO Auto-generated method stub
				System.out.println("Start element");
			}

			public void startPrefixMapping(String arg0, String arg1)
					throws SAXException {
				// TODO Auto-generated method stub
				
			}
			
		});
		parser.parse(new InputSource(new FileInputStream("c:\\temp.xml")));
	}
}
