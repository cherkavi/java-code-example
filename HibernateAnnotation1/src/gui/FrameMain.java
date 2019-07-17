package gui;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import database.HibernateFirebirdConnect;
import database.TableOne;

public class FrameMain extends JFrame{
	/** */
	private static final long serialVersionUID = 1L;
	private Logger field_logger=Logger.getLogger("gui.FrameMain");
	{
		field_logger.setLevel(Level.DEBUG);
	}
	private PanelTableOne field_panel_table;
	private HibernateFirebirdConnect field_connect;
	
	private void debug(String information){
		field_logger.debug(information);
	}
	
	private void error(String information){
		field_logger.error(information);
	}
	
	
	public FrameMain(){		
		super("Hibernate Example");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(300,300);
		
		initComponents();
		this.setVisible(true);
	}
	
	/** инициализация компонентов */
	private void initComponents(){
		debug("initComponents:begin");
		field_connect=new HibernateFirebirdConnect("c:/temp_hibernate.gdb","SYSDBA","masterkey",0);
		// создать компоненты
		JButton button_read=new JButton("read from database");
		JButton button_write=new JButton("write to database");
		this.field_panel_table=new PanelTableOne();
		// назначить слушателей
		button_read.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				on_button_read_click();
			}
		});
		button_write.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				on_button_write_click();
			}
		});
		// расставить компоненты
		JPanel panel_manager=new JPanel(new GridLayout(2,1));
		panel_manager.add(button_read);
		panel_manager.add(button_write);
		
		JPanel panel_main=new JPanel(new BorderLayout());
		panel_main.add(field_panel_table,BorderLayout.CENTER);
		panel_main.add(panel_manager,BorderLayout.SOUTH);
		this.getContentPane().add(panel_main);
		debug("initComponents:end");
	}
	
	
	/** reaction on striking button connect */
	private void on_button_read_click(){
		debug("button_read:begin");
		try{
			Session session=field_connect.getSession();
            TableOne table_one=(TableOne)session.get(database.TableOne.class, new Integer(2));
            this.field_panel_table.setTableOne(table_one);
            System.out.println("table_one.getField_string()"+table_one.getField_string()+"   table_one.getField_date():"+table_one.getField_timestamp());
			session.close();
		}catch(Exception ex){
			error("IOException: "+ex.getMessage());
		}
		debug("button_read:end");
	}
	
	private void on_button_write_click(){
		debug("button_write:begin");
		try{
			HibernateFirebirdConnect connect=new HibernateFirebirdConnect("c:/temp_hibernate.gdb","SYSDBA","masterkey",0);
			Session session=connect.getSession();
			session.beginTransaction();
			session.saveOrUpdate(this.field_panel_table.getTableOne());
			session.getTransaction().commit();
			session.close();
		}catch(Exception ex){
			error("IOException: "+ex.getMessage());
		}
		debug("button_write:end");
	}
}
