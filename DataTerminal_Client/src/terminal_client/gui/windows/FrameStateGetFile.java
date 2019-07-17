package terminal_client.gui.windows;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import terminal.function.RequestBoncard;
import terminal.function.RequestBoncardCatalog;
import terminal.transfer.GetFile;
import terminal.transfer.GetString;
import terminal.transfer.PercentReport;
import terminal_client.gui.utility.JInternalFrameParent;
import terminal_client.gui.utility.ModalClose;

public class FrameStateGetFile extends JInternalFrameParent
							   implements GetFile,GetString{
	/** */
	private static final long serialVersionUID = 1L;
	/** каталог, в который нужно сохранять данные */
	private String field_directory="c:\\";
	
	/** уникальное имя для задачи по получению пути к файлам, от сервера */
	private String field_function_get_file=this.addUniqueString("RequestBoncardCatalog");
	/** полный путь к каталогу с файлами */
	private String field_function_get_directory=this.addUniqueString("RequestBoncard");
	
	public FrameStateGetFile(JInternalFrameParent parent, JDesktopPane desktop, 
							 ModalClose parent_element,PercentReport percent_report) {
		super(parent.getAccess(),
			  parent, 
			  desktop, 
			  parent_element, 
			  percent_report, 
			  "Загрузить новые файлы с сервера", 
			  300, 
			  100);
	}

	@Override
	protected void initComponents() {
		// create component's
		JButton button_load=new JButton("Проверить наличие и закачать файлы");
		// add listener's
		button_load.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				on_load_click();
			}
		});
		JPanel panel_button=this.getEmptyPanelWithComponent(button_load, 5, 5, 5, 5);
		// placing component's
		/** панель расположений*/
		JPanel panel_main=new JPanel();
		panel_main.setLayout(new BorderLayout());
		
		/** панель управления загрузкой*/
		panel_main.add(panel_button);
		this.getContentPane().add(panel_main);
	}
	
	private void on_load_click(){
		debug("получить каталог");
		if(!this.getPercentReport().isInAction(this.field_function_get_directory,this.getRandomTailLength())){
			this.field_function_get_directory=this.replaceUniqueString(this.field_function_get_directory);
			(new RequestBoncardCatalog(this,
									   this.getPercentReport(),
									   this.field_function_get_directory,
									   "Получение каталога для сохранения файлов")
									   ).start(true);
			debug("получить файлы");
			if(!this.getPercentReport().isInAction(this.field_function_get_file,this.getRandomTailLength())){
				this.field_function_get_file=this.replaceUniqueString(this.field_function_get_file);
				(new RequestBoncard(this,
						            this.getPercentReport(),
						            this.field_function_get_file,
						            "Загрузить файлы с сервера")
									).start(false);
			}else{
				this.showWarningCollision(this);
			}
		}else{
			this.showWarningCollision(this);
		}
	}

	@Override
	public void getFile(String taskName, 
					    String pathName, 
					    String fileName,
					    byte[] data) {
		// отработать полное сохранение 
		try{
			/** полная строка пути к файлу  */
			String full_path="";
			pathName=pathName.trim();
			if(!pathName.endsWith(System.getProperty("file.separator"))){
				full_path=pathName+System.getProperty("file.separator")+fileName;
			}else{
				full_path=pathName+fileName;
			}
			debug("full_path:"+full_path);
			/*
			FileOutputStream fos=new FileOutputStream(full_path);
			ByteArrayInputStream bis=new ByteArrayInputStream(data);
			
			byte[] buffer=new byte[1024];
			int number_of_bytes=0;
			
			while((number_of_bytes=bis.read(buffer))!=(-1)){
				fos.write(buffer,0,number_of_bytes);
			}
			bis.close();
			fos.close();
			*/
			if(this.getFileFromZipBytes(data, full_path)==true){
				debug("unzip byte[] to file is ok "+full_path);
			}else{
				error("FrameStateGetFile get file error");
			}
		}catch(Exception ex){
			error("getFile Exception:"+ex.getMessage());
		}
	}

	@Override
	public void getFile(String taskName, 
						String fileName, 
						byte[] data) {
		debug("вызвать полный метод");
		this.getFile(taskName,
					 this.field_directory, 
					 fileName, 
					 data);
	}

	@Override
	public void getString(String taskName, String value) {
		if(taskName!=null){
			if(this.equalsStringHeader(taskName, this.field_function_get_directory)){
				debug("catalog is getted:"+value);
				this.field_directory=value;
			};
		}else{
			error("Task is null");
			getString(taskName);
		}
	}

	@Override
	public void getString(String taskName) {
		error("error in getting Path to Directory of Settings ");
		this.field_directory="c:\\";
	}
	
}
