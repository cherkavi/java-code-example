package test;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class EnterPoint {
	public static void main(String[] args){
		System.out.println("EnterPoint Begin:");
		
		new ClassPathXmlApplicationContext("spring-settings.xml");
		
		System.out.println("EnterPoint end");
	}
}
