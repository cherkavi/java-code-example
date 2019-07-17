package proxy.toy.example;

import java.lang.reflect.Method;

import com.thoughtworks.proxy.toys.decorate.Decorating;
import com.thoughtworks.proxy.toys.decorate.Decorator;

/**
 *  http://proxytoys.codehaus.org/example-code.html
 */
public class ProxyToy {
	public static void main(String[] args){
		System.out.println("begin");
		Decorator<IMock> decorator=new Decorator<IMock>(){
			private final static long serialVersionUID=1L;
			@Override
			public Object[] beforeMethodStarts(IMock proxy, 
											   Method method,
											   Object[] args) {
				System.out.println("beforeMethodStart");
				// return super.beforeMethodStarts(proxy, method, args);
				return null;
			}
		};
		IMock mock=Decorating.proxy(IMock.class).with(new Mock("temp value",3)).visiting(decorator).build();
		System.out.println("Mock#getIntValue: "+mock.getIntValue());
		System.out.println("Mock#getValue:"+mock.getValue());
		System.out.println("end");
	}
	
	
}

class Mock implements IMock{
	private String value;
	private int intValue;
	
	public Mock(String value, int intValue) {
		super();
		this.value = value;
		this.intValue = intValue;
	}
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	public int getIntValue() {
		return intValue;
	}
	public void setIntValue(int intValue) {
		this.intValue = intValue;
	}
}

interface IMock{
	public String getValue();
	public void setValue(String value);
	
	public int getIntValue();
	public void setIntValue(int intValue);
}
