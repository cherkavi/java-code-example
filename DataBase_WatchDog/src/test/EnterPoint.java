package test;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import database.connector.Connector;
import database.watchdog.WatchDog;

public class EnterPoint extends JFrame{
	
	/** */
	private static final long serialVersionUID = 1L;
	public EnterPoint(){
		super("Test");
		this.setSize(200,150);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.init();
		this.setVisible(true);
	}
	
	/** первоначальная инициализация */
	private void init(){
		JPanel panel_main=new JPanel();
		JButton button_1=new JButton("one");
		button_1.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				on_button_one_click();
			}
		});
		JButton button_2=new JButton("two");
		button_2.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				on_button_two_click();
			}
		});
		panel_main.add(button_1);
		panel_main.add(button_2);
		this.getContentPane().add(panel_main);
	}
	
	/** reaction on striking button one */
	private void on_button_one_click(){
		WatchDog.start(15, Connector.get_connection_to_firebird("", "D:\\parking.gdb", 0, "SYSDBA", "masterkey"), "POWER_CONTROLLER", "ID", "COUNTER_VALUE", "LAST_UPDATE");
	}
	/** reaction on striking button two */
	private void on_button_two_click(){
		
	}

	public static void main(String[] args){
		new EnterPoint();
	}
}
