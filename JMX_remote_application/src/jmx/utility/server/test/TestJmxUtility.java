package jmx.utility.server.test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;

import org.apache.log4j.BasicConfigurator;

import jmx.utility.JmxUtility;
import jmx.utility.server.interfaces.NotificationAware;

public class TestJmxUtility {
	// run without authentification
	// -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=8085 -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false
	// run with authentification
	// -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=8085 -Dcom.sun.management.jmxremote.authenticate=true -Dcom.sun.management.jmxremote.password.file=c:\jmxremote.password -Dcom.sun.management.jmxremote.ssl=false   
	 
	public static void main(String[] args) throws InterruptedException, MalformedObjectNameException, InstanceAlreadyExistsException, MBeanRegistrationException, NotCompliantMBeanException, NullPointerException{
		BasicConfigurator.configure();
		System.out.println("begin");
		NotificationAware<ITestNotification> notificationAware=new NotificationAware<ITestNotification>(new TestNotification());
		new JmxUtility(new TestAttribute(), 
					  new TestOperations(), 
					  notificationAware,
					  "test_value",
					  "default_remote_object",
					  "test utility");
		
		int counter=10;
		while(true){
			notificationAware.getNotificationProxyObject().notifyIntValue(++counter);
			notificationAware.getNotificationProxyObject().notifyStringValue(new SimpleDateFormat("HH:mm:ss").format(new Date()));
			TimeUnit.SECONDS.sleep(10);
		}
	}
}
