package interface_fields;

import java.lang.reflect.Modifier;

public class TestInterface {
	private static String getModifiers(int modifiers){
		StringBuilder returnValue=new StringBuilder();
		if(Modifier.isFinal(modifiers)){
			if(returnValue.length()>0)returnValue.append(", ");
			returnValue.append("final");
		}
		if(Modifier.isPublic(modifiers)){
			if(returnValue.length()>0)returnValue.append(", ");
			returnValue.append("public");
		}
		if(Modifier.isStatic(modifiers)){
			if(returnValue.length()>0)returnValue.append(", ");
			returnValue.append("static");
		}
		return returnValue.toString();
	}
	
	public static void main(String[] args){
		System.out.println("begin");
		ITest.DynamicTest dynamicTest=new ITest.DynamicTest();
		System.out.println("DynamicTest#getIntValue:"+dynamicTest.getIntValue());
		ITest.StaticTest staticTest=new ITest.StaticTest();
		ITest.StaticTest2 staticTest2=new ITest.StaticTest2();
		
		System.out.println("DynamicTest:"+getModifiers(ITest.DynamicTest.class.getModifiers()));
		System.out.println("StaticTest:"+getModifiers(ITest.StaticTest.class.getModifiers()));
		System.out.println("StaticTest2:"+getModifiers(ITest.StaticTest2.class.getModifiers()));
		System.out.println("Determinant:"+getModifiers(ITest.determinant.getClass().getModifiers()));
		System.out.println("determinant:"+ITest.determinant);
		
		ITest value=new ITest(){
			@Override
			public Object getObject() {
				return "test value";
			}
		};
		System.out.println(value.getObject());
		System.out.println("-end-");
	}
}
