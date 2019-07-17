package proxy.toy.example;

import java.lang.reflect.Method;

import com.thoughtworks.proxy.toys.decorate.Decorating;
import com.thoughtworks.proxy.toys.decorate.Decorator;

/**
 *  http://proxytoys.codehaus.org/example-code.html
 */
public class DecoratorExample {
	public static void main(String[] args){
		System.out.println("begin");
		Decorator<IMock> decorator=new Decorator<IMock>(){
			private final static long serialVersionUID=1L;
			@Override
			public Object[] beforeMethodStarts(IMock proxy, 
											   Method method,
											   Object[] args) {
				System.out.println("	beforeMethodStart:"+method);
				return args;
			}
		};
		IMock mock=Decorating.proxy(IMock.class).with(new Mock("temp value",3)).visiting(decorator).build();
		System.out.println("Mock#getIntValue: "+mock.getIntValue());
		System.out.println("Mock#getValue:"+mock.getValue());
		System.out.println("end");
	}
	
	
}

