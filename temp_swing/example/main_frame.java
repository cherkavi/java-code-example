package example;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
//import java.awt.*;

public class main_frame extends JFrame{
	/**
	 * this is entrance point to progr
	 */
	private static final long serialVersionUID = 1L;

	public main_frame(){
		super("test");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(200,300);
		this.create_element();
		this.setVisible(true);
	}
	
	private void create_element(){
		JPanel panel_main=new JPanel();
		panel_main.setLayout(new BorderLayout());
		JButton button_first=new JButton("first_button");
		button_first.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("clicked to button");
			}
		});
		panel_main.add(button_first,BorderLayout.SOUTH);
		this.getContentPane().add(panel_main);
	}

}
