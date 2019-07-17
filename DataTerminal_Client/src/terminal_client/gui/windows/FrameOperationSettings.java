package terminal_client.gui.windows;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import terminal.transfer.GetString;
import terminal.transfer.PercentReport;
import terminal_client.gui.utility.JInternalFrameParent;
import terminal_client.gui.utility.ModalClose;
import terminal.function.*;

public class FrameOperationSettings extends JInternalFrameParent
								    implements GetString{
	/** каталог для хранения операций, которые были отправлены на сервер*/
	private JTextField field_path;
	/** уникальный идентификатор для задачи запроса каталога с сервера */
	private String field_function_catalog_get;
	/** уникальный идентификатор для задачи установки каталога с сервера */
	private String field_function_catalog_set="ResponseOperationCatalog";
	
	public FrameOperationSettings(JInternalFrameParent parent, 
								  JDesktopPane desktop, 
							      ModalClose parent_element,
							      PercentReport percent_report) {
		super(parent.getAccess(),
			  parent, 
			  desktop, 
			  parent_element,
			  percent_report, 
			  "Установка настроек для выгрузки на сервер", 
			  400, 
			  140);
	}

	/** */
	private static final long serialVersionUID = 1L;

	@Override
	protected void initComponents() {
		// create component's
		debug("запрос каталогов файла от сервера");
		field_path=new JTextField();
		field_path.setEditable(false);
		if(this.field_function_catalog_get==null){
			this.field_function_catalog_get="RequestOperationCatalog";
		}
		this.field_function_catalog_get=this.replaceUniqueString(this.field_function_catalog_get);
		(new RequestOperationCatalog(this,
									 this.getPercentReport(),
									 this.field_function_catalog_get,
									 "Получение каталога \"Файлы на сервер\""
									 )
		).start(false);
		JPanel panel_text_path=this.getTextPanelWithComponent(field_path, "Путь к каталогу с файлами для выгрузки на сервер");
		JButton field_button_set_directory=new JButton("Установить каталог");
		JButton field_button_show_dialog=new JButton("...");
		// add listener's
		field_button_set_directory.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				on_button_set_directory_click();
			}
		});
		field_button_show_dialog.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				on_button_show_dialog();
			}
		});
		// placing
		JPanel panel_main=new JPanel(new GridLayout(2,1));
		JPanel panel_path=new JPanel(new BorderLayout());
		panel_path.add(panel_text_path, BorderLayout.CENTER);
		panel_path.add(field_button_show_dialog,BorderLayout.EAST);
		
		panel_main.add(panel_path);
		panel_main.add(field_button_set_directory);
		this.getContentPane().add(panel_main);
	}

	/** reaction on striking button set directory */
	private void on_button_set_directory_click(){
		if(!this.getPercentReport().isInAction(this.field_function_catalog_set,this.getRandomTailLength())){
			this.field_function_catalog_set=this.replaceUniqueString(this.field_function_catalog_set);
			(new ResponseOperationCatalog(this.field_path.getText(),
										  this.getPercentReport(),
										  this.field_function_catalog_set,
										  "Установить каталог выгрузки файлов на сервер")
										 ).start(false); 
		}else{
			this.showWarningCollision(this);
		}
	}
	
	/** reaction on striking button show dialog */
	private void on_button_show_dialog(){
		JFileChooser dialog=new JFileChooser();
		dialog.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		if(dialog.showDialog(this, "Каталог для загрузки")==JFileChooser.APPROVE_OPTION){
			this.field_path.setText(dialog.getSelectedFile().getPath());
		}
	}

	/** reaction when Thread return positive result */
	@Override
	public void getString(String taskName, String value) {
		if(taskName!=null){
			if(this.equalsStringHeader(taskName, this.field_function_catalog_get)){
				this.field_path.setText(value);
			};
			if(this.equalsStringHeader(taskName, field_function_catalog_set)){
				debug("catalog is set :"+value);
			};
		}else{
			error("positive result, taskName is null");
		}
	}

	/** reaction when Thread return negative result */
	@Override
	public void getString(String taskName) {
		error("Task is ERROR:"+taskName);
	}
	
}
