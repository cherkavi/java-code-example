package terminal_client.gui.windows;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import terminal.transfer.PercentReport;
import terminal_client.gui.utility.JInternalFrameParent;
import terminal_client.gui.utility.ModalClose;

public class FrameState extends JInternalFrameParent{

	/** */
	private static final long serialVersionUID = 1L;

	public FrameState(JInternalFrameParent parent, JDesktopPane desktop,ModalClose parent_element,PercentReport percent_report){
		super(parent.getAccess(),parent, desktop, parent_element, percent_report, "Состояние БонКарт",300,150);
	}

	@Override
	protected void initComponents() {
		// create componen's
		JButton button_load=new JButton("Загрузка состояния БонКарт с сервера");
		JButton button_log=new JButton("Просмотр логов загрузок");
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
		placingComponents(panel_main,
				          new JPanel[]{ panel_boncard,      panel_operation,    panel_settings},
				          new String[]{"FrameStateGetFile","FrameStateLoadLog","FrameStateSettings"},
						  this.getGuiPath(),
						  this.getAccess()
						  );
		this.getContentPane().add(panel_main);
	}

	
	private void on_button_load_click(){
		this.setVisible(false);
		new FrameStateGetFile(this,this.getDesktopPane(),this,this.getPercentReport());
	}
	private void on_button_log_click(){
		this.setVisible(false);
		new FrameStateLoadLog(this,this.getDesktopPane(),this,this.getPercentReport());
	}
	private void on_button_settings_click(){
		this.setVisible(false);
		new FrameStateSettings(this,this.getDesktopPane(),this,this.getPercentReport());
	}
	
}
