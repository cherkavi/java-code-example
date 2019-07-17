package client1;


import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Properties;
import javax.jms.*;
import javax.naming.*;
import org.apache.activemq.ActiveMQConnectionFactory;

public class Sender {
	
	public static void main(String[] args) throws Exception{
		new Sender();
	}
	
	public Sender() throws Exception {
		System.out.println("begin");

		Properties props = new Properties();
		props.setProperty(Context.INITIAL_CONTEXT_FACTORY,"org.apache.activemq.jndi.ActiveMQInitialContextFactory");
		props.setProperty(Context.PROVIDER_URL, "tcp://localhost:61616");
		// props.setProperty("java.naming.security.principal", "system");
		// props.setProperty("java.naming.security.credentials", "manager");
		// Context context=null;
		ActiveMQConnectionFactory  connectionFactory = null;
		try{
			// context=new InitialContext(props);
			// Class.forName("org.activemq.jndi.ActiveMQInitialContextFactory");
			
			connectionFactory =new ActiveMQConnectionFactory("tcp://localhost:61616"); // (ActiveMQConnectionFactory) context.lookup("ActiveMQConnectionFactory");   
			Connection connection = connectionFactory.createConnection();
		    Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		    Queue queue=session.createQueue("queue_for_sender");
			MessageProducer producer=session.createProducer(queue);
			
			connection.start();
			
			TextMessage message=session.createTextMessage("this is temp message: "+sdf.format(new Date()));
			producer.send(message);
			
		}catch(Exception ex){
			System.err.println("Sender Exception: "+ex.getMessage());
		}finally{
			try{
				
			}catch(Exception ex){};
		}
		
		System.out.println("-end-");
	}
	
	private SimpleDateFormat sdf=new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
}
