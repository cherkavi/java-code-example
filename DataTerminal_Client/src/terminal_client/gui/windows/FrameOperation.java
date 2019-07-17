package terminal_client.gui.windows;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import terminal.transfer.PercentReport;
import terminal_client.gui.utility.JInternalFrameParent;
import terminal_client.gui.utility.ModalClose;

public class FrameOperation extends JInternalFrameParent{

	/** */
	private static final long serialVersionUID = 1L;

	public FrameOperation(JInternalFrameParent parent,JDesktopPane desktop,ModalClose parent_element,PercentReport percent_report){
		super(parent.getAccess(), parent,desktop, parent_element,percent_report, "Операции с БонКартами",250,150);
	}

	@Override
	protected void initComponents() {
		// create componen's
		JButton button_load=new JButton("Выгрузка операций на сервер");
		JButton button_log=new JButton("Просмотр логов Выгрузок");
		JButton button_settings=new JButton("Системные опции");
		
		// add listener's
		button_load.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				on_button_load_click();
			}
		});
		button_log.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				on_button_log_click();
			}
		});
		button_settings.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				on_button_settings_click();
			}
		});
		
		// placing component's
		JPanel panel_boncard=this.getTextPanelWithComponent(button_load, "");
		JPanel panel_operation=this.getTextPanelWithComponent(button_log, "");
		JPanel panel_settings=this.getTextPanelWithComponent(button_settings, "");
		// placing
		JPanel panel_main=new JPanel();
		this.placingComponents(panel_main, 
				               new JPanel[]{panel_boncard, panel_operation, panel_settings}, 
				               new String[]{"FrameOperationPutFile","FrameOperationLoadLog","FrameOperationSettings"}, 
				               this.getGuiPath(), 
				               getAccess());
		
		this.getContentPane().add(panel_main);
	}
	
	private void on_button_load_click(){
		this.setVisible(false);
		new FrameOperationPutFile(this,this.getDesktop(),this,this.getPercentReport());
	}
	private void on_button_log_click(){
		this.setVisible(false);
		new FrameOperationLoadLog(this,this.getDesktop(),this,this.getPercentReport());
	}
	private void on_button_settings_click(){
		this.setVisible(false);
		new FrameOperationSettings(this,this.getDesktop(),this,this.getPercentReport());
	}
	
}
