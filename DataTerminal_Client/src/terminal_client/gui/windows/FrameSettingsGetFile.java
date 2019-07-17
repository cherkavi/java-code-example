package terminal_client.gui.windows;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import terminal.function.RequestSettings;
import terminal.function.RequestSettingsCatalog;
import terminal.transfer.GetFile;
import terminal.transfer.GetString;
import terminal.transfer.PercentReport;
import terminal_client.gui.utility.JInternalFrameParent;
import terminal_client.gui.utility.ModalClose;

public class FrameSettingsGetFile extends JInternalFrameParent
								  implements GetFile,GetString{
	/** */
	private static final long serialVersionUID = 1L;

	private String field_function_get_file=this.addUniqueString("FrameSettingsGetFile");
	private String field_function_get_directory=this.addUniqueString("FrameSettingsGetDirectory");
	private String field_path_to_directory="";
	
	/** Фрейм, содержащий реализацию интерфейса получения файлов: */
	public FrameSettingsGetFile(JInternalFrameParent parent, 
							    JDesktopPane desktop, 
							    ModalClose parent_element,
							    PercentReport percent_report) {
		super(parent.getAccess(),
			  parent, desktop, 
			  parent_element, 
			  percent_report, 
			  "Загрузить настройки для системы", 
			  300, 
			  100);
	}

	@Override
	protected void initComponents() {
		// create component's
		JButton button_load=new JButton("Проверить/загрузить настройки системы");
		// add listener's
		button_load.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				on_load_click();
			}
		});
		// placing component's
		/** панель расположений*/
		JPanel panel_main=new JPanel();
		panel_main.setLayout(new BorderLayout());
		
		/** панель управления загрузкой*/
		panel_main.add(button_load);
		this.getContentPane().add(panel_main);
	}
	
	private void on_load_click(){
		if(!this.getPercentReport().isInAction(this.field_function_get_file, this.getRandomTailLength())){
			this.field_function_get_file=this.replaceUniqueString(this.field_function_get_file);
			(new RequestSettings(this,
								this.getPercentReport(),
					            this.field_function_get_file,
					            "Получение файла настроек для системы")).start(false);
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
			if(this.getFileFromZipBytes(data, full_path)==true){
				debug("Unzip OK:"+full_path);
			}else{
				error("UnzipError:");
			}
			
		}catch(Exception ex){
			error("getFile Exception:"+ex.getMessage());
		}
	}

	@Override
	public void getFile(String taskName, 
						String fileName, 
						byte[] data) {
		// получить путь к каталогу для сохранения данных
		this.field_function_get_directory=this.replaceUniqueString(this.field_function_get_directory);
		(new RequestSettingsCatalog(this,
									this.getPercentReport(),
									this.field_function_get_directory,
									"Получение каталога настроек ")
		).start(true);
		// вызвать полный метод
		this.field_function_get_file=this.replaceUniqueString(this.field_function_get_file);
		this.getFile(taskName,
					 this.field_path_to_directory, 
					 fileName, 
					 data);
	}

	@Override
	public void getString(String taskName, String value) {
		if(taskName!=null){
			if(this.equalsStringHeader(taskName, this.field_function_get_directory)){
				this.field_path_to_directory=value;
			};
		}else{
			getString(taskName);
		}
	}

	@Override
	public void getString(String taskName) {
		error("error in getting Path to Directory of Settings ");
		this.field_path_to_directory="c:\\";
	}

}
