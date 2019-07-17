package terminal_client.gui.windows;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import terminal.transfer.PercentReport;
import terminal_client.gui.utility.Access;
import terminal_client.gui.utility.JInternalFrameParent;
import terminal_client.gui.utility.ModalClose;

/** одно из дочерних окон */
public class FrameMain extends JInternalFrameParent{
	/** */
	private static final long serialVersionUID = 1L;
	
	public FrameMain(Access access,
					 JDesktopPane desktop,
					 ModalClose parent_element,
					 PercentReport percent_report){
		super(access, 
			  null,
			  desktop, 
			  parent_element,
			  percent_report, 
			  "Меню",
			  300,
			  200);
	}
	
	protected void initComponents(){
		// create component's
		JButton button_boncard=new JButton(this.getButtonStateCaption());
		JButton button_operation=new JButton(this.getButtonOperationCaption());
		JButton button_settings=new JButton(this.getButtonSettingsCaption());
		// add listener's
		button_boncard.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				on_button_state_click();
			}
		});
		button_operation.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				on_button_operation_click();
			}
		});
		button_settings.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				on_button_settings_click();
			}
		});
		
		
		JPanel panel_boncard=this.getTextPanelWithComponent(button_boncard, "");
		JPanel panel_operation=this.getTextPanelWithComponent(button_operation, "");
		JPanel panel_settings=this.getTextPanelWithComponent(button_settings, "");
		// placing
		JPanel panel_main=new JPanel();
		GroupLayout group_layout=new GroupLayout(panel_main);
		panel_main.setLayout(group_layout);
		GroupLayout.SequentialGroup group_layout_horizontal=group_layout.createSequentialGroup();
		GroupLayout.SequentialGroup group_layout_vertical=group_layout.createSequentialGroup();
		group_layout.setVerticalGroup(group_layout_vertical);
		group_layout.setHorizontalGroup(group_layout_horizontal);
		
		group_layout_vertical.addComponent(panel_boncard);
		group_layout_vertical.addComponent(panel_operation);
		group_layout_vertical.addComponent(panel_settings);
		
		group_layout_horizontal.addGroup(group_layout.createParallelGroup()
									     .addComponent(panel_boncard)
									     .addComponent(panel_operation)
									     .addComponent(panel_settings)
										 );
		this.getContentPane().add(panel_main);
	}


	
	/** reaction on striking button "State BonCard"*/
	private void on_button_state_click(){
		this.setVisible(false);
		new FrameState(this, this.getDesktop(),this,this.getPercentReport());
	}
	/** reaction on striking button "Operation"*/
	private void on_button_operation_click(){
		this.setVisible(false);
		new FrameOperation(this, this.getDesktop(),this,this.getPercentReport());
	}
	/** reaction on striking button "Settings"*/
	private void on_button_settings_click(){
		this.setVisible(false);
		new FrameSettings(this, this.getDesktop(),this,this.getPercentReport());
	}
	
	/** get button "State BonCard" caption*/
	private String getButtonStateCaption(){
		return  "Состояния БонКарт";
	}
	/** get button "Operation" caption*/
	private String getButtonOperationCaption(){
		return "Проведенные операции";
	}
	/** get button "Settings" caption*/
	private String getButtonSettingsCaption(){
		return "Настройки лояльности";
	}
}
