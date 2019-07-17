package gui;
import java.awt.GridLayout;

import javax.swing.*;

import org.apache.log4j.BasicConfigurator;

public class EnterPoint extends JFrame{
	private static final long serialVersionUID=1L;
	/** рабочий стол для отображения всех событий*/
	private JDesktopPane field_desktop;
	
	public static void main(String[] args){
		BasicConfigurator.configure();
		new EnterPoint();
	}
	
	public EnterPoint(){
		super("Viewer");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(800,600);
		this.getContentPane().add(this.initComponent());
		try{
			//this.setExtendedState(MAXIMIZED_BOTH);
		}catch(Exception ex){};
		this.setVisible(true);
		CommonObject common_object=new CommonObject();
		new FrameMain(this.field_desktop,common_object);
	}
	
	private JPanel initComponent(){
		JPanel panel_main=new JPanel(new GridLayout(1,1));
		this.field_desktop=new JDesktopPane();
		panel_main.add(field_desktop);
		return panel_main;
	}
	
}
