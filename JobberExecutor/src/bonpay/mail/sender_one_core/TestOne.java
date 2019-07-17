package bonpay.mail.sender_one_core;

import java.awt.GridLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import database.IConnectionAware;
import database.OracleConnectionAware;

import bonpay.mail.sender_one_core.implementations.LetterConfirmSendLetter;
import bonpay.mail.sender_one_core.implementations.LetterFromDatabaseById;
import bonpay.mail.sender_one_core.interfaces.IConfirmSendLetter;

public class TestOne extends JFrame{
	private final static long serialVersionUID=1L;
	private IConnectionAware connectorAware=new OracleConnectionAware();
	private JTextField textNumber;
	
	public TestOne(){
		super("test one");
		// create element's
		textNumber=new JTextField();
		JButton buttonSend=new JButton("send");

		// add listener's
		buttonSend.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				onButtonSend();
			}
		});

		// placing element's
		JPanel panelTitle=new JPanel(new GridLayout(1,1));
		panelTitle.add(textNumber);
		panelTitle.setBorder(javax.swing.BorderFactory.createTitledBorder("ID of letter"));
		JPanel panelMain=new JPanel(new GridLayout(2,1));
		panelMain.add(panelTitle);
		panelMain.add(buttonSend);
		this.getContentPane().add(panelMain);
		this.setBounds(100,100,300,200);
		this.setVisible(true);
	}

	/** */
	private void onButtonSend(){
		//ConnectWrap connector=this.connectorAware.getConnectWrap();
		Connection connection=this.connectorAware.getConnection();
		IConfirmSendLetter confirmSend=new LetterConfirmSendLetter();
		int id=Integer.parseInt(this.textNumber.getText());
		try{
			LetterFromDatabaseById letter=new LetterFromDatabaseById(connection, id);
			SenderOneLetter sender=new SenderOneLetter(letter);
			sender.sendMail(letter);
			confirmSend.setLetterAsSended(id, connection);
			JOptionPane.showMessageDialog(this, "sended");
		}catch(Exception ex){
			JOptionPane.showMessageDialog(this,"Letter was not sended: "+ex.getMessage(),"Error", JOptionPane.WARNING_MESSAGE);
			confirmSend.setLetterAsSendedError(id, connection);
		}finally{
			try{
				connection.close();
			}catch(Exception ex){};
		}
	}
	
	public static void main(String[] args){
		new TestOne();
	}
	 
}


