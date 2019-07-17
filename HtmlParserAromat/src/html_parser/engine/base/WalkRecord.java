package html_parser.engine.base;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.Reader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.dom4j.DocumentException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.dappit.Dapper.parser.MozillaParser;
import com.dappit.Dapper.parser.ParserException;

import html_parser.element.base.HtmlPage;
import html_parser.element.base.HtmlRecord;

/**
 * @author cherkashinv
 *
 */
public abstract class WalkRecord {
	/** страница с данными */
	protected HtmlPage page;
	
	/** создать объект на основании HtmlPage - страницы с данными */
	public WalkRecord(HtmlPage page){
		this.page=page;
	}
	
	
	/** получить следующую порцию данных из страницы 
	 * @return следующую запись, или null, если данные закончены 
	 * */
	public abstract HtmlRecord getNextRecord();
	
	
	/** получить байты данных на основании Reader и таблицы кодировки 
	 * @param reader - Reader
	 * @param carsetName - кодировка для преобразования в байты 
	 * */
	protected byte[] getBytes(Reader reader, String charsetName) throws IOException{
		StringBuffer output=new StringBuffer();
		//new InputStreamReader(new FileInputStream(this.pathToFile),charsetName)
		BufferedReader br=new BufferedReader(reader,1024);
		String currentLine=null;
		while((currentLine=br.readLine())!=null){
			output.append(currentLine);
		}
		return output.toString().getBytes(charsetName);
		
	}
	
	
	/**
	 * получить XML Node с блоком данных, в котором находятся все записи 
	 * @param reader - из страницы 
	 * @param charsetName - кодировка для чтения данных 
	 * @param xpathToBlockData -путь в виде XML к блоку с записями 
	 * @return
	 * @throws IOException
	 * @throws DocumentException
	 * @throws ParserException
	 * @throws ParserConfigurationException
	 * @throws XPathExpressionException
	 */
	protected  Node getNodeWithDataBlock(Reader reader, 
							  String charsetName, 
							  String xpathToBlockData) throws IOException, 
							  								  DocumentException, 
							  								  ParserException, 
							  								  ParserConfigurationException, 
							  								  XPathExpressionException {
		byte[] data=this.getBytes(this.page.getReader(), charsetName);
		MozillaParser parser = new MozillaParser();
		Document doc = parser.parse( data, charsetName); // windows-1251
		XPathFactory factory=XPathFactory.newInstance();
		XPath field_xpath=factory.newXPath();
		XPathExpression expression=field_xpath.compile(xpathToBlockData);
		return (Node)expression.evaluate(doc,XPathConstants.NODE);
	}
	
}
