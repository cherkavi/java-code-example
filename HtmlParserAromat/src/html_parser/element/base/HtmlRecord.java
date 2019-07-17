package html_parser.element.base;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/** класс-родитель для записи(Html данных) из блока данных HtmlBlock*/
public class HtmlRecord {
	private String value;
	private XPath xpath=null;
	
	public HtmlRecord(String value){
		this.value=value;
		init();
	}
	
	public HtmlRecord(){
		init();
	}

	private void init(){
		XPathFactory factory=XPathFactory.newInstance();
		this.xpath=factory.newXPath();
	}
	
	@Override
	public String toString(){
		return this.value;
	}
	
	/** возвращает Node на основании XPath выражения <br>
	 *  <b>Important:  
	 *  <li>  example expression="//td[1]/div[2]" - относительно корня всего документа</li>
	 *  <li>  example expression="td[1]/div[2]" - относительно данного Node</li>
	 *  </b>
	 * @param node - относительно чего искать
	 * @param expression - выражение для поиска
	 * @return Node или null - если произошла ошибка парсинга
	 * */
	protected Node getNodeByXPath(Node node, String expression){
		try{
			XPathExpression xpathExpression=this.xpath.compile(expression);
			Object object=xpathExpression.evaluate(node,XPathConstants.NODE);
			return (Node)object;
		}catch(Exception ex){
			System.err.println("getNodeByXPath error:"+ex.getMessage());
			return null;
		}
	}
	
	/** возвращает Node на основании XPath выражения <br>
	 *  <b>Important:  
	 *  <li>  example expression="//td[1]/div[2]" - относительно корня всего документа</li>
	 *  <li>  example expression="td[1]/div[2]" - относительно данного Node</li>
	 *  </b>
	 * @param node - относительно чего искать
	 * @param expression - выражение для поиска
	 * @return Node или null - если произошла ошибка парсинга
	 * */
	protected NodeList getNodeListByXPath(Node node, String expression){
		try{
			XPathExpression xpathExpression=this.xpath.compile(expression);
			Object object=xpathExpression.evaluate(node,XPathConstants.NODESET);
			return (NodeList)object;
		}catch(Exception ex){
			System.err.println("getNodeByXPath error:"+ex.getMessage());
			return null;
		}
	}
	
}
