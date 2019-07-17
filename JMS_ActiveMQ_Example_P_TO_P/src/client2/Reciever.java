package client2;

import java.util.Properties;
import javax.jms.*;
import javax.naming.*;

import org.apache.activemq.ActiveMQConnectionFactory;



public class Reciever implements MessageListener {
	public static void main(String[] args) throws Exception{
		new Reciever();
	}

	
	public Reciever(){
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
		    
		    MessageConsumer consumer=session.createConsumer(queue);
		    
		    consumer.setMessageListener(this);
			connection.start();
			
		}catch(Exception ex){
			System.err.println("Sender Exception: "+ex.getMessage());
		}finally{
			try{
				
			}catch(Exception ex){};
		}
		
		System.out.println("-end-");
	}


	@Override
	public void onMessage(Message message) {
		System.out.println("onMessage Get:");
		TextMessage textMessage=(TextMessage)message;
		try{
			System.out.println(textMessage.getText());
		}catch(Exception ex){
			System.err.println("onMessage Exception: "+ex.getMessage());
		}
		
	}
}

