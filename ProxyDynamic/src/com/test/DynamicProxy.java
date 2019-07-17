package com.test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class DynamicProxy<T> implements InvocationHandler{
	private T object;
	public DynamicProxy(T object){
		this.object=object;
	}

	@SuppressWarnings("unchecked")
	public static <T> T newInstance(T object){
		return (T) Proxy.newProxyInstance(DynamicProxy.class.getClassLoader(), 
									  		object.getClass().getInterfaces(), 
									  		new DynamicProxy<T>(object));
	}
	
	public static void main(String[] args) {
		IMock mock=new Mock(10, "temp value");
		IMock newMock=(IMock)DynamicProxy.newInstance(mock);
		System.out.println("begin");
		System.out.println("	Mock#IntValue:"+mock.getIntValue());
		System.out.println("	Mock#StringValue:"+mock.getStringValue());
		System.out.println("	MockNew#IntValue:"+newMock.getIntValue());
		System.out.println("	MockNew#StringValue:"+newMock.getStringValue());
		System.out.println("-end-");
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		System.out.println("#invoke proxy: ");
		System.out.println("#invoke method: "+method);
		System.out.println("#invoke args: "+Arrays.toString(args));
		System.out.println("----------------------");
		return method.invoke(object, args);
	}
	
}


