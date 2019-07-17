package terminal_client.gui.utility;

import java.util.StringTokenizer;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathExpression;

import org.w3c.dom.Document;

/** объект разрешений отображения визуальных элементов для всей системы */
public class Access {
	private Document field_xml_document=null;
	private XPath field_xpath;
	private XPathExpression field_expression;
	
	/** 
	 * объект, который позволяет задавать необходимые разрешения на отображения различных кнопок на экране 
	 */
	public Access(Document xml_document){
		this.field_xml_document=xml_document;
		XPathFactory factory=XPathFactory.newInstance();
		field_xpath=factory.newXPath();
	}
	
	/** 
	 * @param path_of_class полный путь к фрейму, который хотим отобразить 
	 * @return true, если отображение данного фрейма разрешено
	 * */
	public boolean isEnabledPath(String path_of_class){
		boolean return_value=false;
		StringTokenizer st=new StringTokenizer(path_of_class,".");
		String[] elements=new String[st.countTokens()];
		for(int counter=0;counter<elements.length;counter++){
			elements[counter]=st.nextToken();
		}
		StringBuffer path=new StringBuffer();
		path.append("/");
		for(int counter=0;counter<elements.length;counter++){
			path.append("/");
			path.append(elements[counter]);
			path.append("[@visible='true']");
		}
		try{
			this.field_expression=this.field_xpath.compile(path.toString());
			Object result_node=this.field_expression.evaluate(this.field_xml_document, XPathConstants.NODE);
			if(result_node!=null){
				// element Finded
				return_value=true;
			}else{
				// element not Finded
				return_value=false;
			}
		}catch(XPathExpressionException ex){
			System.out.println("XPath compile Error:"+ex.getMessage());
		}
		
		return return_value;
	}
}
