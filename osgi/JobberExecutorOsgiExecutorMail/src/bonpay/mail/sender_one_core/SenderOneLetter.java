package bonpay.mail.sender_one_core;

import java.util.Properties;


import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.URLName;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

import bonpay.mail.sender_one_core.interfaces.ILetterBodyAware;
import bonpay.mail.sender_one_core.interfaces.ILetterSettingsAware;

/** отправитель E-mail*/
public class SenderOneLetter {
	private Logger logger=Logger.getLogger(this.getClass());
	private ILetterSettingsAware letterSettingsAware;
	
	/** отправитель E-mail*/
	public SenderOneLetter(ILetterSettingsAware letterSettingsAware){
		this.updateSettingsAware(letterSettingsAware);
	}
	
	/** обновить объект, который отвечает за получение параметров для E-mail */
	public void updateSettingsAware(ILetterSettingsAware letterSettingsAware){
		logger.debug("обновить объект, который отвечает за получение параметров для E-mail");
		this.letterSettingsAware=letterSettingsAware;
	}
	
	/** отправить письмо 
	 * @param letterAware - объект, который "ведает" текстом письма 
	 * @throws Exception - если произошла какая-либо ошибка во время отправки (или же SMTP сервер принял сообщение для получателя)  
	 */
	public void sendMail(ILetterBodyAware letterAware) throws Exception {
		logger.debug("отправить письмо");
		Properties props = new Properties();
		
        props.put("mail.smtp.host", this.letterSettingsAware.getSmtp());
        props.put("mail.smtp.port", "" + this.letterSettingsAware.getPort());
		
        /*Session session = Session.getDefaultInstance(props,new Authenticator(){
        	@Override
        	protected PasswordAuthentication getPasswordAuthentication() {
        		return new PasswordAuthentication(login, password);
        	}
        });*/
        Session session=Session.getDefaultInstance(props,null);
        if(this.letterSettingsAware.isAuth()){
        	session.setPasswordAuthentication(new URLName(this.letterSettingsAware.getSmtp()), new PasswordAuthentication(this.letterSettingsAware.getLogin(), this.letterSettingsAware.getPassword()));
        }
        // session.setDebug(true);
        logger.debug("создать сообщение для отправки ");
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(letterAware.getFrom()));
        String[] recipients=letterAware.getRecipients();
        for(int counter=0;counter<recipients.length;counter++){
        	msg.addRecipient(Message.RecipientType.TO,new InternetAddress(recipients[counter]));
        }
        msg.setSubject(letterAware.getSubject());
        msg.setText(letterAware.getText());
        
      //Transport.send(msg);
        logger.debug("получить транспорт для отправки");
        Transport transport=session.getTransport("smtp");
        logger.debug("установить соединение с SMTP ");
        transport.connect(this.letterSettingsAware.getSmtp(),
        				  this.letterSettingsAware.getPort(),
        				  this.letterSettingsAware.getLogin(), 
        				  this.letterSettingsAware.getPassword());
        logger.debug("послать сообщение ");
        transport.sendMessage(msg, msg.getAllRecipients());
        logger.debug("SMTP ответил положительно на установку письма в очередь ");
        transport.close();
        logger.debug(" закрыть транспорт для отправки письма ");
        // System.out.println("Send is OK: ");
	}
	
	
	
}
