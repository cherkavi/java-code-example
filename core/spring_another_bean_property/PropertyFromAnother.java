package spring_another_property;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class PropertyFromAnother {
	public static void main(String[] args){
		System.out.println("begin");
		ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("spring_another_property/description.xml");
		Bean bean=(Bean)context.getBean("bean");
		System.out.println("Bean:"+bean);
		AnotherBean anotherBean=(AnotherBean)context.getBean("another_bean");
		System.out.println("Bean:"+anotherBean);
		System.out.println("-end-");
	}
}
