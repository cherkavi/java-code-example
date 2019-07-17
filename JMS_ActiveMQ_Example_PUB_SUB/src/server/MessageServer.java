package server;

import java.util.Properties;

import javax.jms.Connection;
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

public class MessageServer implements MessageListener {
	public static void main(String[] args) throws Exception{
		new MessageServer();
	}
	
	
	private Connection connection;
	private Session session;
	private MessageConsumer consumer;
	private MessageProducer producer;
	  	
	public MessageServer() throws Exception{
	    ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
	    connection = connectionFactory.createConnection();
	    session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
	    System.out.println("create our request and response queues");
	    Topic request = session.createTopic("request.queue");
	    Topic response = session.createTopic("response.queue");
	    System.out.println("and attach a consumer and producer to them");
	    consumer = session.createConsumer(request);
	    consumer.setMessageListener(this);
	    producer = session.createProducer(response);
	    System.out.println("start your engines...");
	    connection.start();	
	}
	
	public void destroy() throws Exception {
	    session.close();
	    connection.close();
	}
	
	
	@Override
	public void onMessage(Message message) {
		try {
		      if (message instanceof TextMessage) {
		        String messageText = ((TextMessage) message).getText();
		        System.out.println("Server: Got request [" + messageText + "]");
		        Message responseMessage = 
		          session.createTextMessage("Processed " + messageText);
		        if (message.getJMSCorrelationID() != null) {
		          // pass it through
		          responseMessage.setJMSCorrelationID(message.getJMSCorrelationID());
		        }
		        producer.send(responseMessage);
		      }
		    } catch (JMSException e) {
		      System.err.println(e);
		    }	
	}

}
