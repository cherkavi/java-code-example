import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.io.ObjectInputStream.GetField;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.lobobrowser.html.UserAgentContext;
import org.lobobrowser.html.domimpl.HTMLDocumentImpl;
import org.lobobrowser.html.parser.DocumentBuilderImpl;
import org.lobobrowser.html.parser.HtmlParser;
import org.lobobrowser.html.parser.InputSourceImpl;
import org.lobobrowser.html.test.SimpleUserAgentContext;
import org.w3c.dom.Document;
import org.w3c.dom.html.HTMLCollection;


// 

/**<b> ѕредназначен дл€ создани€ броузера средствами Java (Lobo), очень критичен к CSS и JavaScript - делает очень серьезные проверки </b>
 * <br> 
 * ƒемонстраци€ преобразовани€ HTML в org.w3c.Document средствами Cobra*/
public class EnterPoint {
	public static void main(String[] args){
		//Document document=getDocumentFromFile("d:\\temp.html");
		//String url="file:///d:/temp2.html";
		//String url="http://google.com.ua";
		String url="http://ava.com.ua/category/16/106/150/p1/";
		Document document=getDocumentFromUrl(url,"UTF-8");
		//System.out.println(getStringFromXmlDocument(document));
		System.out.println("End");
	}
	
	
	@SuppressWarnings("unused")
	private static Document getDocumentFromFile(String pathToFile){
		Document returnValue=null;
		try {
			UserAgentContext uacontext = new SimpleUserAgentContext();
            returnValue = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            HtmlParser parser = new HtmlParser(uacontext, returnValue);
            parser.parse(new FileReader(new File(pathToFile)));
            /*HTMLDocumentImpl document = (HTMLDocumentImpl) d;
            HTMLCollection images = document.getImages();
            int length = images.getLength();
            for(int i = 0; i < length; i++) {
                System.out.println("- Image#" + i + ": " + images.item(i));
            }*/
        }catch(Exception ex){
        	System.err.println("EnterPoint#getDocumentFromUrl Exception:"+ex.getMessage());
        }finally {
            
        }
        return returnValue;
	}
	
	@SuppressWarnings("unused")
	private static Document getDocumentFromUrl(String urlHttp, String charset){
		Document returnValue=null;
        InputStream in = null;
		try {
			Logger.getLogger("org.lobobrowser").setLevel(Level.WARNING);
			Logger.getLogger("com.steadystate").setLevel(Level.SEVERE);
			UserAgentContext uacontext = new SimpleUserAgentContext();
            DocumentBuilderImpl builder = new DocumentBuilderImpl(uacontext);
            in = new URL(urlHttp).openConnection().getInputStream();
            returnValue = builder.parse(new InputSourceImpl(new InputStreamReader(in, charset), 
            						    urlHttp));
            
            /*HTMLDocumentImpl document = (HTMLDocumentImpl) d;
            HTMLCollection images = document.getImages();
            int length = images.getLength();
            for(int i = 0; i < length; i++) {
                System.out.println("- Image#" + i + ": " + images.item(i));
            }*/
        }catch(Exception ex){
        	System.err.println("EnterPoint#getDocumentFromUrl Exception:"+ex.getMessage());
        }finally {
        	try{
        		in.close();
        	}catch(Exception ex){};
            
        }
        return returnValue;
	}
	
	
	
	
	/** тестирование, получить строку текста из объекта Document */
	private static String getStringFromXmlDocument(Document document){
		Writer out=null;
		try{
			javax.xml.transform.TransformerFactory transformer_factory = javax.xml.transform.TransformerFactory.newInstance();  
			javax.xml.transform.Transformer transformer = transformer_factory.newTransformer();  
			javax.xml.transform.dom.DOMSource dom_source = new javax.xml.transform.dom.DOMSource(document); // Pass in your document object here  
			out=new StringWriter();
			//string_writer = new Packages.java.io.StringWriter();  
			javax.xml.transform.stream.StreamResult stream_result = new javax.xml.transform.stream.StreamResult(out);  
			transformer.transform(dom_source, stream_result);  
		}catch(Exception ex){
			System.err.println("getStringFromXmlDocument:"+ex.getMessage());
		}
		return (out==null)?"":out.toString();
	}
	
}
