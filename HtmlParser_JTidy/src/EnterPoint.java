import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.StringWriter;
import java.io.Writer;

import org.w3c.dom.Document;
import org.w3c.tidy.Tidy;


/** попытка разработки JTidy парсера  -<br> 
 * <b>полученный document не полностью соответствует стандарту, то есть возможны нюансы даже с трансформацией</b>
 * 
 * */
public class EnterPoint {
	public static void main(String[] args){
		System.out.println("begin");
		Document document=getDocumentFromFile("d:\\temp.html","windows-1251");
		// полученный document не полностью соответствует стандарту, то есть возможны нюансы даже с трансформацией
		System.out.println(getStringFromXmlDocument(document));
		System.out.println("end");
	}
	
	private static Document getDocumentFromFile(String pathToFile, String charset){
		/* #1
		 * Document returnValue=null;
		try{
			Tidy tidy=new Tidy();
			//tidy.setCharEncoding(org.w3c.tidy.Configuration.UTF8);
			returnValue=tidy.parseDOM(new FileInputStream(pathToFile), System.out);
		}catch(Exception ex){
			System.err.println("getDocumentFromFile: "+ex.getMessage());
		}
		return returnValue;
		 */
		Document returnValue=null;
		try{
			Tidy tidy=new Tidy();
			//tidy.setCharEncoding(org.w3c.tidy.Configuration.UTF8);
			//tidy.setXmlOut(true);
			tidy.parseDOM(new FileInputStream(pathToFile), System.out);
		}catch(Exception ex){
			System.err.println("getDocumentFromFile: "+ex.getMessage());
		}
		return returnValue;
		
	}

	
	private static Document getDocumentFromUrl(String pathToFile, String charset){
		Document returnValue=null;
		try{
			Tidy tidy=new Tidy();
			//tidy.setCharEncoding(org.w3c.tidy.Configuration.UTF8);
			returnValue=tidy.parseDOM(new FileInputStream(pathToFile), System.out);
		}catch(Exception ex){
			System.err.println("getDocumentFromFile: "+ex.getMessage());
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
		}catch(Throwable ex){
			System.err.println("getStringFromXmlDocument:"+ex.getMessage());
		}
		return (out==null)?"":out.toString();
	}
	
}
