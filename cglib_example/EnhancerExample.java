package cglib_example;

import java.lang.reflect.Method;
import java.util.Arrays;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class EnhancerExample {
	public static void main(String[] args){
		System.out.println("begin");
		IMock mock=(IMock)Enhancer.create(IMock.class, new MethodInterceptor(){
			@Override
			public Object intercept(Object arg0, 
									Method arg1, 
									Object[] arg2,
									MethodProxy arg3) throws Throwable {
				System.out.println("Object object:");
				System.out.println("Object method:"+arg1);
				System.out.println("Object args:"+Arrays.toString(arg2));
				System.out.println("Object methodProxy:"+arg3);
				return null;
			}
		});
		System.out.println("Mock#intValue:"+mock.getIntValue());
		System.out.println("Mock#StringValue:"+mock.getStringValue());
		System.out.println("-end-");
	}
}

class Mock implements IMock{
	private int intValue;
	private String stringValue;
	
	public int getIntValue() {
		return intValue;
	}
	public void setIntValue(int intValue) {
		this.intValue = intValue;
	}
	public String getStringValue() {
		return stringValue;
	}
	public void setStringValue(String stringValue) {
		this.stringValue = stringValue;
	}
	
}

interface IMock{
	public int getIntValue();
	public void setIntValue(int intValue);
	public String getStringValue();
	public void setStringValue(String stringValue);
}
