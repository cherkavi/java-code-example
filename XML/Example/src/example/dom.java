/*
 * вщь.java
 *
 * Created on 29 квітня 2008, 15:57
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package example;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Когда вы разбираете XML-документ при помощи парсера DOM, вы получаете структуру дерева, которая представляет содержимое XML-документа. Весь текст, элементы и атрибуты (вместе с другими вещами, которые я вкратце рассмотрю) находятся в структуре дерева. DOM также предоставляет различные функции, которые вы можете использовать для исследования содержимого и структуры дерева и манипулирования ими. 
 */
public class dom {
    org.w3c.dom.Document dom_document;
    /** Creates a new instance of вщь */
    public dom(String path_to_file,String path_to_out_file) {
        // получение фабрики
        javax.xml.parsers.DocumentBuilderFactory document_builder_factory=javax.xml.parsers.DocumentBuilderFactory.newInstance();
        // установить непроверяемое порождение Parser-ов
        document_builder_factory.setValidating(false);
        try {
            // получение анализатора
            javax.xml.parsers.DocumentBuilder parser=document_builder_factory.newDocumentBuilder();
            //print_file(path_to_file);
            this.dom_document=parser.parse(new File(path_to_file));
            if(dom_document==null){
                System.out.println("NULL");
            }else{
                System.out.println(this.dom_document.getDocumentURI());
            }
            this.analize();
            if((path_to_out_file!=null)&&(!path_to_out_file.equals(""))){
                // нужно сохранить данные в файл
                try{
                    javax.xml.transform.TransformerFactory transformer_factory = javax.xml.transform.TransformerFactory.newInstance();  
                    javax.xml.transform.Transformer transformer = transformer_factory.newTransformer();  
                    javax.xml.transform.dom.DOMSource dom_source = new javax.xml.transform.dom.DOMSource(this.dom_document); // Pass in your document object here  
                    java.io.FileWriter out=new java.io.FileWriter(path_to_out_file);
                    //string_writer = new Packages.java.io.StringWriter();  
                    javax.xml.transform.stream.StreamResult stream_result = new javax.xml.transform.stream.StreamResult(out);  
                    transformer.transform(dom_source, stream_result);  
                    //xml_string = string_writer.toString(); // At this point you could use the Servoy XMLReader plugin to read your XML String.  
                    //application.output(xml_string);                  
                    out.flush();
                    System.out.println("XML saved");
                }catch(Exception ex){
                    System.out.println("Export XML Error");
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println("FileNotFoundException:"+ex.getMessage());
        } catch (SAXException ex) {
            System.out.println("SAXException:"+ex.getMessage());
        } catch (IOException ex) {
            System.out.println("IOException:"+ex.getMessage());
        } catch (ParserConfigurationException ex) {
            System.out.println("ParserConfigurationException:"+ex.getMessage());
        }
    }
    
    
    private void print_file(String path_to_file) {
        try {
            BufferedReader br=new BufferedReader(new InputStreamReader((new FileInputStream(path_to_file))));
            String line="";
            while((line=br.readLine())!=null){
                System.out.println(line);
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch(IOException ex){
            ex.printStackTrace();
        }
        
    }
    private void print_subnode(String prefix,org.w3c.dom.Node node){
        /*if(node.getNodeType()==node.ELEMENT_NODE){// элемент, который содержит данные
            System.out.print(prefix+node.getNodeName());
        }
        if(node.getNodeType()==node.TEXT_NODE){// текстовое поле, \n ...
            System.out.print(":"+node.getNodeValue());
        }
        if(node.getNodeType()==node.ATTRIBUTE_NODE){ //атрибуты
            System.out.print("["+((org.w3c.dom.Attr)node).getValue());
        }*/
        
        if(node.getNodeType()==node.ELEMENT_NODE){
            System.out.print(prefix+"Name:"+node.getNodeName()+"["+node.getNodeValue()+"] Type:"+node.getNodeType()+"     Text_content:"+node.getTextContent());
            if(node.hasAttributes()){
                System.out.println("("+node.getAttributes().getLength()+")");
            }else{
                System.out.println();
            }
        };
        /*
        if(node.getNodeType()==node.TEXT_NODE){
            System.out.println("Data:"+((org.w3c.dom.Text)node).getData()+"   NodeValue:"+node.getNodeName());
        }*/
        if(node.hasChildNodes()){
            org.w3c.dom.NodeList nodelist=node.getChildNodes();
            for(int index=0;index<nodelist.getLength();index++){
                print_subnode(prefix+prefix,nodelist.item(index));
            }
        }
        
    }
    private void analize(){
        org.w3c.dom.NodeList nodelist=this.dom_document.getElementsByTagName("DATABASE");
        if(nodelist==null){
            System.out.println("NULL");
        }else{
            if(nodelist.getLength()>0){
                for(int index=0;index<nodelist.getLength();index++){
                    print_subnode(" - ",nodelist.item(index));
                }
            }else{
                System.out.println("NodeList is 0");
            }
        }
        
    }
}
