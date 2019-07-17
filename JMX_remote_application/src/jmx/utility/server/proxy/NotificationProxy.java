package jmx.utility.server.proxy;

import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.management.MBeanNotificationInfo;

import jmx.utility.server.description_decorator.AMBeanNotification;
import jmx.utility.server.description_decorator.AMBeanNotificationSkip;
import jmx.utility.server.interfaces.INotification;

public class NotificationProxy {
	private static List<String> blackMethods=Arrays.asList("toString","equals","hashCode","getClass","notify","notifyAll","wait", "invoke");
	
	private MBeanNotificationInfo notificationInfo;
	private String methodName;
	private String parameterTypeName;
	
	public MBeanNotificationInfo getNotification(){
		return this.notificationInfo;
	}
	
	private NotificationProxy(MBeanNotificationInfo notificationInfo, 
							  String methodName,
							  String parameterTypeName){
		this.notificationInfo=notificationInfo;
		this.methodName=methodName;
		this.parameterTypeName=parameterTypeName;
	}

	private static String getDescriptionFromMethod(Method method){
		if(method!=null){
			AMBeanNotification notification=method.getAnnotation(AMBeanNotification.class);
			if(notification!=null){
				return notification.description();
			}
		}
		return null;
	}

	private static boolean isSkippedMethod(Method method) {
		return method.getAnnotation(AMBeanNotificationSkip.class)!=null;
	}


	public static List<NotificationProxy> getFromObject(INotification notificationObject) {
		if(notificationObject==null){
			return null;
		}
		List<NotificationProxy> returnValue=new ArrayList<NotificationProxy>();
		Method[] methods=notificationObject.getClass().getMethods();
		for(Method method:methods){
			if((blackMethods.indexOf(method.getName())<0)&&(method.getParameterTypes().length==1)&&(!isSkippedMethod(method))){
				returnValue.add(new NotificationProxy(new MBeanNotificationInfo(new String[]{method.getParameterTypes()[0].getName()}, 
																				"javax.management.Notification", 
																				getDescriptionFromMethod(method)),
													  method.getName(),
													  method.getParameterTypes()[0].getName()
													  )
								);  
			}
		}
		return returnValue;
	}
	

	public String getMethodName() {
		return this.methodName;
	}

	public String getParameterTypeName() {
		return this.parameterTypeName;
	}
}
