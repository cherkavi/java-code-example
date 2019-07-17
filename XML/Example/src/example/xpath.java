/*
 * xpath.java
 *
 * Created on 17 травня 2008, 9:19
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package example;
import java.io.IOException;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import javax.xml.parsers.*;
import javax.xml.xpath.*;
/**
 * прмер реализации XPath парсера
 */
public class xpath {
    /** Creates a new instance of xpath */
    public xpath(String path_to_xml) {
        try{
            System.out.println(":begin:");
            DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
            domFactory.setNamespaceAware(true); // never forget this!
            DocumentBuilder builder = domFactory.newDocumentBuilder();
            Document doc = builder.parse(path_to_xml);

            XPathFactory factory = XPathFactory.newInstance();
            XPath xpath = factory.newXPath();
            XPathExpression expr = xpath.compile("//TABLE/FIELD[NAME='DATE_WRITE' and PRIMARY_KEY='false']/TYPE/text()");
  
            // получить результат как NODESET
            Object result_nodeset = expr.evaluate(doc, XPathConstants.NODESET);
            NodeList nodes = (NodeList) result_nodeset;
            for (int i = 0; i < nodes.getLength(); i++) {
                System.out.println("<"+i+">:"+nodes.item(i).getNodeValue()); 
            };
            
            // получить результат как NODE
            Object result_node=expr.evaluate(doc,XPathConstants.NODE);
            Node node=(Node) result_node;
            System.out.println("Text Content:"+node.getTextContent());
            
            // получить результат как STRING
            Object result_string = expr.evaluate(doc, XPathConstants.STRING);
            System.out.println(">>>:"+(String)result_string);
            
            System.out.println(":end:");
        }catch(Exception ex){
            System.out.println("xpath#constructor Exception:"+ex.getMessage());
        }
    }
}
