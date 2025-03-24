package com.cherkashyn.vitalii.emailcenter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.mail.MessagingException;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.util.CollectionUtils;

import com.cherkashyn.vitalii.emailcenter.datasource.MessageAttachmentFinder;
import com.cherkashyn.vitalii.emailcenter.datasource.MessageFinder;
import com.cherkashyn.vitalii.emailcenter.datasource.StatusUpdater;
import com.cherkashyn.vitalii.emailcenter.domain.MessageAttachment;
import com.cherkashyn.vitalii.emailcenter.domain.SendMessage;
import com.cherkashyn.vitalii.emailcenter.exception.SendException;
import com.cherkashyn.vitalii.emailcenter.sender.MailSender;
import com.cherkashyn.vitalii.emailcenter.utility.Timer;


/**
 * read data from DB and send Email
 *  
 */
public class Sender {
	private final static String	SPRING_CONTEXT_PATH	= "applicationContext.xml";

	final static Logger			LOGGER				= Logger.getLogger(Sender.class);

	/** Enter point for application */
	public static void main(String[] args) {
		ApplicationContext context = null;
		if (args.length > 0) {
			context = new FileSystemXmlApplicationContext(args[0]);
		} else {
			context = new ClassPathXmlApplicationContext(SPRING_CONTEXT_PATH);
		}

		context.getBean(Sender.class).executeMainLogic();
	}

	/**
	 * delay before next cycle
	 */
	@Value("${timeout.main_cycle}")
	private int				delay					= 5;
	
	/** value for omit it from JMX */
	private boolean continueToExecute=true;
	
	@Autowired
	MessageFinder finder;
	
	@Autowired
	MessageAttachmentFinder finderAttachment;

	@Autowired
	StatusUpdater statusUpdater;
	
	public void stopExecution(){
		this.continueToExecute=false;
	}

	public void executeMainLogic() {
		LOGGER.info(" - start - ");
		Timer timer = new Timer(delay);

		while (continueToExecute) {
			LOGGER.debug(" - wait before next iteration - ");
			timer.waitingForFinished();
			timer.start();

			LOGGER.debug(" - get data for sending" );
			Collection<SendMessage> sendList = finder.findNext();
			if(CollectionUtils.isEmpty(sendList)){
				continue;
			}
			
			for(SendMessage eachMessage:sendList){
				try{
					sendMessage(eachMessage);
					markSended(eachMessage);
					LOGGER.debug(" message was sent: "+eachMessage );
				}catch(SendException ex){
					markError(eachMessage, ex);
					LOGGER.warn(" can't send message: "+eachMessage, ex);
				}
				
				try {
					TimeUnit.SECONDS.sleep(eachMessage.getDelayBeforeNext());
				} catch (InterruptedException e) {
				}
			}
		}
		LOGGER.info(" - end - ");
	}

	private void markError(SendMessage eachMessage, SendException sendException) {
		statusUpdater.sendError(eachMessage.getId(), sendException.getMessage());
	}

	private void markSended(SendMessage eachMessage) {
		statusUpdater.sendSuccessfully(eachMessage.getId());
	}

	private void sendMessage(SendMessage message) throws SendException{
		try {
			MailSender.INSTANCE.sendMessage(message.getSmtpServer(), Integer.toString(message.getSmtpPort()), message.isSmtpAuthorization()?message.getSmtpUser():null, message.isSmtpAuthorization()?message.getSmtpPassword():null, message.isSmtpSsl(), parseRecipients(message.getRecipient()), message.getSubject(), message.getText(), message.getFrom(), parseAttachments(message));
		} catch (MessagingException e) {
			throw new SendException(e.getMessage());
		}
	}

	private final static String EMAIL_RECIPIENTS_DELIMITER=";";
	
	private List<String> parseRecipients(String recipient) {
		List<String> returnValue=new ArrayList<String>();
		if(recipient==null){
			return returnValue;
		}
		String[] parts=recipient.split(EMAIL_RECIPIENTS_DELIMITER);
		for(String eachPart:parts){
			String clearValue=StringUtils.trimToNull(eachPart);
			if(clearValue==null){
				continue;
			}
			returnValue.add(clearValue);
		}
		return returnValue;
	}

	private String[] parseAttachments(SendMessage message) {
		List<MessageAttachment> listOfAttachements=finderAttachment.findAttachments(message);
		if(CollectionUtils.isEmpty(listOfAttachements)){
			return new String[0];
		}
		List<String> returnValue=new ArrayList<String>();
		for(MessageAttachment eachAttachment:listOfAttachements){
			if(eachAttachment==null || eachAttachment.getFilePath()==null){
				continue;
			}
			returnValue.add(eachAttachment.getFilePath());
		}
		return returnValue.toArray(new String[listOfAttachements.size()]);
	}

}
