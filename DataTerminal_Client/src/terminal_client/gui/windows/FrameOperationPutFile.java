package terminal_client.gui.windows;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.*;

import terminal.function.RequestOperationCatalog;
import terminal.function.ResponseOperation;
import terminal.transfer.GetString;
import terminal.transfer.PercentReport;
import terminal_client.gui.utility.JInternalFrameParent;
import terminal_client.gui.utility.ModalClose;

public class FrameOperationPutFile extends JInternalFrameParent implements GetString{
	/** */
	private static final long serialVersionUID = 1L;
	private JTextField field_current_path;
	private String field_path=null;
	private String field_function_get_catalog=this.addUniqueString("RequestOperationCatalog");
	private String field_function_get_files=this.addUniqueString("ResponseOperation");
	
	public FrameOperationPutFile(JInternalFrameParent parent, JDesktopPane desktop, 
							 ModalClose parent_element,
							 PercentReport percent_report) {
		super(parent.getAccess(),
			  parent, 
			  desktop, 
			  parent_element, 
			  percent_report, 
			  "Выгрузить файлы на сервер", 
			  300, 
			  115);
	}

	@Override
	protected void initComponents() {
		// create component's
		JLabel label_path=new JLabel("Имя загружаемого файла:");
		field_current_path=new JTextField("");
		field_current_path.setEditable(false);
		JButton button_show_dialog=new JButton("...");
		JButton button_send=new JButton("Выгрузить указанный файл");
		// add listener's
		button_show_dialog.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				on_show_dialog_click();
			}
		});
		button_send.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				on_send_click();
			}
		});
		// placing component's
		/** панель расположений*/
		JPanel panel_main=new JPanel();
		panel_main.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
		GroupLayout group_layout=new GroupLayout(panel_main);
		panel_main.setLayout(group_layout);
		GroupLayout.SequentialGroup group_layout_horizontal=group_layout.createSequentialGroup();
		GroupLayout.SequentialGroup group_layout_vertical=group_layout.createSequentialGroup();
		group_layout.setVerticalGroup(group_layout_vertical);
		group_layout.setHorizontalGroup(group_layout_horizontal);
		
		group_layout_horizontal.addGroup(group_layout.createParallelGroup()
										 .addGroup(group_layout.createSequentialGroup()
												 .addGroup(group_layout.createParallelGroup()
														 .addComponent(label_path,GroupLayout.Alignment.CENTER)
														 .addComponent(field_current_path)
												 			)
												 .addComponent(button_show_dialog)
										 	     )
										 .addComponent(button_send,GroupLayout.DEFAULT_SIZE,Short.MAX_VALUE, Short.MAX_VALUE)
										 //.addComponent(panel_log)
		  								);
		
		group_layout_vertical.addGroup(group_layout.createParallelGroup()
				 						.addGroup(group_layout.createSequentialGroup()
				 								.addComponent(label_path)
						     				    .addComponent(field_current_path,GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE)
				 						)
			                           .addComponent(button_show_dialog)
			                           );
		group_layout_vertical.addGap(5);
		group_layout_vertical.addComponent(button_send);
		//group_layout_vertical.addComponent(panel_log);
		
		/** панель управления загрузкой*/
		this.getContentPane().add(panel_main);
	}
	
	private void on_show_dialog_click(){
		if(this.field_path==null){
			this.field_function_get_catalog=this.replaceUniqueString(this.field_function_get_catalog);
			(new RequestOperationCatalog(this,
										 this.getPercentReport(),
										 this.field_function_get_catalog,
										 "Запрос каталога")
									     ).start(true);
		}
		JFileChooser file_chooser=new JFileChooser();
		file_chooser.setCurrentDirectory(new File(this.field_path));
		if(JFileChooser.APPROVE_OPTION==file_chooser.showDialog(this, "Файл для отправки")){
			this.field_current_path.setText(file_chooser.getSelectedFile().getAbsolutePath());
		}
	}
	
	private void on_send_click(){
		if(!this.getPercentReport().isInAction(this.field_function_get_files, this.getRandomTailLength())){
			this.field_function_get_files=this.replaceUniqueString(this.field_function_get_files);
			(new ResponseOperation(this.field_current_path.getText(),
								   this.getPercentReport(),
								   this.field_function_get_files,
								   "Посылка файла на сервер")
								  ).start(false);
		}else{
			this.showWarningCollision(this);
		}
	}

	@Override
	public void getString(String taskName, String value) {
		if(taskName!=null){
			if(this.equalsStringHeader(taskName, this.field_function_get_catalog)){
				this.field_path=value;
			}
		}else{
			error("unique id is null ");
			getString(taskName);
		}
	}

	@Override
	public void getString(String taskName) {
		this.field_path="c:\\";
		error("result is null ");
	}

}
