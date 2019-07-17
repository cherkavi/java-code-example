package ua.cetelem.dossierserver.tools.reestr_loans.transport.mail;

import java.util.ArrayList;
import java.util.Properties;

import ua.cetelem.dossierserver.tools.reestr_loans.RunParameters;
import ua.cetelem.dossierserver.tools.reestr_loans.exceptions.EMailException;
import java.util.Date;


import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;

import org.apache.log4j.Logger;

/**
 * Sender to EMail 
 */
public class EMailSender {
	/** send files to EMail
	 * @param parameters - parameters for sending to EMail
	 * @param pathToFiles - path to files for send to EMail
	 */
	public static void sendFilesToEMail(RunParameters parameters, String ... pathToFiles) throws EMailException{
		sendMail(parameters.getMailRecipients(),
				 parameters.getMailReplyTo(),
				 null, 
				 parameters.getMailSubject(), 
				 parameters.getMailText(), 
				 parameters.getMailFrom(),
				 parameters.getMailLogin(),
				 parameters.getMailPassword(),
				 parameters.getMailSmtp(),
				 parameters.getMailPort(),
				 pathToFiles
				 );
	}

	
	private static void sendMail(String[] recipients,
								 String[] replyTo,
								 String[] carbonCopyRecipients,
			   					 String subject, 
			   					 String text, 
			   					 String from,
			   					 final String login, 
			   					 final String password, 
			   					 String smtp, 
			   					 int port,
			   					 String ... files) throws EMailException{
		Logger logger=Logger.getLogger(EMailSender.class);
		Properties props = new Properties();
        props.put("mail.smtp.host", smtp);
        props.put("mail.smtp.port", "" + port);
        props.setProperty("mail.smtp.user", login);
        if ((password != null)&&(password.length()>0)) {
            props.setProperty("mail.smtp.password", password);
        }
		
        Session session=Session.getDefaultInstance(props,null); 
        // session.setPasswordAuthentication(new URLName(smtp), new PasswordAuthentication(login, password));
        session.setDebug(true);

        try {
            MimeMessage msg = new MimeMessage(session);
            msg.setHeader("Content-Type", "text/plain;charset=utf-8");
            msg.setHeader("Content-Transfer-Encoding", "8bit");
            msg.setFrom(new InternetAddress(from));
            if(recipients!=null){
                for(int counter=0;counter<recipients.length;counter++){
                	try{
                		msg.addRecipient(Message.RecipientType.TO,new InternetAddress(recipients[counter]));
                	}catch(Exception ex){
                		// InternetAddress create Exception
                	}
                }
            }
            msg.setReplyTo(convertStringToInetAddress(replyTo));
            if(carbonCopyRecipients!=null){
            	for(int counter=0;counter<carbonCopyRecipients.length;counter++){
            		try{
            			msg.addRecipient(Message.RecipientType.CC,new InternetAddress(carbonCopyRecipients[counter]));
            		}catch(Exception ex){
            			// InternetAddress create Exception
            		}
            	}
            }
            msg.setSubject(subject);
            msg.setSentDate(new Date());

            MimeBodyPart messageText=new MimeBodyPart();
            messageText.setText(text);
            	
            Multipart multiPart=new MimeMultipart();
            multiPart.addBodyPart(messageText);
            for(int counter=0;counter<files.length;counter++){
                MimeBodyPart filePart=new MimeBodyPart();
                DataSource fileDataSource=new FileDataSource(files[counter]);
                filePart.setDataHandler(new DataHandler(fileDataSource));
                filePart.setFileName(extractFileName(files[counter]));
                multiPart.addBodyPart(filePart);
            }
            msg.setContent(multiPart);
            
            Transport transport=session.getTransport("smtp");
            transport.connect();
            // transport.connect(smtp,port,login, password);
            transport.sendMessage(msg, msg.getAllRecipients());
            transport.close();
        } catch (AddressException e) {
        	logger.error("AddressException: "+e.getMessage());
        	throw new EMailException("AddressException: "+e.getMessage());
        } catch (MessagingException e) {
        	logger.error("MessagingException: "+e.getMessage());
        	throw new EMailException("MessagingException: "+e.getMessage());
        }		
	}

	private static String extractFileName(String value) {
		int index=value.lastIndexOf(System.getProperty("file.separator"));
		if(index>=0){
			return value.substring(index+1);
		}else{
			return value;
		}
	}

	
	/**
	 * convert array of String to array of InternetAddress
	 * @param replyTo
	 * @return
	 */
	private static Address[] convertStringToInetAddress(String[] replyTo) {
		if(replyTo==null)return null;
		ArrayList<Address> returnValue=new ArrayList<Address>();
		for(int counter=0;counter<replyTo.length;counter++){
			try{
				returnValue.add(new InternetAddress(replyTo[counter]));
			}catch(Exception ex){
				// address cannot be added to list, wrong address
			}
		}
		return returnValue.toArray(new Address[]{});
	}
}
