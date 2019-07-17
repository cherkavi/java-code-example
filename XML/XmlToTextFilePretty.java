import java.io.FileWriter;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class XmlToTextFilePretty {
	public static void main(String[] args) throws Exception{
		System.out.println("begin");
		Document document=getDocument();
		saveDocument(document, "c:\\temp.xml");
		System.out.println("-end-");
	}

	private static void saveDocument(Document document, String pathToFile) throws TransformerException, IOException {
		// TODO Auto-generated method stub
		// Configure transformer
        Transformer transformer = TransformerFactory.newInstance()
                        .newTransformer(); // An identity transformer
        transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "testing.dtd");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        transformer.transform(new DOMSource(document),  new StreamResult(new FileWriter(pathToFile)));
		
	}
/*
	public static OutputFormat getPrettyPrintFormat() {
	    OutputFormat format = new OutputFormat();
	    format.setLineWidth(120);
	    format.setIndenting(true);
	    format.setIndent(2);
	    format.setEncoding("UTF-8");
	    return format;
	}
*/
	
	private static Document getDocument() throws ParserConfigurationException {
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		org.w3c.dom.Document doc = documentBuilderFactory.newDocumentBuilder().newDocument();
		Element rootElement=doc.createElement("root_element");
		doc.appendChild(rootElement);
		Element childElement=doc.createElement("child_element");
		rootElement.appendChild(childElement);
		childElement.setTextContent("hello from file");
		return doc;
	}
}
