package jmx.utility.server.interfaces;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class NotificationProxyIntercepter<T> implements java.lang.reflect.InvocationHandler{
	private T object;
	private INotificationProxyListener[] listeners;
	
	public NotificationProxyIntercepter(T object, INotificationProxyListener ... listeners){
		this.object=object;
		this.listeners=listeners;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		if( (this.listeners!=null) && ( INotificationProxyListener.objectMethods.indexOf(method.getName())<0) ){
			for(INotificationProxyListener listener:this.listeners){
				listener.preInvokeMethod(object, method, args);
			}
		}
		return method.invoke(object, args);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getNotificationProxy(T object, INotificationProxyListener ... listeners){
		return (T)Proxy.newProxyInstance(NotificationProxyIntercepter.class.getClassLoader(), 
										 object.getClass().getInterfaces(), 
										 new NotificationProxyIntercepter(object, listeners));
	}
}
