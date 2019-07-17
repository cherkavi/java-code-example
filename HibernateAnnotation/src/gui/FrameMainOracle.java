package gui;

import javax.swing.*;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import database.oracle.HibernateOracleConnect;

import java.awt.BorderLayout;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.ResultSet;

public class FrameMainOracle extends JFrame {
	/** */
	private static final long serialVersionUID = 1L;
	private Logger field_logger=Logger.getLogger(this.getClass());
	{
		field_logger.setLevel(Level.DEBUG);
	}
	
	private JTextArea field_area;
	private HibernateOracleConnect field_connection;
	
	public FrameMainOracle(){
		super("Oracle connection");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(300,300);
		this.getContentPane().add(initComponents());
		this.setVisible(true);
	}
	
	private JPanel initComponents(){
		JPanel return_value=new JPanel();
		return_value.setLayout(new BorderLayout());
		this.field_connection=new HibernateOracleConnect("jdbc:oracle:thin:@192.168.15.254:1521:demo",
														 "bc_reports",
														 "bc_reports",
														 1);
		
		field_area=new JTextArea(); 
		JButton button=new JButton("ADD");
		
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				on_button_click();
			}
		});
		
		return_value.add(button,BorderLayout.SOUTH);
		return_value.add(field_area, BorderLayout.CENTER);
		return return_value;
	}
	
	/** reaction on striking button */
	private void on_button_click(){
		
		Session session=field_connection.getSession();
		
/*		{
			TablePurchasesInt table=new TablePurchasesInt();
			table.setField_id_file(new Integer(20));
			table.setField_id_term(new Integer(21));
			session.beginTransaction();
			session.saveOrUpdate(table);
			session.getTransaction().commit();
		}
*/		
		{
			
			try{
				//TablePurchasesInt field=null;
				//field=(TablePurchasesInt)session.load(database.oracle.TablePurchasesInt.class, new Integer(101));
				//Query query=session.createSQLQuery("select * from F_DT_PURCHASES_INT ");
				//List list=(List)query.list();
				
				//???Query query=session.createQuery("from TablePurchasesInt as table");
				
				Connection connection=session.connection();
				connection.setAutoCommit(false);
				ResultSet rs=connection.createStatement().executeQuery("select * from F_DT_PURCHASES_INT ");
				if(rs.next()){
					field_logger.debug("rs has next:"+rs.getString(1));
				}
				connection.close();
				
				// session not close, when "connection.close()"
				connection=session.connection();
				connection.setAutoCommit(false);
				rs=connection.createStatement().executeQuery("select * from F_DT_PURCHASES_INT ");
				if(rs.next()){
					field_logger.debug("rs has next:"+rs.getString(1));
				}
				connection.close();
				
				//field_logger.debug(" WIN WIN WIN OK   file:"+field.getField_id_file()+"   term:"+field.getField_id_term());
				field_logger.debug(" WIN WIN WIN OK   ");
				
			}catch(Exception ex){
				field_logger.error("read data Error:"+ex.getMessage());
			}
			
		}
		session.close();
	}
}
