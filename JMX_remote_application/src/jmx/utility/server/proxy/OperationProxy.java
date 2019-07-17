package jmx.utility.server.proxy;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.management.MBeanOperationInfo;
import javax.management.MBeanParameterInfo;

import jmx.utility.server.description_decorator.AMBeanOperation;
import jmx.utility.server.description_decorator.AMBeanOperationParameter;
import jmx.utility.server.description_decorator.AMBeanOperationSkip;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;

public class OperationProxy {
	private MBeanOperationInfo operationInfo;
	private Method method;
	private String name;
	
	private OperationProxy(MBeanOperationInfo operationInfo,
						String methodName,
						Method method){
		this.operationInfo=operationInfo;
		this.method=method;
		this.name=methodName;
	}
	
	private static String getDescription(Method method){
		if(method!=null){
			AMBeanOperation annotation=method.getAnnotation(AMBeanOperation.class);
			if(annotation!=null){
				return annotation.description();
			}
		}
		return null;
	}
	
	/**
	 * get list of {@link OperationProxy} from object (IMBeanMethods)
	 * @param object
	 * @return
	 */
	public static List<OperationProxy> getFromObject(Object object){
		Validate.notNull(object);
		Method[] methods=object.getClass().getMethods();
		List<OperationProxy> returnValue=new ArrayList<OperationProxy>(methods.length);
		for(Method method:methods){
			if((!isObjectMethod(method)&&(!isSkippedMethod(method)))){
				returnValue.add(new OperationProxy(
						new MBeanOperationInfo(method.getName(),
											   getDescription(method),
											   getMBeanParameters(method),
											   method.getReturnType().getName(),
											   MBeanOperationInfo.UNKNOWN),
						method.getName(),
						method));
			}
		}
		return returnValue;
	}
	
	private static boolean isSkippedMethod(Method method) {
		return method.getAnnotation(AMBeanOperationSkip.class)!=null;
	}

	private static String getDescriptionFromAnnotation(Annotation[][] annotations, int counter){
		if(annotations.length>counter){
			if(annotations[counter].length>=1){
				if(annotations[counter][0] instanceof AMBeanOperationParameter){
					return ((AMBeanOperationParameter)annotations[counter][0]).description();
				}
			}
		}
		return null;
	}

	private static MBeanParameterInfo[] getMBeanParameters(Method method) {
		Annotation[][] annotations=method.getParameterAnnotations();
		Class<?>[] classes=method.getParameterTypes();
		MBeanParameterInfo[] returnValue=new MBeanParameterInfo[classes.length];
		try{
			for(int counter=0;counter<classes.length;counter++){
				returnValue[counter]=new MBeanParameterInfo("P"+counter, 
															classes[counter].getName(), 
															getDescriptionFromAnnotation(annotations, counter)
															);
			}
		}catch(Exception ex){
			Logger.getLogger(OperationProxy.class).error("getMBeanParameters Exception:"+ex.getMessage());
		}
		return returnValue;
	}

	/**
	 * check method for object
	 * @param checkMethod
	 * @return
	 * <ul>
	 * 	<li><b>true</b> -
	 * 		<ul>
	 * 			<li>{@link Object#toString()}</li>
	 * 			<li>{@link Object#hashCode()}</li>
	 * 			<li>{@link Object#equals(Object)}</li>
	 * 			<li>{@link Object#getClass()}</li>
	 * 			<li>{@link Object#notify()}</li>
	 * 			<li>{@link Object#notifyAll()}</li>
	 * 			<li>{@link Object#wait()}</li>
	 * 		</ul> 
	 * 	</li>
	 * 	<li><b>false</b> - otherwise</li>
	 * </ul>
	 */
	private static boolean isObjectMethod(Method checkMethod) {
		return objectMethods.indexOf(checkMethod.getName())>=0;
	}
	private static List<String> objectMethods=Arrays.asList("toString","equals","hashCode","getClass","notify","notifyAll","wait");

	public MBeanOperationInfo getOperationInfo() {
		return this.operationInfo;
	}

	public Method getMethod() {
		return this.method;
	}

	public String getName() {
		return this.name;
	}
}
