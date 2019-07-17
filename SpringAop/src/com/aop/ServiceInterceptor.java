package com.aop;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class ServiceInterceptor implements MethodInterceptor{

	@Override
	public Object invoke(MethodInvocation inv) throws Throwable {
		Method method = inv.getMethod();
        Object obj = inv.getThis();
        System.out.println("= BEFORE METHOD [" + method.getName() + 
                "] from {" + obj +"} now called...");
 
        inv.proceed();
 
        System.out.println("= AFTER METHOD [" + method.getName() + 
                "] from {" + obj +"} now called...");
 
        return null;
	}

}
