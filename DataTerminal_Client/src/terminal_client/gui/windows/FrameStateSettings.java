package terminal_client.gui.windows;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import terminal.function.RequestBoncardCatalog;
import terminal.function.ResponseBoncardCatalog;
import terminal.transfer.GetString;
import terminal.transfer.PercentReport;
import terminal_client.gui.utility.JInternalFrameParent;
import terminal_client.gui.utility.ModalClose;

public class FrameStateSettings extends JInternalFrameParent implements GetString{
	/** каталог для получения настроек для всей системы*/
	private JTextField field_path;
	
	/** уникальное имя выполняемой/выполненной Function - получить каталог */
	private String field_function_directory_get;
	/** уникальное имя выполняемой/выполненной Function - установить каталог */
	private String field_function_directory_set=this.addUniqueString("ResponseBoncardCatalog");
	

	public FrameStateSettings(JInternalFrameParent parent, JDesktopPane desktop, 
							  ModalClose parent_element,PercentReport percent_report) {
		super(parent.getAccess(),parent, desktop, parent_element, percent_report,"Установка настроек", 400, 140);
	}

	/** */
	private static final long serialVersionUID = 1L;

	@Override
	protected void initComponents() {
		// create component's
		field_path=new JTextField();
		// передать на сервер запрос на получение каталога
		if(this.field_function_directory_get==null){
			this.field_function_directory_get=this.addUniqueString(this.addUniqueString("RequestBoncardCatalog"));
		}
		this.field_function_directory_get=this.replaceUniqueString(this.field_function_directory_get);
		(new RequestBoncardCatalog(this,
								   this.getPercentReport(),
								   this.field_function_directory_get,
								   "Получить каталог загрузки БонКарт")
								   ).start(false);		
		field_path.setEditable(false);
		/*
		try{
			Thread.sleep(2000);
		}catch(Exception ex){};
		*/
		
		JPanel panel_text_path=this.getTextPanelWithComponent(field_path, "Путь к каталогу с файлами для загрузки от сервера");
		JButton field_button_set_directory=new JButton("Установить каталог");
		JButton field_button_show_dialog=new JButton("...");
		JPanel panel_show_dialog=this.getTextPanelWithComponent(field_button_show_dialog, "");
		// add listener's
		field_button_set_directory.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				on_button_set_directory_click();
				debug("set_directory");
			}
		});
		field_button_show_dialog.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				on_button_show_dialog();
			}
		});
		// placing
		JPanel panel_main=new JPanel(new GridLayout(2,1));
		JPanel panel_path=new JPanel(new BorderLayout());
		panel_path.add(panel_text_path, BorderLayout.CENTER);
		panel_path.add(panel_show_dialog,BorderLayout.EAST);
		
		panel_main.add(panel_path);
		panel_main.add(field_button_set_directory);
		this.getContentPane().add(panel_main);
	}

	/** reaction on striking button set directory */
	private void on_button_set_directory_click(){
		if(!this.getPercentReport().isInAction(this.field_function_directory_set,this.getRandomTailLength())){
			this.field_function_directory_set=this.replaceUniqueString(this.field_function_directory_set);
			(new ResponseBoncardCatalog(this.field_path.getText(),
				                       this.getPercentReport(),
					                   this.field_function_directory_set,
					                   "Установка каталога загрузки БонКарт")).start(false);
		}else{
			this.showWarningCollision(this);
		}
	}
	/** reaction on striking button show dialog for set catalog */
	private void on_button_show_dialog(){
		JFileChooser dialog=new JFileChooser();
		dialog.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		if(dialog.showDialog(this, "Каталог для загрузки")==JFileChooser.APPROVE_OPTION){
			this.field_path.setText(dialog.getSelectedFile().getPath());
		}
	}
	
	/** получить ответ от потока выполнения */
	@Override
	public void getString(String taskName, String value) {
		debug("getString:begin");
		debug("taskName:"+taskName);
		if((taskName!=null)&&(taskName.startsWith("RequestBoncardCatalog"))){
			debug("getString:value="+value);
			this.field_path.setText(value);
		}
		debug("getString:end");
	}

	@Override
	public void getString(String taskName) {
		JOptionPane.showMessageDialog(this, "Ошибка при получении данных");
	}
}
