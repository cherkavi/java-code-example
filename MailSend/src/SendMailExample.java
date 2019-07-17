import java.util.Date;
import java.util.Properties;


import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;

public class SendMailExample {
	
	/**
	 * @param recipient - полный адрес получателя
	 * @param subject - предмет письма
	 * @param text - текст, который нужно отправить в письме
	 * @param from - адрес, от кого письмо
	 * @param login - логин ( имя пользователя почтового ящика )
	 * @param password - пароль (пароль к почтовому ящику)
	 * @param smtp - SMTP сервер, ( полный путь )
	 * @param port ( порт, на который нужно соединяться )
	 */
	public SendMailExample(String recipient, 
						   String subject, 
						   String text, 
						   String from,
						   final String login, 
						   final String password, 
						   String smtp, 
						   int port){
		
		Properties props = new Properties();
        props.put("mail.smtp.host", smtp);
        props.put("mail.smtp.port", "" + port);
		
        /*Session session = Session.getDefaultInstance(props,new Authenticator(){
        	@Override
        	protected PasswordAuthentication getPasswordAuthentication() {
        		return new PasswordAuthentication(login, password);
        	}
        });*/
        Session session=Session.getDefaultInstance(props,null); 
        session.setPasswordAuthentication(new URLName(smtp), new PasswordAuthentication(login, password));
        session.setDebug(true);
        
        try {
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(from));
            msg.addRecipient(Message.RecipientType.TO,new InternetAddress(recipient));
            msg.setSubject(subject);
            msg.setSentDate(new Date());

            // if only text
            // msg.setText(text);

            // if attachment files
            	// Text part 
            MimeBodyPart messageText=new MimeBodyPart();
            messageText.setText(text);
            	
            
            MimeBodyPart filePart=new MimeBodyPart();
            DataSource fileDataSource=new FileDataSource("D:\\temp.rar");
            filePart.setDataHandler(new DataHandler(fileDataSource));
            filePart.setFileName("temp.rar");
            
            Multipart multiPart=new MimeMultipart();
            multiPart.addBodyPart(messageText);
            multiPart.addBodyPart(filePart);
            msg.setContent(multiPart);
            
            Transport transport=session.getTransport("smtp");
            transport.connect(smtp,port,login, password);
            transport.sendMessage(msg, msg.getAllRecipients());
            transport.close();
            System.out.println("Send is OK: ");
        } catch (AddressException e) {
            // ...
        	System.err.println("AddressException: "+e.getMessage());
        } catch (MessagingException e) {
            // ...
        	System.err.println("MessagingException: "+e.getMessage());
        }		
		
	}
	
	
	public static void main(String[] args){
		System.out.println("begin");
		@SuppressWarnings("unused")
		SendMailExample mail=new SendMailExample("technik7_job@mail.ru",
												 "test value",
												 "this is temp text",
												 "nmtg@mail.ru",
												 "nmtg",
												 "nmtg1234",// вставить пароль
												 "smtp.mail.ru",
												 25);
		System.out.println("end");
	}
}
