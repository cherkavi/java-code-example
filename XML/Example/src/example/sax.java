package example;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
/*
 * sax.java
 *
 * Created on 29 квітня 2008, 14:48
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 * Когда вы разбираете XML-документ при помощи парсера SAX, парсер генерирует серию событий по мере того, как он читает документ. На ваше усмотрение - что делать по этим событиям. Вот несколько примеров событий, которые вы можете получить при разборе XML-документа: 
 * Событие startDocument. 
 * Для каждого элемента, событие startElement при начале элемента и событие endElement при окончании элемента. 
 * Если в элементе есть содержимое, будут такие события, как characters для дополнительного текста, startElement и endElement для дочерних элементов и т.п. 
 * Событие endDocument. 
 */
public class sax extends org.xml.sax.helpers.DefaultHandler {
    
    /**  */
    public sax(String path_to_file) {
        // создаем фабрику анализаторов
        javax.xml.parsers.SAXParserFactory sax_parser_factory=javax.xml.parsers.SAXParserFactory.newInstance();
        // задаем отсутствие документа DTD
        sax_parser_factory.setValidating(false);
        javax.xml.parsers.SAXParser sax_parser;
        try {
            // получаем объект, который будет производить разбор(Parsing) файла
            sax_parser=sax_parser_factory.newSAXParser();
            // задаем источник
            // можно заменить на StringReader, если получаем не файл, а строку
            org.xml.sax.InputSource input_source=new org.xml.sax.InputSource(new FileReader(path_to_file));
            // разбор (Parsing)
            sax_parser.parse(input_source,this);
        } catch (FileNotFoundException ex) {
            System.out.println("FileNotFoundException:"+ex.getMessage());
        } catch(IOException iox){
            System.out.println("IOException:"+iox.getMessage());
        }catch (ParserConfigurationException ex) {
            System.out.println("ParserConfigurationException:"+ex.getMessage());
        } catch (SAXException ex) {
            System.out.println("SAXException:"+ex.getMessage());
        }
        
    }
    
    /** чтение данных - символов не относящихся к тэгам разметки*/
    public void characters(char[] ch, int start, int length){
        if((new String(ch,start,length)).trim().equals("")){
            // прочитан пустой элемент разметки - символы Tab, \n
        }else{
            // прочитан не пустой элемент - данные
            System.out.println(new String(ch,start,length));
        }
    }
    /** прочитан стартовый элемент */
    public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException{
        System.out.println("<NameSpace:"+uri+"    localName:"+localName+"    qualified Name:"+qName+"  Attributes:"+attributes.getLength());
        if(attributes.getLength()>0){
            for(int index=0;index<attributes.getLength();index++){
                System.out.println("   attributes       type:"+attributes.getType(index)+"    Value:"+attributes.getValue(index)+"    localName:"+attributes.getLocalName(index)+"    qName:"+attributes.getQName(index)+"   URI:"+attributes.getURI(index));
            }
        }
    }
    /** прочитан замыкающий символ*/
    public void endElement(String uri,String localName,String qName){
        System.out.println(">NameSpace:"+uri+"    localName:"+localName+"    qualified Name:"+qName);
    }
    /** start parsing*/
    public void startDocument(){
        System.out.println("Start parsing");
    }
    /** end parsing*/
    public void endDocument(){
        System.out.println("End document");
    }
    /** warning in parsing process*/
    public void warning(SAXParseException e){
        System.out.println("!!! Exception:"+e.toString());
    }
    /** error*/
    /** fatalError */
}
