package bonpay.mail.sender_core;

import java.awt.GridLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import javax.swing.JButton;
import javax.swing.JFrame;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import pay.database.connector.Connector;
import pay.database.connector.IConnectorAware;

import bonpay.mail.sender_core.sender.SenderThreadContainer;

public class Test extends JFrame{
	private final static long serialVersionUID=1L;
	{
		String packageValue="bonpay.mail.sender_core";
		Logger.getLogger(packageValue).setLevel(Level.DEBUG);
		Logger.getLogger(packageValue).addAppender(new ConsoleAppender(new PatternLayout()));
		
		packageValue="org.hibernate";
		Logger.getLogger(packageValue).setLevel(Level.ERROR);
		Logger.getLogger(packageValue).addAppender(new ConsoleAppender(new PatternLayout()));
	}
	/** отправщик писем */
	private ConnectionAware connectorAware=new ConnectionAware();
	private SenderThreadContainer senderThreadContainer=new SenderThreadContainer(connectorAware,new LetterAware(connectorAware));
	
	public Test(){
		super("SenderContainer test");
		this.setBounds(100,100,200,75);
		senderThreadContainer.startAllSenderThread();
		JButton buttonNotify=new JButton("notify about new letter");
		buttonNotify.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				onButtonNotify();
			}
		});
		this.getContentPane().setLayout(new GridLayout(1,1));
		this.getContentPane().add(buttonNotify);
		this.setVisible(true);
	}
	
	private void onButtonNotify(){
		this.senderThreadContainer.notifyAboutNewLetter();
		System.out.println("notify");
	}
	
	public static void main(String[] args){
		System.out.println("begin");
		new Test();
		System.out.println("end");
	}
}

class ConnectionAware implements IConnectorAware{
	@Override
	public Connection getConnection() {
		return Connector.getConnectWrap().getConnection();
	}
}


