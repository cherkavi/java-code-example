package main;
import java.lang.management.ManagementFactory;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanNotificationInfo;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;
import javax.management.ObjectName;

import control.SimpleControlInterface;


public class EnterPoint extends NotificationBroadcasterSupport implements Runnable{
	private Thread thread;
	// java -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=8085 -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false main.EnterPoint
	private static String notificationType="main.EnterPoint";
	private static MBeanNotificationInfo[] notificationInfo=new MBeanNotificationInfo[]{new MBeanNotificationInfo(new String[]{notificationType}, "javax.managment.Notification", "this is simple Counter")};
	public static void main(String[] args) throws MalformedObjectNameException, NullPointerException, InstanceAlreadyExistsException, MBeanRegistrationException, NotCompliantMBeanException{
		// create objects
		SimpleControlInterface controlInterface=new SimpleControlInterface(notificationInfo);
		EnterPoint enterPoint=new EnterPoint();
		controlInterface.setEnterPoint(enterPoint);
		// register ManagedBean
		
		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		ObjectName name = new ObjectName("control:type=SimpleControlInterface");
		mbs.registerMBean(controlInterface, name);
		
		// run Thread
		(new EnterPoint()).startThread();
	}
	
	public EnterPoint(){
		thread=new Thread(this);
	}
	
	public void startThread(){
		this.thread.start();
	}
	
	private SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");
	private int sendIndex=0;
	
	public void run(){
		int counter=0;
		System.out.println("begin");
		while(true){
			counter++;
			System.out.println("Pulse:"+sdf.format(new Date()));
			if(counter%10==0){
				System.out.println("Send notification");
				this.sendNotification(new Notification(notificationType,this, sendIndex++));
			}
			// check for exit
			try{
				Thread.sleep(500);
			}catch(InterruptedException ex){
				break;
			};
			if(Thread.interrupted()==true){
				break;
			}
		}
	}
	
	@Override
	public MBeanNotificationInfo[] getNotificationInfo() {
		return notificationInfo;
	}
	
	public void printMessage(String message){
		SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");
		System.out.println(sdf.format(new Date())+"   "+message);
	}
}
