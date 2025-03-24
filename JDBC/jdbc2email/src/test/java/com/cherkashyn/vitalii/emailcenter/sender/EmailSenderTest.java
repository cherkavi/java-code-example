package com.cherkashyn.vitalii.emailcenter.sender;

import javax.mail.MessagingException;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Before;
import org.junit.Test;

import com.cherkashyn.vitalii.emailcenter.domain.SendMessage;
import com.cherkashyn.vitalii.emailcenter.exception.SendException;

public class EmailSenderTest {

	@Before
	public void setUp() throws IllegalAccessException{
		FieldUtils.writeDeclaredStaticField(EmailSender.class, "debugFlag", Boolean.TRUE, true);
	}
	
	@Test
	public void sendMailNeedToCheckMailboxManually() throws MessagingException, IllegalAccessException, SendException{
		SMTP smtp=SMTP.mail_ru;
		
		SendMessage message=new SendMessage();
		FieldUtils.writeDeclaredField(message, "smtpServer", smtp.getServer(), true);
		FieldUtils.writeDeclaredField(message, "smtpPort", smtp.getPort(), true);
		FieldUtils.writeDeclaredField(message, "smtpUser", smtp.getUser(), true);
		FieldUtils.writeDeclaredField(message, "smtpPassword", smtp.getPassword(), true);

		FieldUtils.writeDeclaredField(message, "recipient", "technik@mail.ru", true);
		FieldUtils.writeDeclaredField(message, "subject", "test subject", true);
		FieldUtils.writeDeclaredField(message, "text", "test <b> message </b> это тестовое сообщение ", true);
		FieldUtils.writeDeclaredField(message, "attachment", false, true);
		FieldUtils.writeDeclaredField(message, "from", smtp.getUser(), true);
		FieldUtils.writeDeclaredField(message, "delayBeforeNext", 1000L, true);
		FieldUtils.writeDeclaredField(message, "smtpSsl", true, true);
		FieldUtils.writeDeclaredField(message, "smtpAuthorization", true, true);

        EmailSender.send(message);
	}
	
}
