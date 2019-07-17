package gui;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import javax.swing.*;
import org.hibernate.*;

import DataBase.Table_one;
import Utility.HibernateUtil;

public class EnterPoint extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Logger field_logger=Logger.getLogger("gui.EnterPoint");
	{
		field_logger.setLevel(Level.DEBUG);
	}
	
	
	public EnterPoint(){
		super("Hibernate test");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(300,300);
		initComponent();
		initHibernate();
		this.setVisible(true);
	}
	
	private void initComponent(){
		// create component's
		JPanel panel_main=new JPanel(new BorderLayout());
		JButton field_button=new JButton("get text");
		// add listener's
		field_button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				on_button_click();
			}
		});
		// placing components
		panel_main.add(field_button,BorderLayout.NORTH);
		this.getContentPane().add(panel_main);
	}
	
	private void initHibernate(){
		Session session = HibernateUtil.getSessionFactory().openSession();
/*        Transaction tx = session.beginTransaction();
        Table_one record=new Table_one();
        Long msgId = (Long) session.save(record);
        tx.commit();
        session.close();
*/        
        // Second unit of work
        Session newSession = HibernateUtil.getSessionFactory().openSession();
        Transaction newTransaction = newSession.beginTransaction();
        //java.util.List results =newSession.createQuery("from Table_two").list();
        //System.out.println( results.size() + " message(s) found: <<<<<<<<<<<<<" );		
	}
	private void on_button_click(){
		field_logger.debug("on_button_click");
	}
	
	public void finalize(){
		HibernateUtil.shutdown();
	}
	
}
