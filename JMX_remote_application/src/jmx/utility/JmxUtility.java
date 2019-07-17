package jmx.utility;

import java.lang.management.ManagementFactory;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.AttributeNotFoundException;
import javax.management.DynamicMBean;
import javax.management.InstanceAlreadyExistsException;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.MBeanNotificationInfo;
import javax.management.MBeanOperationInfo;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;
import javax.management.ObjectName;
import javax.management.ReflectionException;

import jmx.utility.server.interfaces.IMBeanAttributes;
import jmx.utility.server.interfaces.IMBeanOperations;
import jmx.utility.server.interfaces.INotificationProxyListener;
import jmx.utility.server.interfaces.NotificationAware;
import jmx.utility.server.interfaces.NotificationProxyIntercepter;
import jmx.utility.server.proxy.AttributeProxy;
import jmx.utility.server.proxy.NotificationProxy;
import jmx.utility.server.proxy.OperationProxy;

import org.apache.log4j.Logger;

/**
 * class utility for help in execute method through JMX
 * 	run parameters: -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=8085 -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false
 */
public class JmxUtility extends NotificationBroadcasterSupport implements DynamicMBean, INotificationProxyListener {
	private Logger logger=Logger.getLogger(this.getClass());
	/** MBean attributes object for set or get attributes  */
	private IMBeanAttributes attributesObject=null;
	/** list of AttributeProxy */
	private List<AttributeProxy> attributes=null;
	
	private IMBeanOperations methodObject=null;
	private List<OperationProxy> methods=null;
	
	private List<NotificationProxy> notifications;

	private String description=null;
	
	/**
	 * @param mbeanAttributes - object for parse all methods as POJO (find setters and getters ) 
	 * @param mbeanMethods - object for parse all methods for invoke through JMX 
	 * @param notification - (!!! changed after object was created !!! ) object for parse all methods for notification ( is somethig write to it )
	 * @param sectionName - parameter for {@link ObjectName}
	 * @param beanName - parameter for {@link ObjectName }
	 * @param description - JMX.MBean description  
	 * @throws NullPointerException 
	 * @throws MalformedObjectNameException 
	 * @throws NotCompliantMBeanException 
	 * @throws MBeanRegistrationException 
	 * @throws InstanceAlreadyExistsException 
	 */
	@SuppressWarnings("unchecked")
	public JmxUtility(IMBeanAttributes mbeanAttributes, 
					  IMBeanOperations mbeanMethods, 
					  NotificationAware notificationAware,
					  String sectionName,
					  String beanName,
					  String description
					  ) throws MalformedObjectNameException, NullPointerException, InstanceAlreadyExistsException, MBeanRegistrationException, NotCompliantMBeanException {
		this.attributesObject=mbeanAttributes;
		if(mbeanAttributes!=null){
			logger.debug("parse Attributes ");
			this.attributes=AttributeProxy.parseAttributes(this.attributesObject);
		}
		this.methodObject=mbeanMethods;
		if(mbeanMethods!=null){
			logger.debug("parse Methods ");
			this.methods=OperationProxy.getFromObject(this.methodObject);
		}
		if((notificationAware!=null)&&(notificationAware.getNotificationProxyObject()!=null)){
			logger.debug("parse Notification ");
			this.notifications=NotificationProxy.getFromObject(notificationAware.getNotificationProxyObject());
			notificationAware.setNotificationProxyObject(NotificationProxyIntercepter.getNotificationProxy(notificationAware.getNotificationProxyObject(), this));
		}
		this.description=description;
		logger.debug("register MBean ");
		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		ObjectName name = new ObjectName(sectionName+":type="+beanName);
		mbs.registerMBean(this, name);
	}
	
	/** get Attribute from {@link #attributes}*/
	private AttributeProxy getAttributeProxyByName(String name){
		if(this.attributes!=null)
		for(AttributeProxy attribute:this.attributes){
			if(attribute.getName().equals(name)){
				return attribute;
			}
		}
		return null;
	}
	
	@Override
	public Object getAttribute(String attrName) throws AttributeNotFoundException,
													   MBeanException, 
													   ReflectionException {
		AttributeProxy attribute=this.getAttributeProxyByName(attrName);
		if(attribute!=null){
			try{
				return attribute.getMethodRead().invoke(attributesObject);
			}catch(Exception ex){
				logger.error("getAttribute Exception: "+ex.getMessage(), ex);
			}
		}
		return null;
	}

	
	@Override
	public AttributeList getAttributes(String[] attrNames) {
		AttributeList returnValue=new AttributeList();
		for(String attrName:attrNames){
			try{
				returnValue.add(new Attribute(attrName, getAttribute(attrName)));
			}catch(Exception ex){
				logger.error("#getAttributes ("+attrName+") Exception:"+ex.getMessage(), ex);
			}
		}
		return returnValue;
	}

	@Override
	public void setAttribute(Attribute attribute) throws AttributeNotFoundException,
			InvalidAttributeValueException, MBeanException, ReflectionException {
		AttributeProxy attributeProxy=this.getAttributeProxyByName(attribute.getName());
		if(attribute!=null){
			try{
				attributeProxy.getMethodWrite().invoke(this.attributesObject, attribute.getValue());
			}catch(Exception ex){
				logger.error("#setAttribute ("+attribute+") Exception:"+ex.getMessage(),ex);
			}
		}
	}

	@Override
	public AttributeList setAttributes(AttributeList attributeList) {
		if(attributeList!=null){
			List<Attribute> list=attributeList.asList();
			AttributeList returnValue=new AttributeList();
			for(Attribute attr:list){
				try{
					this.setAttribute(attr);
					returnValue.add(attr);
				}catch(Exception ex){
					logger.warn("error try to set Attribute: "+attr,ex);
				}
			}
			return returnValue;
		}else{
			return null;
		}
	}

	private MBeanAttributeInfo[] getArrayOfAttributeInfos(){
		if(this.attributes==null){
			return null;
		}
		List<MBeanAttributeInfo> returnValue=new ArrayList<MBeanAttributeInfo>(this.attributes.size());
		for(AttributeProxy proxy:this.attributes){
			returnValue.add(proxy.getParameterInfo());
		}
		return returnValue.toArray(new MBeanAttributeInfo[]{});
	}
	
	private MBeanOperationInfo[] getArrayOfOperations(){
		if(this.methods==null){
			return null;
		}
		List<MBeanOperationInfo> returnValue=new ArrayList<MBeanOperationInfo>(this.methods.size());
		for(OperationProxy method:this.methods){
			returnValue.add(method.getOperationInfo());
		}
		return returnValue.toArray(new MBeanOperationInfo[]{});
	}

	@Override
	public Object invoke(String methodName, 
						 Object[] arguments, 
						 String[] argumentsName)
			throws MBeanException, ReflectionException {
		OperationProxy proxy=getMethodByName(methodName);
		if(proxy!=null){
			try {
				return proxy.getMethod().invoke(this.methodObject, arguments);
			} catch (Exception e) {
				logger.error("#invoke (#"+methodName+") Exception: "+e.getMessage(), e);
			}
		}
		return null;
	}

	private OperationProxy getMethodByName(String methodName) {
		if(this.methods!=null){
			for(OperationProxy method:this.methods ){
				if(method.getName().equals(methodName)){
					return method;
				}
			}
		}
		return null;
	}

	private MBeanNotificationInfo[] getArrayOfNotifications() {
		if(this.notifications!=null){
			List<MBeanNotificationInfo> returnValue=new ArrayList<MBeanNotificationInfo>(this.notifications.size());
			for(NotificationProxy notification:this.notifications){
				returnValue.add(notification.getNotification());
			}
			return returnValue.toArray(new MBeanNotificationInfo[]{});
		}
		return null;
	}

	private MBeanInfo info=null;
	@Override
	public MBeanInfo getMBeanInfo() {
		if(this.info==null){
			this.info=new MBeanInfo(this.getClass().getName(),
					                this.description,
					                this.getArrayOfAttributeInfos(),
					                null,
					                this.getArrayOfOperations(),
					                this.getArrayOfNotifications()
					                );
		}
		return this.info;
	}

	private NotificationProxy getNotificationByName(String methodName){
		for(NotificationProxy notification:this.notifications){
			if(notification.getMethodName().equals(methodName)){
				return notification;
			}
		}
		return null;
	}
	
	private long notificationSequenceNumber=0;
	/** place for execute Notification  */
	@Override
	public void preInvokeMethod(Object object, 
								Method method, 
								Object[] args) {
		logger.debug("get notification for : "+method.getName());
		NotificationProxy notification=this.getNotificationByName(method.getName());
		if(notification!=null){
			logger.debug("send notification  Type:"+notification.getParameterTypeName()+"   Value:"+args[0]);
			this.sendNotification(new Notification(notification.getParameterTypeName(),args[0], ++notificationSequenceNumber));
		}
	}

}
