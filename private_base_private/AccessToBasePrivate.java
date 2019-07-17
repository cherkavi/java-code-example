package private_base_private;

import java.lang.reflect.Method;

public class AccessToBasePrivate {
	
	public static void main(String[] args) throws Exception {
		/** access to private class  */
		Child child=new Child();
		Method[] methods=child.getClass().getDeclaredMethods();
		for(int index=0;index<methods.length;index++){
			if(methods[index].getName().equals("getIntValue")){
				Method method=methods[index];
				method.setAccessible(true);
				System.out.println("getIntValue : "+method.invoke(child));
			}
		}
		// ----------------
		/** access to base private class - only through certain class declaration  */
		methods=Parent.class.getDeclaredMethods();
		for(int index=0;index<methods.length;index++){
			if(methods[index].getName().equals("getIntValue")){
				Method method=methods[index];
				method.setAccessible(true);
				System.out.println("getIntValue : "+method.invoke(child));
			}
		}
	}
}
