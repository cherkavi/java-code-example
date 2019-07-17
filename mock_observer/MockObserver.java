package mock_observer;

import java.lang.reflect.Method;

import reflection_private.Mock;

public class MockObserver {
	public static void main(String[] args) throws Exception{
		printAllMethods(new Mock());
		System.out.println("-------------");
		printAllMethods(new Class1());
		
		
		 String aClass = "mock_observer.Class1";
		    String aMethod1 = "class1Method1";
		    String aMethod2 = "class1Method2";
		    Class params[] = {};
		    Object paramsObj[] = {};

		    Class thisClass = Class.forName(aClass);
		    Object iClass = thisClass.newInstance();
		    Method thisMethod1 = thisClass.getDeclaredMethod(aMethod1, params);
		    thisMethod1.setAccessible(true);
		    System.out.println(thisMethod1.invoke(iClass, paramsObj).toString());
		    Method thisMethod2 = thisClass.getDeclaredMethod(aMethod2, params);
		    thisMethod2.setAccessible(true);
		    System.out.println(thisMethod2.invoke(iClass, paramsObj).toString());		
	}
	
	private static void testProtected(Mock mock){
		Mock temp=new Mock(){
			public String getProtectedValue222(){
				return super.getProtectedValue();
			}
		};
	}
	
	private static void printAllMethods(Object object){
		Class<?> clazz=object.getClass();
		Method[] methods=clazz.getMethods();
		for(Method met:methods){
			System.out.println("Method: "+met);
		}
	}
}


class Class1 {
	  private String class1Method1() {
	    return "Method1";
	  }

	  protected String class1Method2() {
	    return "Method2";
	  }
	}