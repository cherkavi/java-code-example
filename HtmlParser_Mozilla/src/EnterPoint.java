import java.io.StringWriter;
import java.io.Writer;

import javax.xml.parsers.ParserConfigurationException;

import org.dom4j.DocumentException;
import org.w3c.dom.Document;

import com.dappit.Dapper.parser.MozillaParser;
import com.dappit.Dapper.parser.ParserException;
import com.dappit.Dapper.parser.ParserInitializationException;


/*
	Three (more or less) easy steps to integrate Mozilla parser into your code :

	1. Download And include the source code 

	2. Insert both the dist/bin and the dist/bin/components directories to the right enviroment variable. 

		On windows it is PATH variable and you can manipulate it threw Start->ControlPanel->System->Advanced->Enviroment Vairables 

		On Linux you should add it to LD_LIBRARY_PATH 
		export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:/path/to/dist/bin:/path/to/dist/bin/components 

		On MacOSX you should add it to DYLD_LIBRARY_PATH
		export DYLD_LIBRARY_PATH=$DYLD_LIBRARY_PATH:/path/to/dist/bin:/path/to/dist/bin/components  

		I know it's a pain , but it's the only way I managed to do it. if someone happens to be smarter than I , please send me a note. 

	3. Place the MozillaParser library file (dll/so/dynlib) in your java runtime library path , you can use the -Djava.library.path=... for this.

	4. Use this code to initialize the parser : 
		// let's say that c:\dapper\mozilla\dist\bin is where the mozilla components directory is , this initialized the parser libraries : 
		MozillaParser.init(null , "C:\\dapper\\mozilla\\dist\\bin"); 
		Document domDocument =new MozillaParser().parse("<html>Hello world!</html>"); 
 */

public class EnterPoint {
	public static void main(String[] args){
		//String value=System.getProperty("java.library.path");
		//System.out.println("value:"+value);
		try {
			//MozillaParser.init(null , "D:\\java_lib\\HtmlParser\\MozillaParser-v-0-3-0\\dist\\windows\\mozilla\\");
			String htmlText="<html>Hello world!</html>";
			Document domDocument =new MozillaParser().parse(htmlText.getBytes(),null);
			
			htmlText="<html><body><h1>Hello world!</h1><h2>это первый пример</h2></body></html>";
			domDocument =new MozillaParser().parse(htmlText.getBytes(),"windows-1251");
			//domDocument =new MozillaParser().parse(htmlText.getBytes(),"utf-8");
			
			System.out.println(domDocument+"\n");
			
			System.out.println(getStringFromXmlDocument(domDocument));
		} catch (ParserInitializationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
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
			//error("getStringFromXmlDocument:"+ex.getMessage());
		}
		return (out==null)?"":out.toString();
	}
	
}
