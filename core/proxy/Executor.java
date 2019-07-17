package proxy;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class Executor {
	public static void main(String[] args){
		Real real=new Real();
		printAllMethods(real);
		
		Real anotherReal=(Real) Proxy.newProxyInstance(real.getClass().getClassLoader(), new Class[]{Real.class}, new RealProxy());
		printAllMethods(anotherReal);
	}
	
	private static void printAllMethods(Object object){
		Method[] methods=object.getClass().getMethods();
		System.out.println(" --- methods for object: "+object.getClass().getSimpleName()+" ---");
		for(Method method:methods){
			System.out.println("Method: "+method.getName());
		}
		System.out.println(" --- methods end ---");
	}
}
