package terminal_client.gui.windows;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/** одно из дочерних окон */
public class JDialogMain_Child extends JDialog{
	/** */
	private static final long serialVersionUID = 1L;
	private JDialog field_parent_frame;
	
	public JDialogMain_Child(JDialog parent_frame){
		super(parent_frame, "Дочернее окно",true);
		this.setSize(300,200);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		initComponents();
		this.setVisible(true);
	}
	
	private void initComponents(){
		// create component's
		JButton button_open=new JButton("open Modal");
		// add listener's
		button_open.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				on_button_load_click();
			}
		});
		// placing
		JPanel panel_main=new JPanel();
		panel_main.setLayout(new BorderLayout());
		this.getContentPane().add(panel_main,BorderLayout.NORTH);
	}
	
	
	private void on_button_load_click(){
		
	}

}
