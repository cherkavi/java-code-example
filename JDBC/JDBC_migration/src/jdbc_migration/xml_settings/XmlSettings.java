package jdbc_migration.xml_settings;

import java.io.File;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;

/** �����, ������� �������� ����������� ���������� ��� ���������� ������ � ���� XML ����� */
public class XmlSettings {
	private Document document;
	private XPath xpath;
	
	/** �����, ������� �������� ����������� ���������� ��� ���������� ������ � ���� XML ����� */
	public XmlSettings(String path) throws Exception{
		this.document=this.getXmlByPath(path);
		if(this.document==null){throw new Exception("");};
		// �������� ������� XPath
		XPathFactory factory=XPathFactory.newInstance();
		this.xpath=factory.newXPath();
	}
	
	/** �������� �������� �� XML ����� �������� ��������� XPath ���� <br>
	 * @return ���������� ���� ������ ������, ���� �������� 
	 * */
	public String getValue(String path){
		String return_value="";
		try{
			XPathExpression expression=this.xpath.compile(path);
			return_value=(String) expression.evaluate(this.document,XPathConstants.STRING);
		}catch(Exception ex){
			System.err.println("getValue: Exception:"+ex.getMessage());
		}
		return return_value;
	}
	
	/** �������� XML ���� �� ���������� ����������� ���� */
	private Document getXmlByPath(String path_to_xml){
        Document return_value=null;
		javax.xml.parsers.DocumentBuilderFactory document_builder_factory=javax.xml.parsers.DocumentBuilderFactory.newInstance();
        // ���������� ������������� ���������� Parser-��
        document_builder_factory.setValidating(false);
        try {
            // ��������� �����������
            javax.xml.parsers.DocumentBuilder parser=document_builder_factory.newDocumentBuilder();
            return_value=parser.parse(new File(path_to_xml));
        }catch(Exception ex){
        	System.err.println("XmlSettings ERROR:"+ex.getMessage());
        	//throw ex;
        }
		return return_value;
	}
	/** �������� �������� �� XML ����� �������� ��������� XPath ���� <br>
	 * @return ���������� ���� default_value 
	 * */
	public int getInteger(String path,int default_value){
		int return_value=default_value;
		try{
			return_value=Integer.parseInt(getValue(path));
		}catch(Exception ex){
			System.err.println("getInteger: Exception:"+ex.getMessage());
		}
		return return_value;
	}

	/** �������� �������� �� XML ����� �������� ��������� XPath ���� <br>
	 * @return ���������� ���� default_value 
	 * */
	public double getDouble(String path, double default_value){
		double return_value=default_value;
		try{
			return_value=Double.parseDouble(getValue(path));
		}catch(Exception ex){
			System.err.println("getInteger: Exception:"+ex.getMessage());
		}
		return return_value;
	}

	/** �������� ������ ��������, �������� ���������� ���� */
	public Object getNode(String path){
		Object returnValue=null;
		try{
			XPathExpression expression=this.xpath.compile(path);
			returnValue=expression.evaluate(this.document,XPathConstants.NODE);
		}catch(Exception ex){
			System.err.println("getNode: Exception:"+ex.getMessage());
		}
		return returnValue;
	}
}
