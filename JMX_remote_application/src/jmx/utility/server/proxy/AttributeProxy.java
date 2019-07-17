package jmx.utility.server.proxy;

import java.beans.PropertyDescriptor;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.management.IntrospectionException;
import javax.management.MBeanAttributeInfo;

import jmx.utility.server.description_decorator.AMBeanAttribute;
import jmx.utility.server.description_decorator.AMBeanAttributeSkip;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;

/**
 * this is MBean#Attribute proxy object
 */
public class AttributeProxy {
	private MBeanAttributeInfo parameterInfo;
	private Method methodRead;
	private Method methodWrite;
	
	/** get Name of property ( without get/set prefix ) */
	public String getName(){
		return this.parameterInfo.getName();
	}
	
	private AttributeProxy(MBeanAttributeInfo parameterInfo, 
						   Method methodRead,
						   Method methodWrite){
		Validate.notNull(parameterInfo);
		Validate.notNull(methodWrite);
		Validate.notNull(methodRead);
		this.parameterInfo=parameterInfo;
		this.methodRead=methodRead;
		this.methodWrite=methodWrite;
	}
	
	public MBeanAttributeInfo getParameterInfo(){
		return this.parameterInfo;
	}
	
	public Method getMethodRead(){
		return this.methodRead;
	}
	
	public Method getMethodWrite(){
		return this.methodWrite;
	}
	
	private static String getDescriptionFromMethod(Method method){
		if(method!=null){
			AMBeanAttribute annotation=method.getAnnotation(AMBeanAttribute.class);
			if(annotation!=null){
				return annotation.description();
			}
		}
		return null;
	}
	
	private static String getFirstNotNull(String ... elements){
		if(elements!=null){
			for(int counter=0;counter<elements.length;counter++){
				if(elements[counter]!=null){
					return elements[counter];
				}
			}
		}
		return null;
	}

	private static boolean isSkippedMethod(Method method) {
		return method.getAnnotation(AMBeanAttributeSkip.class)!=null;
	}


	/**
	 * parse attributes from object
	 * @param object
	 * @return - list of Proxy Attributes 
	 */
	public static List<AttributeProxy> parseAttributes(Object object) {
		List<AttributeProxy> returnValue=new ArrayList<AttributeProxy>();
		
		PropertyDescriptor[] descriptors=PropertyUtils.getPropertyDescriptors(object.getClass());
		for(int counter=0;counter<descriptors.length;counter++){
			if(!descriptors[counter].getName().equals("class")){
				String name=descriptors[counter].getName();
				Method methodRead=descriptors[counter].getReadMethod();
				Method methodWrite=descriptors[counter].getWriteMethod();
				String description=getFirstNotNull(getDescriptionFromMethod(methodRead), getDescriptionFromMethod(methodWrite));  
				if((name!=null)&&((methodRead!=null)||(methodWrite!=null)) && (notHaveSkipAnnotation(methodRead, methodWrite)) ){
					try {
						returnValue.add(new AttributeProxy(new MBeanAttributeInfo(name, 
																				  description,
																				  methodRead, 
																				  methodWrite),
														   methodRead, 
														   methodWrite));
					} catch (IntrospectionException ex) {
						Logger.getLogger(AttributeProxy.class).error("parse Attribute (\""+name+"\") Exception:"+ex.getMessage(), ex);
					}
				}
			}
		}
		return returnValue;
	}

	private static boolean notHaveSkipAnnotation(Method methodRead,Method methodWrite) {
		boolean notHave=true;
		if(methodRead!=null){
			notHave=notHave && (!isSkippedMethod(methodRead));
		}
		if(methodWrite!=null){
			notHave=notHave && (!isSkippedMethod(methodWrite));
		}
		return notHave;
	}

}

