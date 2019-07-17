package proxy.toy.example;

import com.thoughtworks.proxy.Invoker;
import com.thoughtworks.proxy.ProxyFactory;
import com.thoughtworks.proxy.toys.delegate.Delegating;

public class DelegatingExample {
	public static void main(String[] args){
		IMock mock=new Mock("ten value", 10);
		IMock newMock=Delegating.proxy(IMock.class).with(new Mock("eleven value",11)).build();
	}
}


