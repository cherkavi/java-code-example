package com.test;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;

import org.apache.log4j.BasicConfigurator;

import jmx.utility.JmxUtility;
import jmx.utility.server.interfaces.NotificationAware;

import com.test.jmx_controller.AttributeJobberName;
import com.test.jmx_controller.INotificationJobber;
import com.test.jmx_controller.OperationJobber;
import com.test.jobbers.FirstJobber;
import com.test.jobbers.IJobber;
import com.test.jobbers.JobberManager;
import com.test.jobbers.SecondJobber;
import com.test.jobbers.ThirdJobber;

public class JobberExecuter {
	public static void main(String[] args) throws InstanceAlreadyExistsException, MBeanRegistrationException, NotCompliantMBeanException, MalformedObjectNameException, NullPointerException{
		BasicConfigurator.configure();
		// java -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=8085 -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false main.EnterPoint
		System.out.println("begin");
		
		// jmx features:
		// notify: start new jobber, jobber end own work,
		// operation: stop the jobber
		// attributes: get execute name: AttributeJobberName
		IJobber[] jobbers=new IJobber[]{new FirstJobber(15), 
										new SecondJobber(15), 
										new ThirdJobber(15)};
		JobberManager controlRealisation=new JobberManager();
		AttributeJobberName attributeName=new AttributeJobberName();
		controlRealisation.setAttributeJobberName(attributeName);
		
		NotificationAware<INotificationJobber> notificationAware=new NotificationAware<INotificationJobber>(new INotificationJobber(){
			/** started new jobber */
			public void changeJobberName(String name){};
			/** start, stop, pause */
			public void changeJobberState(String name){}
		});
		OperationJobber operation=new OperationJobber();
		operation.setJobberManager(controlRealisation);
		
		new JmxUtility(attributeName, 
			  	   	   operation,  
			  	   	   notificationAware, 
			  	   	   "jobber_executor",
			  	   	   "managed_object",
			  	   	   "this is controller for jobber executor ");
		
		controlRealisation.setNotificationJobber(notificationAware.getNotificationProxyObject());
		controlRealisation.sequencyRunAllJobbers(jobbers);
		
		
		System.out.println("end");
	}
}
