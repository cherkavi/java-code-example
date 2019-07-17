package jxpath_example;

import org.apache.commons.jxpath.JXPathContext;

public class Analisator {
	
	public static void main(String[] args){
		System.out.println("begin");
		// build the object 
		SimpleBean bean=SimpleBean.Builder.build();
		JXPathContext context=JXPathContext.newContext(bean);
		
		System.out.println("Original: "+bean);System.out.println("------------------");
		
		context.setValue("/booleanValue", Boolean.FALSE);
		System.out.println("Bean: "+bean);System.out.println("---------booleanValue---------");
		
		// wrong type - nothing happen 
		context.setValue("/booleanValue", context.getValue("/simpleObject/tempString"));
		System.out.println("Bean: "+bean);System.out.println("---------set worng type to boolanValue---------");
		
		context.setValue("/simpleObject/tempString", context.getValue("/booleanValue"));
		System.out.println("Bean: "+bean);System.out.println("---------/simpleObject/tempString---------");
		
		System.out.println("WrongWay: "+context.getValue("/worngWay"));
		
		System.out.println("-end-");
	}
}
