package jdbc_migration;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.sql.Connection;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

import jdbc_migration.connection.PanelConnection;
import jdbc_migration.container.IExportImport;
import jdbc_migration.container.PanelContainer;
import jdbc_migration.xml_settings.XmlSettings;

public class EnterPoint extends JFrame implements IExportImport, WindowListener {
	private final static String defaultXmlFileName="settings.xml";
	private final static long serialVersionUID=1L;
	private JTextField filePath;
	private PanelContainer container;
	private PanelConnection sourceConnection;
	private PanelConnection destinationConnection;
	
	public EnterPoint(){
		super("Экспортирование данных в запросы SQL ");
		initComponents();
		this.addWindowListener(this);
		this.setSize(900,800);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	private void initComponents(){
		sourceConnection=new PanelConnection("Источник данных");
		destinationConnection=new PanelConnection("Приемник данных");
		/** кнопка добавить */
		JButton buttonAdd=new JButton("Добавить Элемент");
		buttonAdd.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				onButtonAdd();
			}
		});
		/** поле ввода файла для вывода данных */
		filePath=new JTextField();
		/** заголовок для вывода данных */
		JLabel labelOpen=new JLabel("Путь к текстовому файлу для сохранения данных");
		/** кнопка открытия диалога поиска файла */
		JButton buttonOpenFile=new JButton("...");
		buttonOpenFile.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				onButtonOpen();
			}
		});
	
		JButton buttonExport=new JButton("Экспорт");
		buttonExport.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				onButtonExport();
			}
		});
		
		JButton buttonDestination=new JButton("Залить в приемник");
		buttonDestination.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				onButtonDestination();
			}
		});
		
		JPanel panelMain=new JPanel();
		GroupLayout groupLayout=new GroupLayout(panelMain);
		panelMain.setLayout(groupLayout);
		GroupLayout.SequentialGroup groupLayoutVertical=groupLayout.createSequentialGroup();
		GroupLayout.SequentialGroup groupLayoutHorizontal=groupLayout.createSequentialGroup();
		groupLayout.setHorizontalGroup(groupLayoutHorizontal);
		groupLayout.setVerticalGroup(groupLayoutVertical);
		
		groupLayoutVertical.addComponent(sourceConnection);
		groupLayoutVertical.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
									 .addComponent(buttonAdd)
									 .addGroup(groupLayout.createSequentialGroup()
											   .addComponent(labelOpen)
											   .addComponent(filePath)
											   )
									 .addComponent(buttonOpenFile)
									 .addComponent(buttonExport)
									 .addComponent(buttonDestination)
									 );
		groupLayoutVertical.addComponent(destinationConnection);
		
		groupLayoutHorizontal.addGroup(groupLayout.createParallelGroup()
									   .addComponent(sourceConnection)
									   .addGroup(groupLayout.createSequentialGroup()
											   	 .addComponent(buttonAdd)
											   	 .addGap(5)
											   	 .addGroup(groupLayout.createParallelGroup()
											   			   .addComponent(labelOpen)
											   			   .addComponent(filePath)
											   			   )
											   	.addGap(5)
												.addComponent(buttonOpenFile)
												.addGap(5)
												.addComponent(buttonExport)
												.addComponent(buttonDestination)
											   	 )
									   .addComponent(destinationConnection)
									   );
		container=new PanelContainer(this);
		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(panelMain,BorderLayout.NORTH);
		this.getContentPane().add(new JScrollPane(container),BorderLayout.CENTER);
		
		this.loadData();
	}
	
	/** экспорт всех элементов  */
	private void onButtonExport(){
		try{
			this.container.exportToFile();
			JOptionPane.showMessageDialog(this, "OK");
		}catch(Exception ex){
			System.err.println(" Exception: "+ex.getMessage());
			JOptionPane.showMessageDialog(this,ex.getMessage(), "Ошибка вывода данных",JOptionPane.ERROR_MESSAGE);
		}
	}

	/** реакция на нажатие клавиши "добавить" */
	private void onButtonAdd(){
		this.container.addElement();
	}
	
	/** реакция на нажатие клавиши "открыть" */
	private void onButtonOpen(){
		JFileChooser fileChooser=new JFileChooser();
		if(fileChooser.showOpenDialog(this)==JFileChooser.APPROVE_OPTION){
			this.filePath.setText(fileChooser.getSelectedFile().getPath());
		}else{
			// выбор отменён
		}
	}
	
	public static void main(String[] args){
		new EnterPoint();
	}

	@Override
	public File getExportFile() {
		try{
			return new File(this.filePath.getText());
		}catch(Exception ex){
			return null;
		}
	}

	private String fileXmlName=null;
	
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
			
			Node url=document.createElement("url");
			url.setTextContent(this.sourceConnection.getUrl());
			jdbcSource.appendChild(url);
			
			Node driverClass=document.createElement("driver_class");
			driverClass.setTextContent(this.sourceConnection.getDriverClass());
			jdbcSource.appendChild(driverClass);
			
			Node login=document.createElement("login");
			login.setTextContent(this.sourceConnection.getLogin());
			jdbcSource.appendChild(login);
			
			Node password=document.createElement("password");
			password.setTextContent(this.sourceConnection.getPassword());
			jdbcSource.appendChild(password);

			Node jdbcDestination=document.createElement("jdbc_destination");
			settings.appendChild(jdbcDestination);
			
			Node urlDestination=document.createElement("url");
			urlDestination.setTextContent(this.destinationConnection.getUrl());
			jdbcDestination.appendChild(urlDestination);
			
			Node driverClassDestination=document.createElement("driver_class");
			driverClassDestination.setTextContent(this.destinationConnection.getDriverClass());
			jdbcDestination.appendChild(driverClassDestination);
			
			Node loginDestination=document.createElement("login");
			loginDestination.setTextContent(this.destinationConnection.getLogin());
			jdbcDestination.appendChild(loginDestination);
			
			Node passwordDestination=document.createElement("password");
			passwordDestination.setTextContent(this.destinationConnection.getPassword());
			jdbcDestination.appendChild(passwordDestination);
			
			Node elements=document.createElement("elements");
			for(int counter=this.container.getElementCount()-1;counter>=0;counter--){
				Element element=document.createElement("element");
				
				Element query=document.createElement("query");
				query.setTextContent(this.container.getPanelElement(counter).getQuery());
				element.appendChild(query);
				
				Element deleteBeforeQuery=document.createElement("delete_before_insert");
				deleteBeforeQuery.setTextContent(Boolean.toString(this.container.getPanelElement(counter).isDeleteBeforeInsert()));
				element.appendChild(deleteBeforeQuery);
				
				elements.appendChild(element);
			}
			settings.appendChild(elements);
			
			javax.xml.transform.TransformerFactory transformer_factory = javax.xml.transform.TransformerFactory.newInstance();  
			javax.xml.transform.Transformer transformer = transformer_factory.newTransformer();  
			javax.xml.transform.dom.DOMSource dom_source = new javax.xml.transform.dom.DOMSource(document); // Pass in your document object here  
			java.io.FileWriter out=new java.io.FileWriter(this.getFullPathToXml());
			//string_writer = new Packages.java.io.StringWriter();  
			javax.xml.transform.stream.StreamResult stream_result = new javax.xml.transform.stream.StreamResult(out);  
			transformer.transform(dom_source, stream_result);  
			
		}catch(Exception ex){
			System.out.println("Save Data: "+ex.getMessage());
		}
	}
	
	private void loadData(){
		System.out.println("Load Data");
		String filePath=this.getFullPathToXml();
		if(filePath==null){
			if(JOptionPane.showConfirmDialog(this, "<html>Do you want choice XML file ? <br>( No - settings.xml) </html>", "Settings choice", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
				// показать меню выбора файлов
				JFileChooser fileChooser=null;
				try{
					fileChooser=new JFileChooser(new File("."));
				}catch(Exception ex){
					fileChooser=new JFileChooser();
				}
				fileChooser.addChoosableFileFilter(new FileFilter(){
					@Override
					public boolean accept(File f) {
						boolean returnValue=false;
						if(f.exists()){
							if(f.isDirectory()){
								returnValue=true;
							}else{
								String fileName=f.getName();
								int lastDotPosition=fileName.lastIndexOf(".");
								if(lastDotPosition>0){
									String extension=fileName.substring(lastDotPosition+1);
									if(extension.equalsIgnoreCase("XML")){
										returnValue=true;
									}
								}else{
									// extension of file does not found 
									returnValue=false;
								}
							}
						}
						return returnValue;
					}
					@Override
					public String getDescription() {
						return "XML файл, который содержит необходимые данные ";
					}
					
				});
				if(fileChooser.showOpenDialog(this)==JFileChooser.APPROVE_OPTION){
					this.fileXmlName=fileChooser.getSelectedFile().getAbsolutePath();
				}else{
					// выбрана отмена
					this.fileXmlName=defaultXmlFileName;
				}
			}else{
				// отмена выбора файла 
				this.fileXmlName=defaultXmlFileName;
			}
		}else{
			// файл уже имеет имя, возможно это "settings.xml"
		}
		filePath=this.getFullPathToXml();
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
			
			this.sourceConnection.setUrl(url);
			this.sourceConnection.setDriverClass(driverClass);
			this.sourceConnection.setLogin(login);
			this.sourceConnection.setPassword(password);

			url=xmlSettings.getValue("//jdbc_destination/url");
			driverClass=xmlSettings.getValue("//jdbc_destination/driver_class");
			login=xmlSettings.getValue("//jdbc_destination/login");
			password=xmlSettings.getValue("//jdbc_destination/password");
			
			this.destinationConnection.setUrl(url);
			this.destinationConnection.setDriverClass(driverClass);
			this.destinationConnection.setLogin(login);
			this.destinationConnection.setPassword(password);
			
			Object object=xmlSettings.getNode("//elements");
			if(object instanceof NodeList){
				NodeList nodeList=(NodeList)object;
				for(int counter=0;counter<nodeList.getLength();counter++){
					if((nodeList.item(counter).getNodeType()==Node.ELEMENT_NODE)&&( ((Element)nodeList.item(counter)).getNodeName().equals("element"))){
						String query="";
						try{
							query=this.getChildNodeByName(nodeList.item(counter),"query").getTextContent();
						}catch(NullPointerException ex){};
						String isDeleteBeforeInsert=null;
						try{
							isDeleteBeforeInsert=this.getChildNodeByName(nodeList.item(counter),"delete_before_insert").getTextContent();
						}catch(NullPointerException ex){};
						boolean deleteBeforeInsert=false;
						if(isDeleteBeforeInsert==null){
							deleteBeforeInsert=false;
						}else{
							try{
								deleteBeforeInsert=Boolean.parseBoolean(isDeleteBeforeInsert);
							}catch(Exception ex){
								System.err.println("EnterPoint#loadData parse DeleteBeforeInsert Exception: "+ex.getMessage());
							}
						}
						//System.out.println("Query: "+query);
						if(query!=null){
							this.container.addElement(query,deleteBeforeInsert);
						}
					}
				}
			}
		}catch(Exception ex){
			System.err.println("Ошибка чтения файла XML "+ex.getMessage());
		}
	}
	
	/** получить Node из родительского Node, имея */
	private Node getChildNodeByName(Node node, String childNodeName){
		if((node!=null)&&(node.getNodeName()!=null)){
			if(node.getNodeName().equals(childNodeName)){
				return node;
			}else{
				if(node.hasChildNodes()){
					NodeList list=node.getChildNodes();
					Node returnValue=null;
					for(int counter=0;counter<list.getLength();counter++){
						if((list.item(counter).getNodeName()!=null)&&(list.item(counter).getNodeName().equals(childNodeName))){
							returnValue=list.item(counter);
							break;
						}
						returnValue=this.getChildNodeByName(list.item(counter), childNodeName);
						if(returnValue!=null)break;
					}
					return returnValue;
				}else{
					return null;
				}
			}
		}else{
			return null;
		}
	}
	
	
	/** получить полный путь к XML файлу */
	private String getFullPathToXml(){
		if(fileXmlName==null){
			return null;
		}else{
			return fileXmlName;
		}
		/*
		String path=null;
		if(fileXmlName.equals(defaultXmlFileName)){
			String currentDirectory=System.getProperty("user.dir");
			String fileSeparator=System.getProperty("file.separator");
			if(currentDirectory.endsWith(fileSeparator)){
				path=currentDirectory+fileXmlName;
			}else{
				path=currentDirectory+fileSeparator+fileXmlName;
			}
		}
		return path;
		*/
	}
	
	@Override
	public Connection getImportConnection() {
		return this.sourceConnection.getConnection();
	}

	@Override
	public void windowActivated(WindowEvent e) {
	}

	@Override
	public void windowClosed(WindowEvent e) {
	}

	@Override
	public void windowClosing(WindowEvent e) {
		if(JOptionPane.showConfirmDialog(this, "Сохранить введённые данные ?","Сохранение настроек",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
			saveData();
		}else{
			// отмена сохранения данных
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

	@Override
	public Connection getExportConnection() {
		return this.destinationConnection.getConnection();
	}
	
	public void onButtonDestination(){
		try{
			this.container.exportToFile();
			JOptionPane.showMessageDialog(this, "OK");
		}catch(Exception ex){
			System.err.println(" Exception: "+ex.getMessage());
			JOptionPane.showMessageDialog(this,ex.getMessage(), "Ошибка вывода данных",JOptionPane.ERROR_MESSAGE);
		}
	}
}
