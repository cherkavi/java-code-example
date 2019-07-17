package bonclub.office_private.gui.text_exchange;

import jms.sender.JMSSender;

/** отправщик текстовых сообщений */
public class JmsTextSender implements ITextOutput{
	private JMSSender sender;
	
	/** отправщик текстовых сообщений 
	 * @param pathToJms -полный путь к JMS 
	 * @param queueName - имя очереди 
	 * @param senderTitle - наименование 
	 */
	public JmsTextSender(String pathToJms, String queueName, String senderTitle ){
		sender=new JMSSender(pathToJms, queueName,10, senderTitle);
		sender.start();
	}
	@Override
	public boolean sendText(String message) {
		return this.sender.addTextMessage(message);
	}

}
