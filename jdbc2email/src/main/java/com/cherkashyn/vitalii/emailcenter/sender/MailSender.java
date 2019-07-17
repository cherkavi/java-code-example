package com.cherkashyn.vitalii.emailcenter.sender;

import java.io.File;
import java.security.Security;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;

// import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.MimeBodyPart;

public class MailSender {
	static{
        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
	}
	
	public static final MailSender INSTANCE=new MailSender();
	
    private static final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

    /**
     * @param smtpHost
     * @param smtpPort
     * @param user
     * @param password
     * @param isSSL
     * @param recipients
     * @param subject
     * @param messageContent
     * @param from
     * @param attachments
     * @throws MessagingException
     */
    public void sendMessage(String smtpHost, String smtpPort, String user, String password, boolean isSSL, List<String> recipients, String subject, String messageContent, String from, String[] attachments)
            throws MessagingException{

        Message message = createMessage(smtpHost, smtpPort, user, password, isSSL);
        
        message.setSubject(subject);
        message.setFrom(new InternetAddress(from));

        for (Iterator<String> iterator = recipients.iterator(); iterator.hasNext();) {
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(iterator.next()));
        }


        // use a MimeMultipart as we need to handle the file attachments
        Multipart multipart = new javax.mail.internet.MimeMultipart();
        message.setContent(multipart);

        addTextContent(multipart, messageContent);

        // add any file attachments to the message
        addAtachments(multipart, attachments);

        message.saveChanges();
        
        sendMessage(message);
    }

    /**
     * @param attachments
     * @param multipart
     * @throws MessagingException
     * @throws AddressException
     * @throws com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException 
     */
    protected void addAtachments(Multipart multipart, String[] attachments)
            throws MessagingException, AddressException, MessagingException {
    	if(attachments==null || attachments.length==0){
    		return;
    	}
    	for(String eachAttachment:attachments){
    		if(eachAttachment==null){
    			continue;
    		}
    		File file=new File(eachAttachment);
    		if(!file.exists() || !file.canRead()){
    			continue;
    		}
    		
            MimeBodyPart attachPart = new MimeBodyPart();
            attachPart.setDataHandler(new DataHandler(new FileDataSource(file)));
            attachPart.setFileName(file.getName());
            multipart.addBodyPart(attachPart);

    	}
    }


    private void addTextContent(Multipart multipart, String messageContent) throws MessagingException {
        BodyPart messageBodyPart = new javax.mail.internet.MimeBodyPart();
        // messageBodyPart.addHeader("Content-type", "text/HTML; charset=UTF-8");
        // messageBodyPart.setText(messageContent);
        messageBodyPart.setContent(messageContent, "text/html; charset=UTF-8");
        multipart.addBodyPart(messageBodyPart);
	}

	private final static Integer TIMEOUT_DEFAULT=5000;
    
	private Message createMessage(final String host, String port, final String user, final String password, boolean isSSL) {
    	// http://connector.sourceforge.net/doc-files/Properties.html
        Properties properties = new Properties();
        Session session=null;
        if(user!=null && password!=null){
            fillSmtpProperties(properties, true, isSSL, host, port, TIMEOUT_DEFAULT);
            session = Session.getDefaultInstance(properties,
                    new javax.mail.Authenticator() {
                        @Override
						protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(user, password);
                        }
                    });
        }else{
            fillSmtpProperties(properties, false, isSSL, host, port ,TIMEOUT_DEFAULT);
            session = Session.getDefaultInstance(properties);
        }

		return new MimeMessage(session);
	}

	private void fillSmtpProperties(Properties props, boolean isAuthentication, boolean isSSL, String smtpHost, String smtpPort, Integer connectionTimeOut) {
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.port", smtpPort);
        
        if(isSSL){
            props.put("mail.transport.protocol", "smtps");
            props.put("mail.smtp.ssl.enable", "true");
            props.put("mail.smtp.ssl.trust", smtpHost);
            props.put("mail.smtp.socketFactory.port", smtpPort);
            props.put("mail.smtp.socketFactory.class", SSL_FACTORY);
            props.put("mail.smtp.socketFactory.fallback", "false");
            // props.put("mail.smtp.starttls.enable","true");
        }
        if(isAuthentication){
            props.put("mail.smtp.auth", "true");
        }
        props.put("mail.smtp.ehlo", "true");
        if(connectionTimeOut!=null){
            // props.put("mail.smtp.timeout", "5000"); - not working
            props.put("mail.smtp.connectiontimeout", Integer.toString(connectionTimeOut));
        }
        props.put("mail.debug", "false");
	}

	private void sendMessage(Message message) throws MessagingException {
        /*Transport transport;
        transport = session.getTransport("smtps"); // smtp
        fillProperties(session.getProperties());
        // transport.connect(SMTP_HOST_NAME, SMTP_USER, SMTP_PASSWORD);
        transport.connect();
        message.saveChanges(); 
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();*/
        Transport.send(message);
	}
    
}
