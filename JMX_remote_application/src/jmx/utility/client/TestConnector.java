package jmx.utility.client;

import java.io.File;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.management.MBeanInfo;
import javax.management.MBeanServerConnection;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

public class TestConnector {

/*	private JMXConnector getLocalConnection(VirtualMachine vm) throws Exception {
		Properties props = vm.getAgentProperties();
		String connectorAddress = props.getProperty(CONNECTOR_ADDRESS_PROPERTY);
		if (connectorAddress == null) {
			props = vm.getSystemProperties();
		    String home = props.getProperty("java.home");
		    String agent = home + File.separator + "lib" + File.separator + "management-agent.jar";
		    vm.loadAgent(agent);
		    props = vm.getAgentProperties();
		    connectorAddress = props.getProperty(CONNECTOR_ADDRESS_PROPERTY);
		}
		JMXServiceURL url = new JMXServiceURL(connectorAddress);
		return JMXConnectorFactory.connect(url);
	}*/
	
	private static JMXConnector getRemoteConnection(String host, int port, String user, String password) throws Exception {
		JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://"+host+":"+port+"/jmxrmi");
		if((user!=null)&&(password!=null)){
			final Map<String, String[]> environment = new HashMap<String, String[]>();
			environment.put(JMXConnector.CREDENTIALS, new String[] {user, password} );
			return JMXConnectorFactory.connect(url, environment);
		}else{
			return JMXConnectorFactory.connect(url);
		}
	}
	
	public static void main(String[] args) throws Exception{
		BasicConfigurator.configure();
		Logger logger=Logger.getLogger(TestConnector.class);
		logger.info("begin");
		
		logger.debug("create connector");
		String hostName="127.0.0.1";
		int portNum=8085;
		JMXConnector connector=getRemoteConnection(InetAddress.getLocalHost().getHostName() ,portNum, null, null );
		
		logger.debug("create serverConnection");
		MBeanServerConnection serverConnection=connector.getMBeanServerConnection();

		logger.debug("get object instances");
		Iterator<ObjectInstance> objectInstances=serverConnection.queryMBeans(new ObjectName("*:*"), null).iterator();
		
		while(objectInstances.hasNext()){
			ObjectInstance objectInstance=objectInstances.next();
			logger.info("ObjectName: "+objectInstance.getObjectName());
			MBeanInfo beanInfo=serverConnection.getMBeanInfo(objectInstance.getObjectName());
			logger.info("	BeanInfo#className:"+beanInfo.getClassName());
			logger.info("	BeanInfo#description:"+beanInfo.getDescription());
			logger.info("	BeanInfo#Attributes:"+Arrays.toString(beanInfo.getAttributes()));
			logger.info("	BeanInfo#Notifications:"+Arrays.toString(beanInfo.getNotifications()));
			logger.info("	BeanInfo#Operations:"+Arrays.toString(beanInfo.getOperations()));
		}
		
		logger.info("-end-");
	}
	
}
