package gui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import database.TableOne;

public class FrameMain extends JFrame{
	/** */
	private static final long serialVersionUID = 1L;
	private Logger field_logger=Logger.getLogger("gui.FrameMain");
	{
		field_logger.setLevel(Level.DEBUG);
	}
	
	private void debug(String information){
		field_logger.debug(information);
	}
	
	private void error(String information){
		field_logger.error(information);
	}
	
	
	public FrameMain(){		
		super("Hibernate Example");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(300,200);
		
		initComponents();
		this.setVisible(true);
		
	}
	
	/** инициализация компонентов */
	private void initComponents(){
		debug("initComponents:begin");
		// создать компоненты
		JButton button_connect=new JButton("connect to database");
		// назначить слушателей
		button_connect.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				on_button_connect_click();
			}
		});
		// расставить компоненты
		JPanel panel_main=new JPanel();
		panel_main.add(button_connect);
		this.getContentPane().add(panel_main);
		debug("initComponents:end");
	}
	
	
	/** reaction on striking button connect */
	private void on_button_connect_click(){
		debug("button_connect:begin");
		try{
			Configuration config=new Configuration();
			config.configure("hibernate.cfg.xml");
			SessionFactory sessions=config.buildSessionFactory();
			Session session=sessions.openSession();
			TableOne table_one=new TableOne();
			table_one.setField_double(5);
			table_one.setField_string("hello");
			session.beginTransaction();
			session.save(table_one);
			session.getTransaction().commit();
			session.close();
			sessions.close();
		}catch(Exception ex){
			error("IOException: "+ex.getMessage());
		}
		
		debug("button_connect:end");
	}
}
