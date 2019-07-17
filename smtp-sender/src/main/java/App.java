import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

public class App {

	private static boolean debugFlag=false;



	public static void main(String ... args ) {
        SendMessage message = new SendMessage();
        JCommander.newBuilder()
                .addObject(message)
                .build()
                .parse(args);
        if(message.isHelp){
            new JCommander(message, args).usage();
            System.exit(0);
        }


		JavaMailSenderImpl sender = new JavaMailSenderImpl();
		sender.setHost(message.smtpServer);
		sender.setPort(message.smtpPort);
		sender.getJavaMailProperties().put("mail.debug", Boolean.toString(debugFlag));
		sender.getJavaMailProperties().put("mail.smtp.timeout", 5000);
		
		if(message.smtpAuthorization){
			sender.getJavaMailProperties().put("mail.smtp.auth", true);
			sender.setUsername(message.smtpUser);
			sender.setPassword(message.smtpPassword);
		}
		if(message.smtpSsl){
			sender.getJavaMailProperties().put("mail.smtp.starttls.enable", "true");
			sender.getJavaMailProperties().put("mail.smtp.quitwait", "false");
			sender.getJavaMailProperties().put("mail.smtp.socketFactory.port", Integer.toString(message.smtpPort));
			sender.getJavaMailProperties().put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			sender.getJavaMailProperties().put("mail.smtp.socketFactory.fallback", "false");
		}
		MimeMessage emailMessage = sender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(emailMessage);
			helper.setTo(message.recipient);
			helper.setText(message.text);
			helper.setSubject(message.subject);
			helper.setFrom(message.from);
//			if(message.at hasAttachment()){
//				FileSystemResource file = new FileSystemResource(new File("c:/Sample.jpg"));
//				helper.addAttachment("CoolImage.jpg", file);}
		} catch (MessagingException e) {
			throw new RuntimeException("can't set parameters: "+e.getMessage(), e);
		}
		sender.send(emailMessage);
	}
	
}




/**
 * object for reading from DB and sending to SMTP
 */
class SendMessage {

    @Parameter(names = {"-r", "--recipient"}, description = "recipient", required =  false)
    String recipient="target_address@unknowserver.com";

    @Parameter(names = {"-s", "--subject"}, description = "subject", required =  false)
    String subject="test subject";

    @Parameter(names = {"-t", "--text"}, description = "message text", required =  false)
    String text="test text";

    @Parameter(names = {"-f", "--from"}, description = "from", required =  false)
    String from="from_address@unknowserver.com";

    @Parameter(names = {"-h", "--host"}, description = "host", required =  false)
    String smtpServer="127.0.0.1";

    @Parameter(names = {"-p", "--port"}, description = "port", required =  false)
    int smtpPort=2525;

    @Parameter(names = {"--ssl"}, description = "is ssl", required =  false)
    boolean smtpSsl=false;

    @Parameter(names = {"--authorization"}, description = "smtp authorization", required =  false)
    boolean smtpAuthorization=false;

    @Parameter(names = {"-u", "--user"}, description = "smtp username", required =  false)
    String smtpUser;

    @Parameter(names = {"-w", "--password"}, description = "smtp password", required =  false)
    String smtpPassword;

    @Parameter(names = {"--help"}, description = "file for output errors", help = true)
    boolean isHelp;

}