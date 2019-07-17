package com.cherkashyn.vitalii.emailcenter.sender;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.cherkashyn.vitalii.emailcenter.domain.SendMessage;
import com.cherkashyn.vitalii.emailcenter.exception.SendException;
import com.cherkashyn.vitalii.emailcenter.exception.SendParametersException;

/**
 * wrong logic inside, pls, use {@link MailSender}
 */
public class EmailSender {
	final static Logger			LOGGER				= Logger.getLogger(EmailSender.class);
	
	private static boolean debugFlag=false;
	
	public static void send(SendMessage message ) throws SendException{
		
		JavaMailSenderImpl sender = new JavaMailSenderImpl();
		sender.setHost(message.getSmtpServer());
		sender.setPort(message.getSmtpPort());
		sender.getJavaMailProperties().put("mail.debug", Boolean.toString(debugFlag));
		sender.getJavaMailProperties().put("mail.smtp.timeout", 5000);
		
		if(message.isSmtpAuthorization()){
			sender.getJavaMailProperties().put("mail.smtp.auth", true);
			sender.setUsername(message.getSmtpUser());
			sender.setPassword(message.getSmtpPassword());
		}
		if(message.isSmtpSsl()){
			sender.getJavaMailProperties().put("mail.smtp.starttls.enable", "true");
			sender.getJavaMailProperties().put("mail.smtp.quitwait", "false");
			sender.getJavaMailProperties().put("mail.smtp.socketFactory.port", Integer.toString(message.getSmtpPort()));
			sender.getJavaMailProperties().put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			sender.getJavaMailProperties().put("mail.smtp.socketFactory.fallback", "false");
		}
		MimeMessage emailMessage = sender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(emailMessage);
			helper.setTo(message.getRecipient());
			helper.setText(message.getText());
			helper.setSubject(message.getSubject());
			if(message.hasAttachment()){
//				FileSystemResource file = new FileSystemResource(new File("c:/Sample.jpg"));
//				helper.addAttachment("CoolImage.jpg", file);
			}
		} catch (MessagingException e) {
			throw new SendParametersException("can't set parameters: "+e.getMessage(), e);
		}
		sender.send(emailMessage);
	}
	
}
