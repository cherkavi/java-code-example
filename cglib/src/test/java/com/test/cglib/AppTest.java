package com.test.cglib;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import junit.framework.Assert;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import org.junit.Test;

public class AppTest {

	@Test
	public void controlTest() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		Real realObject=getReal();
		
		Object proxyRealObject=getProxy(realObject);
		
		Assert.assertNotNull(getMethod(proxyRealObject, "getIndex"));
		Assert.assertEquals(realObject.getIndex(), getMethod(realObject, "getIndex").invoke(realObject, null));
		Assert.assertEquals(realObject.getIndex(), getMethod(proxyRealObject, "getIndex").invoke(proxyRealObject, null));
		Assert.assertNotNull(getMethod(proxyRealObject, AdditionalProperty.METHOD_NAME));
		Assert.assertNull(getMethod(proxyRealObject, AdditionalProperty.METHOD_NAME).invoke(proxyRealObject, "hello"));
	}
	
	private Method getMethod(Object object, String methodName){
		for(Method eachMethod:object.getClass().getMethods()){
			if(eachMethod.getName().equals(methodName)){
				return eachMethod;
			}
		}
		return null;
	}
	

	private Object getProxy(final Real realObject) {
		Enhancer enhancer=new Enhancer();
		enhancer.setSuperclass(realObject.getClass());
		enhancer.setInterfaces(new Class[]{AdditionalProperty.class});
		enhancer.setCallback(new MethodInterceptor() {
			@Override
			public Object intercept(Object object, Method method, Object[] arguments,
					MethodProxy proxy) throws Throwable {
				if(AdditionalProperty.METHOD_NAME.equals(method.getName())){					
					System.out.println("method execute:"+Arrays.asList(arguments));
					return null;
				}else{
					return method.invoke(realObject, arguments);
				}
			}
		});
		return enhancer.create();
	}

	private Real getReal() {
		Real returnValue=new Real(5,"five");
		return returnValue;
	}
	
}
