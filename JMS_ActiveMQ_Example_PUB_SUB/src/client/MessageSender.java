package client;

import java.util.Properties;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

/** http://sujitpal.blogspot.com/2007/12/jms-patterns-with-activemq.html  */
public class MessageSender {

	public static void main(String[] args) throws Exception{
		System.out.println("begin");
		new MessageSender("hello from client");
		System.out.println("-end-");
	}
	
	private Connection connection;
	private Session session;
	private MessageProducer producer;
	private MessageConsumer consumer;
	  
	public MessageSender(String messageText) throws Exception{
		// set 'er up
	    ActiveMQConnectionFactory connectionFactory = 
	      new ActiveMQConnectionFactory("tcp://localhost:61616");
	    connection = connectionFactory.createConnection();
	    session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
	    // create our request and response queues
	    Topic request = session.createTopic("request.queue");
	    // and attach a consumer and producer to them
	    producer = session.createProducer(request);
	    producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
	    // and start your engines...
	    connection.start();
	    producer.send(session.createTextMessage(messageText));
	    session.close();
	    connection.close();
	}
	
	
}
