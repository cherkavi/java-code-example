package com.cherkashyn.vitalii.emailcenter.sender;

import java.util.Arrays;
import java.util.List;

import javax.mail.MessagingException;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Before;
import org.junit.Test;

public class MailSenderTest {

	@Before
	public void setUp() throws IllegalAccessException{
		FieldUtils.writeDeclaredStaticField(EmailSender.class, "debugFlag", Boolean.TRUE, true);
	}
	
	
	@Test
	public void sendMailNeedToCheckMailboxManually() throws MessagingException{
		SMTP smtp=SMTP.mail_ru;
		String[] files=getFiles();

		String smtpHost=smtp.getServer();
		int smtpPort=smtp.getPort();
		String smtpUser=smtp.getUser();
		String smtpPassword=smtp.getPassword();
		
		boolean isSsl=true;
		List<String> recipients=getRecipients();
		String subject="test subject";
		String text="test <b> message </b> это тестовое сообщение";
		String sender=smtpUser;
		
        MailSender.INSTANCE.sendMessage(smtpHost, Integer.toString(smtpPort), smtpUser, smtpPassword, isSsl, recipients, subject, text, sender, files);
	}
	
	private final static String SYSTEM_PROPERTY_RECIPIENT="recipients";
	
	private String[] getFiles(){
		return new String[]{ClassLoader.class.getResource("/one.txt").getFile(), ClassLoader.class.getResource("/two.txt").getFile()};
	}
	
	private List<String> getRecipients(){
		String recipients=System.getProperty(SYSTEM_PROPERTY_RECIPIENT);
		if(recipients==null){
			System.out.println("can't find system parameter: "+SYSTEM_PROPERTY_RECIPIENT);
			return Arrays.asList(new String[]{"technik@mail.ru"});
		}else{
			return Arrays.asList(recipients.split(","));
		}
	}
	
}
