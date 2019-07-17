package gui;

import gui.Utility.JInternalFrameParent;
import gui.View.FrameView;

import java.awt.GridLayout;
import java.awt.event.*;
import javax.swing.*;

import org.apache.log4j.Logger;



public class FrameMain extends JInternalFrameParent{
	private static final long serialVersionUID=1L;
	private Logger logger=Logger.getLogger(this.getClass());
	
	public FrameMain(JDesktopPane desktop,CommonObject common_object){
		super(desktop,common_object, "Main", 200, 150);
		initComponents();
	}
	
	/** return JPanel with component's */
	protected void initComponents(){
		// create component's
		JPanel panel_main=new JPanel();
		JButton button_open=new JButton("Open");
		JButton button_settings=new JButton("Settings");
		
		// add listener's
		button_open.addActionListener(new ActionListener(){
			private static final long serialVersionUID=1L;
			public void actionPerformed(ActionEvent e){
				onButtonOpen();
			}
		});
		button_settings.addActionListener(new ActionListener(){
			private static final long serialVersionUID=1L;
			public void actionPerformed(ActionEvent e){
				onButtonSettings();
			}
		});
		// placing
		/*GroupLayout group_layout=new GroupLayout(panel_main);
		panel_main.setLayout(group_layout);
		GroupLayout.SequentialGroup group_layout_vertical=group_layout.createSequentialGroup();
		GroupLayout.SequentialGroup group_layout_horizontal=group_layout.createSequentialGroup();
		group_layout.setVerticalGroup(group_layout_vertical);
		group_layout.setHorizontalGroup(group_layout_horizontal);
		group_layout_horizontal.addGroup(group_layout.createParallelGroup()
									     .addComponent(button_open,GroupLayout.Alignment.CENTER)
			     						 .addComponent(button_settings,GroupLayout.Alignment.CENTER));
		group_layout_vertical.addComponent(button_open);
		group_layout_vertical.addComponent(button_settings);
		*/
		panel_main.setLayout(new GridLayout(2,1));
		panel_main.add(button_open);
		panel_main.add(button_settings);
		this.getContentPane().add(panel_main);
		this.revalidate();
	}

	/** reaction on striking button open viewer*/
	private void onButtonOpen(){
		new FrameView(this);
	}
	
	/** reaction on striking button open settings*/
	private void onButtonSettings(){
		logger.debug("onButtonSettings");
	}
	
	
}
