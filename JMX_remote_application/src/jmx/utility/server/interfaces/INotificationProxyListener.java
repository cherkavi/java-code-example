package jmx.utility.server.interfaces;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * interface for receive notification from Proxy object, which wrapped original object and call {@link #preInvokeMethod(Object, Method, Object[])} before execute method on original object 
 */
public interface INotificationProxyListener {
	public static List<String> objectMethods=Arrays.asList("toString","equals","hashCode","getClass","notify","notifyAll","wait");
	/** notify about call the method  */
	public void preInvokeMethod(Object object, Method method, Object[] args);
}
