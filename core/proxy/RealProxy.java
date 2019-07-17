package proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class RealProxy implements InvocationHandler{
	@Override
	public Object invoke(Object realObject, Method method, Object[] arguments)
			throws Throwable {
		return method.invoke(realObject, arguments);
	}

}
