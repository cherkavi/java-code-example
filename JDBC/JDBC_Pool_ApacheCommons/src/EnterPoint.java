import javax.swing.*;

import ConnectorPool.Connector;

import java.awt.event.*;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.SQLException;

public class EnterPoint extends JFrame{
	private final static long serialVersionUID=1L;

	private void outDebug(Object information){
		PrintStream currentOut=System.out;
		StackTraceElement traceElement=((new Throwable()).getStackTrace())[1];
		currentOut.print(traceElement.getClassName()+"."+traceElement.getMethodName());
		currentOut.print(" DEBUG:");
		currentOut.println(information);
	}

	private void outError(Object information){
		PrintStream currentOut=System.out;
		StackTraceElement traceElement=((new Throwable()).getStackTrace())[1];
		currentOut.print(traceElement.getClassName()+"."+traceElement.getMethodName());
		currentOut.print(" ERROR:");
		currentOut.println(information);
	}
	
	public static void main(String[] args){
		new EnterPoint();
	}
	
	public EnterPoint(){
		super("Connector");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initComponents();
		setSize(300,300);
		pack();
		setVisible(true);
	}
	
	/** проинициализировать компоненты */
	private void initComponents(){
		JPanel panelMain=new JPanel();
		GroupLayout groupLayout=new GroupLayout(panelMain);
		panelMain.setLayout(groupLayout);
		GroupLayout.SequentialGroup groupLayoutHorizontal=groupLayout.createSequentialGroup();
		GroupLayout.SequentialGroup groupLayoutVertical=groupLayout.createSequentialGroup();
		groupLayout.setHorizontalGroup(groupLayoutHorizontal);
		groupLayout.setVerticalGroup(groupLayoutVertical);
		
		JButton buttonConnectWithClose=new JButton("Connect with Close");
		JButton buttonConnectWithoutClose=new JButton("Connect without Close");
		JButton buttonConnectOtherWithClose=new JButton("Connect other user with Close");
		JButton buttonConnectOtherWithoutClose=new JButton("Connect other user without Close");

		buttonConnectWithClose.addActionListener(new ActionListener(){
			@Override 
			public void actionPerformed(ActionEvent e){
				onConnectWithClose();
			}
		});
		buttonConnectWithoutClose.addActionListener(new ActionListener(){
			@Override 
			public void actionPerformed(ActionEvent e){
				onConnectWithoutClose();
			}
		});
		buttonConnectOtherWithClose.addActionListener(new ActionListener(){
			@Override 
			public void actionPerformed(ActionEvent e){
				onConnectOtherWithClose();
			}
		});
		buttonConnectOtherWithoutClose.addActionListener(new ActionListener(){
			@Override 
			public void actionPerformed(ActionEvent e){
				onConnectOtherWithoutClose();
			}
		});
		
		
		groupLayoutHorizontal.addGroup(groupLayout.createParallelGroup()
									   .addComponent(buttonConnectWithClose)
									   .addComponent(buttonConnectWithoutClose)
									   .addComponent(buttonConnectOtherWithClose)
									   .addComponent(buttonConnectOtherWithoutClose)
									   );
		groupLayoutVertical.addComponent(buttonConnectWithClose);
		groupLayoutVertical.addComponent(buttonConnectWithoutClose);
		groupLayoutVertical.addComponent(buttonConnectOtherWithClose);
		groupLayoutVertical.addComponent(buttonConnectOtherWithoutClose);
		this.getContentPane().add(panelMain);
	}
	
	private void onConnectWithClose() {
		Connection connection=Connector.getConnection("SYSDBA","masterkey");
		outDebug("Connection: "+connection);
		try{
			connection.close();
		}catch(SQLException ex){
			outError(ex.getMessage());
		}
	}
	
	private void onConnectWithoutClose(){
		Connection connection=Connector.getConnection("SYSDBA","masterkey");
		outDebug("Connection: "+connection);
	}
	
	private void onConnectOtherWithClose(){
		Connection connection=Connector.getConnection("first","first");
		outDebug("Connection: "+connection);
		try{
			connection.close();
		}catch(SQLException ex){
			outError(ex.getMessage());
		}
	}

	private void onConnectOtherWithoutClose(){
		Connection connection=Connector.getConnection("first","first");
		outDebug("Connection: "+connection);
	}
}
