import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.jaxen.XPath;
import org.jaxen.dom.DOMXPath;
import org.w3c.dom.Node;


public class EnterPoint {
	public static void main(String[] args) throws Exception{
		  XPath xpath = new DOMXPath("/SETTINGS/DATABASE_PASSWORD");
		  DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		  //factory.setNamespaceAware(true);  		  
		  DocumentBuilder builder = factory.newDocumentBuilder(); 
		  Node doc=builder.parse("C:\\settings.xml ");
		  
		  List result = xpath.selectNodes(doc);
		  System.out.println("Result:"+((Node)result.get(0)).getTextContent());
	}
}
