package bonclub.office_private.gui;

import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.JApplet;
import javax.swing.JOptionPane;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import jms.listener.ITextDataRecieve;
import jms.listener.JMSListener;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import bonclub.office_private.gui.chat_panel.PanelChat;
import bonclub.office_private.gui.text_exchange.ITextInput;
import bonclub.office_private.gui.text_exchange.ITextOutput;
import bonclub.office_private.gui.text_exchange.JmsTextSender;

public class EnterPointApplet extends JApplet implements ITextDataRecieve{
	private static final long serialVersionUID = 1L;

	public EnterPointApplet(){
	}
	
	private void debug(String message){
		System.out.println("DEBUG EnterPointApplet#"+message);
	}

	private void error(String message){
		System.err.println("DEBUG EnterPointApplet#"+message);
	}
	
	public void init() {
		debug("loadParameters");
		loadParameters();
		debug("initComponents");
		initComponents();
		/*try {
			javax.swing.SwingUtilities.invokeAndWait(new Runnable(){
				@Override
				public void run() {
					
				}
			});
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}*/
	};
	
	/** полный путь к JMS сервису  */
	private String parameterJmsServer;
	/** имя входящей очереди  */
	private String parameterJmsInput;
	/** имя исходящей очереди  */
	private String parameterJmsOutput;
	/** заголовок для отправщика сообщений  */
	private String parameterSenderName;
	
	/** загрузить параметры  */
	private boolean loadParameters(){
		boolean returnValue=false;
		try{
			String pathToSettings=this.getParameter("settings_path");
			String keyForSettings=this.getParameter("settings_key");
			// обратиться на удаленный сервлет - получить данные в виде XML
			String xmlDocument=this.getDataFromRemoteUrl(pathToSettings, keyForSettings);
			if(xmlDocument!=null){
				Document document=this.getXmlFromString(xmlDocument);
				/*
					<CHAT> 
						<JMS_SERVER></JMS_SERVER> 
						<JMS_INPUT></JMS_INPUT> 
						<JMS_OUTPUT></JMS_OUTPUT> 
						<SENDER_NAME></SENDER_NAME>
					</CHAT> 				 
				*/
				this.parameterJmsServer=this.getStringFromDocumentByPath(document, "//CHAT/JMS_SERVER");
				debug(this.parameterJmsServer);
				this.parameterJmsInput=this.getStringFromDocumentByPath(document, "//CHAT/JMS_INPUT");
				debug(this.parameterJmsInput);
				this.parameterJmsOutput=this.getStringFromDocumentByPath(document, "//CHAT/JMS_OUTPUT");
				debug(this.parameterJmsOutput);
				this.parameterSenderName=this.getStringFromDocumentByPath(document, "//CHAT/SENDER_NAME");
				debug(this.parameterSenderName);
			}else{
				throw new Exception("xmlDocument does not recieve ");
			}
		}catch(Exception ex){
			error("loadParameters Exception:"+ex.getMessage());
		}
		return returnValue;
	}
	
	/** получить текстовое содержимое указанного (XPath) Node */
	protected String getStringFromDocumentByPath(Document document, String path){
		Node node=this.getNodeFromDocument(document, path);
		if(node!=null){
			return node.getTextContent();
		}else{
			return null;
		}
	}
	
	private XPath xpath = XPathFactory.newInstance().newXPath();
	/** получить Node по указанному пути */
	protected Node getNodeFromDocument(Document document, String path){
		try {
			return (Node)xpath.evaluate(path, document, XPathConstants.NODE);
		} catch (XPathExpressionException e) {
			return null;
		}
	}

	/** получить NodeList по указанному Node */
	protected NodeList getNodeSetFromDocument(Document document, String path){
		try {
			return (NodeList)xpath.evaluate(path, document, XPathConstants.NODESET);
		} catch (XPathExpressionException e) {
			return null;
		}
	}
	
	/** 
	 * получить из String, содержащий вид XML файла объект Document
	 * @param value строка, содержащая XML текст
	 * @return null, если произошла ошибка парсинга, либо же сам XML в виде org.w3c.dom.Document
	 */
	private Document getXmlFromString(String value){
		Document return_value=null;
		javax.xml.parsers.DocumentBuilderFactory document_builder_factory=javax.xml.parsers.DocumentBuilderFactory.newInstance();
        // установить непроверяемое порождение Parser-ов
        document_builder_factory.setValidating(false);
        try {
            // получение анализатора
            javax.xml.parsers.DocumentBuilder parser=document_builder_factory.newDocumentBuilder();
            // Parse источник
            return_value=parser.parse(new ByteArrayInputStream(value.getBytes("UTF-8")));
        }catch(Exception ex){
        	error("getXmlFromString from String exception: "+ex.getMessage());
        }
		return return_value;
	}
	
	
	/** обратиться на удаленный ресурс и получить XML документ 
	 * @param path - полный путь к удаленному документу
	 * @param cookieKey - JSESSIONID - уникальный сессионный номер
	 * @return строку, содержащую XML файл
	 */
	private String getDataFromRemoteUrl(String path, String cookieKey){
		StringBuffer returnValue=new StringBuffer();
		URL url;
		try{ // Construct data String 
			// data = URLEncoder.encode("key1", "UTF-8") + "=" + URLEncoder.encode("value1", "UTF-8"); 
			// data += "&" + URLEncoder.encode("key2", "UTF-8") + "=" + URLEncoder.encode("value2", "UTF-8"); 
			// Send data URL 
			url = new URL(path); 
			URLConnection conn = url.openConnection();
			// если Cookie много - через запятую
			conn.setRequestProperty("Cookie", "JSESSIONID="+cookieKey);
			conn.setDoOutput(true); 
			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream()); 
			// wr.write(data); 
			// wr.flush(); 
			// Get the response 
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream())); 
			String line; 
			while ((line = rd.readLine()) != null) {
				returnValue.append(line);
			} 
			wr.close(); 
			rd.close(); 
		} catch (Exception e) { 
			error("EnterPoin#main Exception:"+e.getMessage());
		}
		return returnValue.toString();
	}
	
	private ITextOutput textOutput;
	private ITextInput textInput;
	
	private void initComponents(){
		try{
			this.getContentPane().setLayout(new GridLayout(1,1));
			debug("создать отправителя сообщений из визуального интерфейса");
			textOutput=new JmsTextSender(this.parameterJmsServer, this.parameterJmsOutput, this.parameterSenderName);
			debug("создать получателя сообщений из визуального интерфейса"); 
			PanelChat panelChat=new PanelChat("Администратор", textOutput);
			this.getContentPane().add(panelChat);
			this.textInput=panelChat;
			debug("запустить слушателя для JMS ");
			new JMSListener(this.parameterJmsServer, this.parameterJmsInput, this);
		}catch(Exception ex){
			error("#initComponents Exception:"+ex.getMessage());
			JOptionPane.showMessageDialog(this, "Applet does not started", "Error", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
	}
	
	@Override
	public void recieveTextData(String sender, String remoteTextMessage) {
		// place of receipt of incoming messages
		this.textInput.textInput( sender, remoteTextMessage);
	}
	
}
