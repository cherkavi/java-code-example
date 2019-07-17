package bonclub.office_private.gui;


import java.awt.GridLayout;

import javax.swing.JFrame;

import bonclub.office_private.gui.chat_panel.PanelChat;
import bonclub.office_private.gui.text_exchange.ITextInput;
import bonclub.office_private.gui.text_exchange.ITextOutput;
import bonclub.office_private.gui.text_exchange.JmsTextSender;

import jms.listener.ITextDataRecieve;
import jms.listener.JMSListener;

public class EnterPoint extends JFrame implements ITextDataRecieve{
	private final static long serialVersionUID=1L;
	
	public static void main(String[] args){
		if(args.length>=4){
			new EnterPoint(args[0], args[1], args[2], args[3]);
		}else{
			String jmsServer="tcp://192.168.15.120:61616";
			String jmsOutput="office_private-47-30";
			String jmsInput="office_private_admin";
			// new EnterPoint(jmsServer, jmsOutput, jmsInput,"admin");
			new EnterPoint(jmsServer, jmsInput, jmsOutput, "test");
		}
	}
	
	public EnterPoint(String jmsServer, String jmsOutput, String jmsInput, String senderTitle){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initComponents(jmsServer, jmsOutput, jmsInput, senderTitle);
		this.setSize(250, 450);
		this.setVisible(true);
	}
	
	/** интерфейс по получению сообщения из удаленного источника */
	private ITextInput textInput=null;
	/** интерфейс по отправке сообщения в удаленный источник  */
	private ITextOutput textOutput=null;
	
	/** инициализация компонентов
	 * @param pathToJms - полный путь к JMS 
	 * @param queueOutput - имя исходящей очереди сообщений  
	 * @param queueInput - имя входящей очереди сообщений
	 * @param senderTitle - подпись отправителя  
	 */
	private void initComponents(String pathToJms, 
								String queueOutput, 
								String queueInput,
								String senderTitle){
		this.getContentPane().setLayout(new GridLayout(1,1));
		// создать отправителя сообщений из визуального интерфейса
		textOutput=new JmsTextSender(pathToJms, queueOutput, senderTitle);
		// создать получателя сообщений из визуального интерфейса 
		PanelChat panelChat=new PanelChat(senderTitle, textOutput);
		this.getContentPane().add(panelChat);
		this.textInput=panelChat;
		new JMSListener(pathToJms, queueInput, this);
	}

	@Override
	public void recieveTextData(String sender, String remoteTextMessage) {
		// place of receipt of incoming messages
		this.textInput.textInput( sender, remoteTextMessage);
	}
	
}
