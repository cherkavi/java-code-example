package jdbc_copy;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import jdbc_copy.connection.PanelConnection;
import jdbc_copy.table.PanelManager;
import jdbc_copy.xml_settings.XmlSettings;


public class EnterPoint extends JFrame implements WindowListener{
	private final static long serialVersionUID=1L;
	private PanelConnection connectionSource;
	private PanelConnection connectionDestination;
	private PanelManager panelManager;
	
	public static void main(String[] args){
		new EnterPoint();
	}
	
	public EnterPoint(){
		super();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(800,700);
		initComponents();
		this.addWindowListener(this);
		this.setVisible(true);
		this.loadData();
	}
	
	private void initComponents(){
		this.connectionSource=new PanelConnection("Источник данных");
		this.connectionDestination=new PanelConnection("Приемник данных");
		this.panelManager=new PanelManager(this.connectionSource, this.connectionDestination);
		
		GroupLayout groupLayout=new GroupLayout(this.getContentPane());
		this.getContentPane().setLayout(groupLayout);
		GroupLayout.SequentialGroup groupLayoutHorizontal=groupLayout.createSequentialGroup();
		GroupLayout.SequentialGroup groupLayoutVertical=groupLayout.createSequentialGroup();
		groupLayout.setHorizontalGroup(groupLayoutHorizontal);
		groupLayout.setVerticalGroup(groupLayoutVertical);
		
		groupLayoutHorizontal.addGroup(groupLayout.createParallelGroup()
									  .addComponent(this.connectionSource)
									  .addComponent(this.connectionDestination)
									  .addComponent(this.panelManager)
									  );
		groupLayoutVertical.addComponent(this.connectionSource,GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE);
		groupLayoutVertical.addComponent(this.connectionDestination,GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE);
		groupLayoutVertical.addComponent(this.panelManager,GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,Short.MAX_VALUE);
	}

	@Override
	public void windowActivated(WindowEvent e) {
	}

	@Override
	public void windowClosed(WindowEvent e) {
	}

	@Override
	public void windowClosing(WindowEvent e) {
		if(JOptionPane.showConfirmDialog(this, "Сохранить введённые значения ?","",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
			this.saveData();
		}
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
	}

	@Override
	public void windowIconified(WindowEvent e) {
	}

	@Override
	public void windowOpened(WindowEvent e) {
	}
	
	private void loadData(){
		System.out.println("Load Data");
		String filePath=this.getFullPathToXml();
		
		String url=null;
		String driverClass=null;
		String login=null;
		String password=null;
		// попытка прочесть все данные из XML файла
		try{
			XmlSettings xmlSettings=new XmlSettings(filePath);
			url=xmlSettings.getValue("//jdbc_source/url");
			driverClass=xmlSettings.getValue("//jdbc_source/driver_class");
			login=xmlSettings.getValue("//jdbc_source/login");
			password=xmlSettings.getValue("//jdbc_source/password");
			
			if(url!=null)this.connectionSource.setUrl(url);
			if(driverClass!=null)this.connectionSource.setDriverClass(driverClass);
			if(login!=null)this.connectionSource.setLogin(login);
			if(password!=null)this.connectionSource.setPassword(password);

			url=xmlSettings.getValue("//jdbc_destination/url");
			driverClass=xmlSettings.getValue("//jdbc_destination/driver_class");
			login=xmlSettings.getValue("//jdbc_destination/login");
			password=xmlSettings.getValue("//jdbc_destination/password");
			
			if(url!=null)this.connectionDestination.setUrl(url);
			if(driverClass!=null)this.connectionDestination.setDriverClass(driverClass);
			if(login!=null)this.connectionDestination.setLogin(login);
			if(password!=null)this.connectionDestination.setPassword(password);
			
		}catch(Exception ex){
			System.err.println("Ошибка чтения файла XML "+ex.getMessage());
		}
	}
	
	private void saveData(){
		System.out.println("Save Data");
		javax.xml.parsers.DocumentBuilderFactory document_builder_factory=javax.xml.parsers.DocumentBuilderFactory.newInstance();
		document_builder_factory.setValidating(false);
		document_builder_factory.setIgnoringComments(true);
		try{
			javax.xml.parsers.DocumentBuilder document_builder=document_builder_factory.newDocumentBuilder();
			Document document=document_builder.newDocument();
			Node settings=document.createElement("settings");
			document.appendChild(settings);

			Node jdbcSource=document.createElement("jdbc_source");
			settings.appendChild(jdbcSource);

			Node jdbcSourceUrl=document.createElement("url");
			jdbcSourceUrl.setTextContent(this.connectionSource.getUrl());
			jdbcSource.appendChild(jdbcSourceUrl);

			Node jdbcSourceDriverClass=document.createElement("driver_class");
			jdbcSourceDriverClass.setTextContent(this.connectionSource.getDriverClass());
			jdbcSource.appendChild(jdbcSourceDriverClass);

			Node jdbcSourceLogin=document.createElement("login");
			jdbcSourceLogin.setTextContent(this.connectionSource.getLogin());
			jdbcSource.appendChild(jdbcSourceLogin);

			Node jdbcSourcePassword=document.createElement("password");
			jdbcSourcePassword.setTextContent(this.connectionSource.getPassword());
			jdbcSource.appendChild(jdbcSourcePassword);

			Node jdbcDestination=document.createElement("jdbc_destination");
			settings.appendChild(jdbcDestination);

			Node jdbcDestinationUrl=document.createElement("url");
			jdbcDestinationUrl.setTextContent(this.connectionDestination.getUrl());
			jdbcDestination.appendChild(jdbcDestinationUrl);

			Node jdbcDestinationDriverClass=document.createElement("driver_class");
			jdbcDestinationDriverClass.setTextContent(this.connectionDestination.getDriverClass());
			jdbcDestination.appendChild(jdbcDestinationDriverClass);

			Node jdbcDestinationLogin=document.createElement("login");
			jdbcDestinationLogin.setTextContent(this.connectionDestination.getLogin());
			jdbcDestination.appendChild(jdbcDestinationLogin);

			Node jdbcDestinationPassword=document.createElement("password");
			jdbcDestinationPassword.setTextContent(this.connectionDestination.getPassword());
			jdbcDestination.appendChild(jdbcDestinationPassword);
			
			
			javax.xml.transform.TransformerFactory transformer_factory = javax.xml.transform.TransformerFactory.newInstance();  
			javax.xml.transform.Transformer transformer = transformer_factory.newTransformer();  
			javax.xml.transform.dom.DOMSource dom_source = new javax.xml.transform.dom.DOMSource(document); // Pass in your document object here  
			java.io.FileWriter out=new java.io.FileWriter(this.getFullPathToXml());
			//string_writer = new Packages.java.io.StringWriter();  
			javax.xml.transform.stream.StreamResult stream_result = new javax.xml.transform.stream.StreamResult(out);  
			transformer.transform(dom_source, stream_result);  
			
		}catch(Exception ex){
			System.err.println("EnterPoint#saveData Exception: "+ex.getMessage());
		}
	}

	private final static String fileXmlName="settings.xml";
	
	/** получить полный путь к XML файлу */
	private String getFullPathToXml(){
		String currentDirectory=System.getProperty("user.dir");
		String fileSeparator=System.getProperty("file.separator");
		String path=null;
		if(currentDirectory.endsWith(fileSeparator)){
			path=currentDirectory+fileXmlName;
		}else{
			path=currentDirectory+fileSeparator+fileXmlName;
		}
		return path;
	}
	
	
}
