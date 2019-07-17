package terminal_client.gui.windows;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import terminal.function.ResponseOperationLog;
import terminal.transfer.GetTask;
import terminal.transfer.PercentReport;
import terminal.transport.Task;
import terminal_client.gui.utility.JInternalFrameParent;
import terminal_client.gui.utility.ModalClose;
import terminal_client.gui.utility.TaskTableModel;

public class FrameOperationLoadLog extends JInternalFrameParent
							   implements GetTask{
	/** */
	private static final long serialVersionUID = 1L;
	/** таблица логов */
	private JTable field_table_log;
	/** уникальный идентификатор функции */
	private String field_function_get_log=this.addUniqueString("ResponseOperationLog"); 
	
	public FrameOperationLoadLog(JInternalFrameParent parent, JDesktopPane desktop, 
							 ModalClose parent_element,PercentReport percent_report) {
		super(parent.getAccess(), 
			  parent, 
			  desktop, 
			  parent_element, 
			  percent_report, 
			  "Загрузка лога состояний", 
			  300, 
			  300,
			  true,
			  true,
			  true,
			  false);
	}

	@Override
	protected void initComponents() {
		// create component's
		JButton button_load_log=new JButton("Загрузить лог Полученных сервером файлов");
		field_table_log=new JTable();
		field_table_log.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		final JPopupMenu field_popup_menu=new JPopupMenu();
		JMenuItem menu_load=new JMenuItem("Load file");
		field_popup_menu.add(menu_load);
		// add listener's
		button_load_log.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				on_load_log_click();
			}
		});
		field_table_log.addMouseListener(new MouseListener(){
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(arg0.getButton()==MouseEvent.BUTTON3){
					on_table_show_popup(field_popup_menu,arg0.getX(), arg0.getY());
				}
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {
				
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
				
			}
			@Override
			public void mousePressed(MouseEvent arg0) {
				
			}
			@Override
			public void mouseReleased(MouseEvent arg0) {
				
			}
		});
		// placing component's
		/** панель расположений*/
		JPanel panel_main=new JPanel();
		panel_main.setLayout(new BorderLayout());
		
		/** панель управления загрузкой*/
		JPanel panel_manager=this.getTextPanelWithComponent(button_load_log, "");
		panel_main.add(panel_manager,BorderLayout.NORTH);
		panel_main.add(field_table_log);
		
		this.getContentPane().add(panel_main);
	}
	
	/** показать JOptionPane на компоненте, если он предусмотрен */
	private void on_table_show_popup(JPopupMenu menu,int x_position, int y_position){
/*		if(this.field_table_log.getSelectedRow()>=0){
			menu.show(this.field_table_log, x_position, y_position);
		}
*/		
	}
	
	/** загрузить лог с сервера */
	private void on_load_log_click(){
		if(!this.getPercentReport().isInAction(this.field_function_get_log, this.getRandomTailLength())){
			this.field_function_get_log=this.replaceUniqueString(this.field_function_get_log);
			(new ResponseOperationLog(this,
									  this.getPercentReport(),
									  "FrameStateLoadLog",
									  "Загрузка лога \"Полученных сервером файлов \" ")
									  ).start(true);
		}else{
			this.showWarningCollision(this);
		}
	}

	/** место обработки полученного Task*/
	@Override
	public void getTask(Task task) {
		if(task!=null){
			if(task.getDataCount()==0){
				debug("log is empty");
				this.setTableModelEmpty();
			}else{
				debug("log consist "+task.getDataCount());
				this.setTableModelFromTask(task);
			}
		}else{
			this.setTableModelEmpty();
			error("Task is null");
			JOptionPane.showMessageDialog(this, "Лог не получен");
		}
	}
	
	/** наполнить таблицу логов пустыми значениями */
	private void setTableModelEmpty(){
		this.field_table_log.setModel(new DefaultTableModel());
	}
	
	/** наполнить таблицу логов из Task*/
	private void setTableModelFromTask(Task task){
		 this.field_table_log.setModel(new TaskTableModel(task));
	}
}
