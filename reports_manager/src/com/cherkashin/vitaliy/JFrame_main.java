package com.cherkashin.vitaliy;
import javax.swing.*;

import com.cherkashin.vitaliy.frame.ModalClose;

public class JFrame_main extends JFrame implements ModalClose{
	/** desktop */
	private JDesktopPane field_desktop;
	int width=800;
	int height=600;
	
	public JFrame_main(){
		super("test connection");
		this.setBounds(50,50,width,height);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.initComponents();
	}
	
	private void initComponents(){
		this.field_desktop=new JDesktopPane();
		this.getContentPane().add(this.field_desktop);
		new JInternalFrame_main(this.field_desktop,((ModalClose)this),400,200);
		
	}
	
	public static void main(String[] args){
		new JFrame_main();
	}

	@Override
	public void close(int return_value) {
		System.exit(0);
	}

}
