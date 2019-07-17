package bonpay.mail.sender_one_core;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.URLName;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import bonpay.mail.sender_one_core.interfaces.ILetterBodyAware;
import bonpay.mail.sender_one_core.interfaces.ILetterSettingsAware;

/** отправитель E-mail*/
public class SenderOneLetter {
	private ILetterSettingsAware letterSettingsAware;
	
	/** отправитель E-mail*/
	public SenderOneLetter(ILetterSettingsAware letterSettingsAware){
		this.updateSettingsAware(letterSettingsAware);
	}
	
	/** обновить объект, который отвечает за получение параметров для E-mail */
	public void updateSettingsAware(ILetterSettingsAware letterSettingsAware){
		this.letterSettingsAware=letterSettingsAware;
	}
	
	/** отправить письмо 
	 * @param letterAware - объект, который "ведает" текстом письма 
	 * @throws Exception - если произошла какая-либо ошибка во время отправки (или же SMTP сервер принял сообщение для получателя)  
	 */
	public void sendMail(ILetterBodyAware letterAware) throws Exception {
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

        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(letterAware.getFrom()));
        String[] recipients=letterAware.getRecipients();
        for(int counter=0;counter<recipients.length;counter++){
        	msg.addRecipient(Message.RecipientType.TO,new InternetAddress(recipients[counter]));
        }
        msg.setSubject(letterAware.getSubject());
        msg.setText(letterAware.getText());
        
      //Transport.send(msg);
        Transport transport=session.getTransport("smtp");
        transport.connect(this.letterSettingsAware.getSmtp(),
        				  this.letterSettingsAware.getPort(),
        				  this.letterSettingsAware.getLogin(), 
        				  this.letterSettingsAware.getPassword());
        transport.sendMessage(msg, msg.getAllRecipients());
        transport.close();
        // System.out.println("Send is OK: ");
	}
	
	
	
}
