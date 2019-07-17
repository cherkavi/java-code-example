package com.cherkashin.vitaliy;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import BonCard.JInternalFrame_Reports;

import com.cherkashin.vitaliy.frame.ModalClose;
import com.cherkashin.vitaliy.frame.Position;

import java.sql.*;

/** 
 * фрейм, который отображает текущие параметры подключения к базе данных <br>
 * Path, Login, Password
 * @author cherkashinv
 */
public class JInternalFrame_main extends JInternalFrame implements InternalFrameListener,ModalClose{
	/** logger для данного фрейма*/
	private static Logger field_logger=Logger.getLogger("JInternalFrame_main");
	static{
		org.apache.log4j.BasicConfigurator.configure();
		field_logger.setLevel(Level.DEBUG);
	}
	/** текущее соединение с базой данных */
	private Connection field_connection;
	/** рабочий стол, на котором находятся все JInternalFrame */
	private JDesktopPane field_desktop;
	/** ссылка на родительское окно */
	private ModalClose field_parent;
	JLabel label_path_connection;
	JTextField jtextfield_path_connection;
	JLabel label_login;
	JTextField jtextfield_login;
	JLabel label_password;
	JTextField jtextfield_password;
	JButton button_test_connection;
	JButton button_connection;

	/** child window for show Reports*/
	JInternalFrame_Reports field_child;
	/**
	 * 
	 * @param desktop - рабочий стол
	 * @param parent - ссылка на родительское окно
	 */
	public JInternalFrame_main(JDesktopPane desktop,
							    ModalClose parent){
		this(desktop,parent,150,100);
	}
	
	/**
	 * 
	 * @param desktop - рабочий стол
	 * @param parent - ссылка на родительское окно
	 * @param width - ширина
	 * @param height - высота
	 */
	public JInternalFrame_main(JDesktopPane desktop,
							    ModalClose parent,
							    int width,
							    int height){
		super("internal frame",false,true,false,false);
		this.field_desktop=desktop;
		this.field_parent=parent;
		this.field_desktop.add(this);
		initComponents();
		this.setVisible(true);
		System.out.println("Desktop width:"+this.field_desktop.getWidth()+"Desktop height:"+this.field_desktop.getHeight());
		//this.setBounds(10,10,this.field_desktop.getWidth()-20,this.field_desktop.getHeight()-20);
		this.addInternalFrameListener(this);
		Position.set_frame_by_dimension(this, field_desktop, width, height);
	}
	/** инициализация компонентов */
	private void initComponents(){
		label_path_connection=new JLabel("Path");
		jtextfield_path_connection=new JTextField(this.getPath());
		label_login=new JLabel("Login");
		jtextfield_login=new JTextField(this.getLogin());
		label_password=new JLabel("Password");
		jtextfield_password=new JTextField(this.getPassword());
		button_test_connection=new JButton("TEST");
		button_connection=new JButton("Connection");
		JPanel panel_main=new JPanel();
		GroupLayout group_layout=new GroupLayout(panel_main);
		panel_main.setLayout(group_layout);
		GroupLayout.SequentialGroup group_layout_horizontal=group_layout.createSequentialGroup();
		GroupLayout.SequentialGroup group_layout_vertical=group_layout.createSequentialGroup();
		group_layout_horizontal.addGroup(group_layout.createParallelGroup()
										 .addComponent(label_path_connection,GroupLayout.Alignment.CENTER)
										 .addComponent(jtextfield_path_connection)
										 .addComponent(label_login,GroupLayout.Alignment.CENTER)
										 .addComponent(jtextfield_login)
										 .addComponent(label_password,GroupLayout.Alignment.CENTER)
										 .addComponent(jtextfield_password)
										 .addGroup(GroupLayout.Alignment.CENTER,group_layout.createSequentialGroup()
												 .addComponent(button_connection,100,100,100)
												 .addGap(50)
										 		 .addComponent(button_test_connection,100,100,100)
										 )
										);
		group_layout_vertical.addComponent(label_path_connection);
		group_layout_vertical.addComponent(jtextfield_path_connection);
		group_layout_vertical.addComponent(label_login);
		group_layout_vertical.addComponent(jtextfield_login);
		group_layout_vertical.addComponent(label_password);
		group_layout_vertical.addComponent(jtextfield_password);
		group_layout_vertical.addGroup(group_layout.createParallelGroup()
									   .addComponent(button_connection)
									   .addComponent(button_test_connection)
									   );
		
		group_layout.setHorizontalGroup(group_layout_horizontal);
		group_layout.setVerticalGroup(group_layout_vertical);
		panel_main.setBorder(javax.swing.BorderFactory.createEmptyBorder(5,5,5,5));
		button_test_connection.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				test_connection();
			}
		});
		button_connection.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				showReports();
			}
		});
		this.getContentPane().add(panel_main);
	}
	
	@Override
	public void internalFrameActivated(InternalFrameEvent e) {
		
	}
	@Override
	public void internalFrameClosed(InternalFrameEvent e) {
		this.setVisible(false);
		this.field_parent.close(0);
	}
	@Override
	public void internalFrameClosing(InternalFrameEvent e) {
		
	}
	@Override
	public void internalFrameDeactivated(InternalFrameEvent e) {
		
	}
	@Override
	public void internalFrameDeiconified(InternalFrameEvent e) {
		
	}
	@Override
	public void internalFrameIconified(InternalFrameEvent e) {
		
	}
	@Override
	public void internalFrameOpened(InternalFrameEvent e) {
		
	}
	/**
	 * попытка соединения и получения Connection
	 * @return
	 */
	private Connection getConnection(){
		Connection return_value=null;
		try {
			System.out.println("try connection");
			System.out.println("find driver");
			//Class.forName("org.firebirdsql.jdbc.FBDriver");
            Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("get connection");
			System.out.println("password:"+this.jtextfield_password.getText());
            System.out.println("login:"+this.jtextfield_login.getText());
            System.out.println("path:"+this.jtextfield_path_connection.getText());
			//Connection connection=DriverManager.getConnection(getPath(),"SYSDBA","masterkey");
			return_value=DriverManager.getConnection(this.jtextfield_path_connection.getText(),
														 this.jtextfield_login.getText(),
														 this.jtextfield_password.getText());
		} catch (SQLException e) {
			System.out.println("SQLException Connection error");
			return_value=null;
		} catch(Exception ex){
			return_value=null;
			//ex.printStackTrace();
			System.out.println("Exception:"+ex.getMessage());
		}
		return return_value;
	}
	/**
	 * попытка соединения с базой данных  
	 */
	private void test_connection(){
		if(this.getConnection()!=null){
			JOptionPane.showInternalMessageDialog(this,"OK","connection",JOptionPane.INFORMATION_MESSAGE);
		}else{
			JOptionPane.showInternalMessageDialog(this,"Error","not connected",JOptionPane.ERROR_MESSAGE);
		}
	}
	/** 
	 * переход на дочерний фрейм, который отображает таблицу с отчетами 
	 */
	private void showReports(){
		this.field_connection=this.getConnection();
		if(this.field_connection==null){
			field_logger.debug("Connection not get");
			JOptionPane.showInternalMessageDialog(this, "Соединение не получено","Ошибка соединения",JOptionPane.ERROR_MESSAGE);
		}else{
			field_logger.debug("show frame with reports");
			this.setVisible(false);
			this.field_child=new JInternalFrame_Reports(this.field_connection,this.field_desktop,this,600,400);
		}
	}
	/** получение полного пути к базе в виде URL, который будет отображатся на форме и использоватся в виде подключения */
	private String getPath(){
		//return "jdbc:firebirdsql://pcitdvlp01:3050/c:/temp.gdb?sql_dialect=3";
		return "jdbc:oracle:thin:@192.168.15.254:1521:demo";
	}
	/** получить Login, который будет отображатся на форме */
	private String getLogin(){
		//return "SYSDBA";
		return "bc_reports";
	}
	/** получить Password, который будет отображатся на форме*/
	private String getPassword(){
		//return "masterkey";
		return "bc_reports";
	}

	@Override
	public void close(int return_value) {
		this.setVisible(true);
	}
}
