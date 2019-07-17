package terminal_client.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JToggleButton;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

import terminal.function.GetXMLfile;
import terminal.transfer.GetFile;
import terminal_client.gui.utility.Access;
import terminal_client.gui.utility.ModalClose;
import terminal_client.gui.windows.FrameMain;


public class JFrameMain extends JFrame implements ModalClose,GetFile{
	/** */
	private static final long serialVersionUID = 1L;
	/** рабочий стол, на котором будет осуществляться отображение всех внутренних элементов */
	private JDesktopPane field_desktop;
	/** объект, который содержит отображение текущих задач, либо лог уже реализованных*/
	private ProcessLogger field_panel_log;
	/** флаг, который говорит о состоянии панели - либо показана панель Log либо она спрятана */
	private boolean field_log_is_show=false;
	/** byte[] содержит в текстовом виде XML файл с разрешениями */
	private byte[] field_xml_content;
	private JSplitPane field_split_pane_log;
	
	
	/** Logger DEBUG */
	protected void debug(String information){
		System.out.print(this.getClass().getName());
		System.out.print(" DEBUG: ");
	    System.out.println(information);
	}
	/** Logger ERROR */
	protected void error(String information){
		System.out.print(this.getClass().getName());
		System.out.print(" ERROR: ");
	    System.out.println(information);
	}
	
	public JFrameMain(){
		super("Программа-клиент для системы БонКлуб");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(800,600);
		initComponents();
		this.setVisible(true);
		new FrameMain(new Access(readXMLDocument()),
				      field_desktop,
				      this,
				      this.field_panel_log);
	}
	
	/** чтение XML файла из источника, возможно чтение из Servlet */
	private Document readXMLDocument(){
		debug("readXMLDocument:begin");
		Document return_value=null;
		(new GetXMLfile("client_login",
						"client_password",
						this)
					    ).start(true);
		try{
			DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
	        domFactory.setNamespaceAware(true); // never forget this!
	        DocumentBuilder builder = domFactory.newDocumentBuilder();
	        debug("parse begin");
	        return_value = builder.parse(new ByteArrayInputStream(this.field_xml_content));
		}catch(Exception ex){
			error("readXMLDocument Error:"+ex.getMessage());
		}
/*		try{
			String path_to_xml="d:\\terminal_client.xml";

			DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
	        domFactory.setNamespaceAware(true); // never forget this!
	        DocumentBuilder builder = domFactory.newDocumentBuilder();
	        return_value = builder.parse(path_to_xml);
		}catch(Exception ex){
			System.out.println("JFrameMain XML ERROR:"+ex.getMessage());
		}
*/		
		debug("readXMLDocument:end");
		return return_value;
	}

	@Override
	public void getFile(String taskName, 
						String pathName, 
						String fileName,
						byte[] data) {
		this.field_xml_content=getStringFromZipBytes(data);
	}

	@Override
	public void getFile(String taskName, 
						String fileName, 
						byte[] data) {
		getFile(taskName,"",fileName,data);
	}

	
	private byte[] getStringFromZipBytes(byte[] data){
		debug("getStringFromZipBytes:begin");
		byte[] return_value=null;
		try{
            // получить поток 
            ByteArrayInputStream fis=new ByteArrayInputStream(data);
            // получить zip поток
            ZipInputStream zis=new ZipInputStream(new BufferedInputStream(fis));
            // получение очередной сущности
            ZipEntry entry;
            BufferedOutputStream dest;
            while((entry=zis.getNextEntry())!=null){
                if(entry.isDirectory()){
                    // Directory
                }else{
                	// File
                    int count;
                    byte buffer[] = new byte[2048];
                    // write the files to the disk
                    ByteArrayOutputStream bos=new ByteArrayOutputStream();
                    dest = new BufferedOutputStream(bos, 2048);
                    while ((count = zis.read(buffer, 0, 2048)) != -1) {
                       dest.write(buffer, 0, count);
                    }
                    dest.flush();
                    
                    return_value=bos.toByteArray();
                    bos.close();
                    dest.close();
                }
            }
            zis.close();
		}catch(Exception ex){
			error("getStringFromZipBytes: Exception:"+ex.getMessage());
		}
		debug("getStringFromZipBytes:end");
		return return_value;
	}
	
	
	
	/** Первоначальная инициализация компонентов */
	private void initComponents(){
		// create component's
		field_desktop=new JDesktopPane();
		JPanel panel_desktop=new JPanel(new BorderLayout());
		panel_desktop.add(field_desktop,BorderLayout.CENTER);
		
		JToggleButton button_show_process=new JToggleButton("",field_log_is_show){
			/** */
			private static final long serialVersionUID = 1L;

			public int getHeight(){
				return 10;
			}
		};
		panel_desktop.add(button_show_process,BorderLayout.SOUTH);
		
		this.field_panel_log=new ProcessLogger();
		JPanel panel_show_process=new JPanel(new BorderLayout()); 
		panel_show_process.add(field_panel_log,BorderLayout.CENTER);

		this.field_panel_log.setVisible(field_log_is_show);
		// add listener's
		button_show_process.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				on_button_show_process_click(e.getSource());
			}
		});
		// placing component's
		/*
		JPanel panel_content=new JPanel(new BorderLayout());
		panel_content.add(field_desktop,BorderLayout.CENTER);
		panel_content.add(panel_show_process,BorderLayout.SOUTH);
		this.getContentPane().add(panel_content);
		*/
		field_split_pane_log=new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		field_split_pane_log.setLeftComponent(panel_desktop);
		field_split_pane_log.setRightComponent(panel_show_process);
		field_split_pane_log.setResizeWeight(1.0);
		this.getContentPane().add(field_split_pane_log);
	}
	
	/** reaction on striking button show_process */
	private void on_button_show_process_click(Object sender){
		this.field_log_is_show=!(this.field_log_is_show);
		this.field_panel_log.setVisible(this.field_log_is_show);
		if(this.field_log_is_show==true){
			this.field_split_pane_log.setDividerLocation(0.7);
		}else{
			this.field_split_pane_log.setDividerLocation(1.0);
		}
		
		if(sender!=null){
			if(sender instanceof JToggleButton){
				System.out.println("set show");
				((JToggleButton)sender).setSelected(this.field_log_is_show);
			}
		}
	}
	
	@Override
	public void modalClose(int result) {
		System.exit(0);
	}

}















