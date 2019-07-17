package bonpay.mail.sender_core.sender;

import java.util.ArrayList;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.URLName;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import bonpay.mail.sender_core.letter.Letter;
import bonpay.mail.sender_core.letter.LetterIdentifier;
import bonpay.mail.sender_core.manager.ILetterAware;
import bonpay.mail.sender_core.manager.SenderIdentifier;

/** объект, который отправляет почту, согласно настройкам из базы данных */
public class SenderThread extends Thread implements INotifyNewLetter, INotifySettingsChange{
	/** уникальный идентификатор данного Sender */
	private SenderIdentifier identifier;
	/** настройки данного Sender */
	private SenderSettings senderSettings;
	/** флаг, который говорит о необходимости продолжения жизненного цикла потока */
	private volatile boolean flagRun=false;
	/** объект, который является общим ресурсом для потоков */
	private Object controlObject=new Object();
	/** объект, из которого можно получить */
	private ILetterAware letterAware;
	/** объект, который "ведает"  настройками Sender */
	private ISenderSettingsAware senderSettingsAware;
	/** объект, который говорит о необходимости изменения в настройках объекта */
	private Boolean isSettingsChanged=false;
	
	
	/** объект, который отправляет почту, согласно настройкам из базы данных
	 * @param senderIdentifier - unique Sender Identifier 
	 * @param letterAware - объект-получатель писем из базы данных 
	 * @param senderSettingsAware - объект, который владеет настройками Sender
	 */
	public SenderThread(SenderIdentifier senderIdentifier, ILetterAware letterAware, ISenderSettingsAware senderSettingsAware){
		this.identifier=senderIdentifier;
		this.letterAware=letterAware;
		this.senderSettingsAware=senderSettingsAware;
	}

	/** оповещение объекта о необходимости получения нового письма из базы данных */
	@Override
	public void notifyAboutNewLetter(){
		synchronized(this.controlObject){
			this.controlObject.notify();
		}
	}
	
	/** оповещение о необходимости обновления настроек данного потока-отправщика писем */
	@Override
	public void notifySenderSettingsChange(){
		synchronized(this.isSettingsChanged){
			this.isSettingsChanged=true;
		}
	}
	
	/** сессия для отправки почтовых сообщений */
	private Session mailSession;
	
	/** обновить параметры данного Sender */
	private void updateSenderSettings(SenderSettings senderSettings) {
		// получить настройки из базы данных по данному пользовател
		this.senderSettings=senderSettings;
		try{
			Properties props = new Properties();
	        props.put("mail.smtp.host", this.senderSettings.getSmtpServer());
	        props.put("mail.smtp.port", "" + this.senderSettings.getSmtpPort());
	        
	        mailSession=Session.getDefaultInstance(props,null); 
	        mailSession.setPasswordAuthentication(new URLName(this.senderSettings.getSmtpServer()), new PasswordAuthentication(this.senderSettings.getLogin(), this.senderSettings.getPassword()));
	        mailSession.setDebug(false);
		}catch(NullPointerException npe){
			System.err.println("updateSenderSettings Exception:"+npe.getMessage());
		}
	}
	
	/** оповестить поток о необходимости прекращения работы */
	public void stopThread(){
		this.flagRun=false;
	}
	
	private void debug(String value){
		System.out.print("SenderThread ");
		System.out.print("DEBUG");
		System.out.println(value);
	}
	
	public void run(){
		this.flagRun=true;
		debug("дать команду на загрузку настроек из внешнего хранилища"); 
		this.isSettingsChanged=true;
		while(flagRun==true){
			while(isSettingsChanged==true){
				synchronized(this.isSettingsChanged){
					this.isSettingsChanged=false;
				}
				this.updateSenderSettings(this.senderSettingsAware.getSenderSettingsByIdentifier(this.identifier));
			}
			debug("получить очередной идентификатор письма"); 
			ArrayList<LetterIdentifier> letters=this.letterAware.getNewLetters(this.identifier);
			if((letters!=null)&&(letters.size()>0)){
				debug("отправить");
				if(this.sendLetterByIdentifier(letters.get(0))){
					debug("письмо удачно отправлено");
					this.letterAware.setLetterAsSended(letters.get(0));
				}else{
					debug("письмо не отправлено - установить флаг ошибочной отправки ");
					this.letterAware.setLetterAsSendedError(letters.get(0));
				}
				debug("заснуть на определенное время");
				try{this.senderSettings.getTimeDelay();}catch(Exception ex){};
			}else{
				debug("писем нет - ожидать"); 
				synchronized(this.controlObject){
					if((letters!=null)&&(letters.size()>0)){
						debug("появилась задача"); 
						continue;
					}else{
						try{
							this.controlObject.wait();
						}catch(Exception ex){};
					}
				}
			}
		}
	}

	/** получить уникальный идентификатор по данному Sender */
	public SenderIdentifier getIdentifier(){
		return this.identifier;
	}
	
	/** отправить письмо и вернуть положительный ответ в случае отправки */
	private boolean sendLetterByIdentifier(LetterIdentifier letterIdentifier){
		boolean returnValue=false;
		// получить письмо
		Letter letter=this.letterAware.getLetter(letterIdentifier);
		if(letter!=null){
			// отправить письмо
	        try {
	            Message msg = new MimeMessage(mailSession);
	            msg.setFrom(new InternetAddress(this.senderSettings.getFrom()));
	            for(int counter=0;counter<letter.getRecipientSize();counter++){
	            	msg.addRecipient(Message.RecipientType.TO,new InternetAddress(letter.getRecipient(counter)));
	            }
	            msg.setSubject(letter.getSubject());
	            msg.setText(letter.getText());
	            
	            //Transport.send(msg);
	            Transport transport=mailSession.getTransport("smtp");
	            transport.connect(this.senderSettings.getSmtpServer(),this.senderSettings.getSmtpPort(),this.senderSettings.getLogin(), this.senderSettings.getPassword());
	            transport.sendMessage(msg, msg.getAllRecipients());
	            transport.close();
	            returnValue=true;
	        } catch (AddressException e) {
	        	returnValue=false;
	        	System.err.println("AddressException: "+e.getMessage());
	        } catch (MessagingException e) {
	        	returnValue=false;
	        	System.err.println("MessagingException: "+e.getMessage());
	        }		
		}else{
			// ошибка получения письма - пометить как ошибочное 
			returnValue=false;
			//this.letterAware.setLetterAsSendedError(letterIdentifier);
		}
		return returnValue;
	}

	@Override
	public void notifyAboutNewLetter(SenderIdentifier senderIdentifier) {
		if(this.identifier.equals(senderIdentifier)){
			this.notifyAboutNewLetter();
		}
	}

	@Override
	public void notifySenderSettingsChange(SenderIdentifier senderIdentifier) {
		if(this.identifier.equals(senderIdentifier)){
			this.notifySenderSettingsChange();
		}
	}

}
