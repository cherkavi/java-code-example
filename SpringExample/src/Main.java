import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.FileSystemResource;


public class Main {
	public static void main(String[] args){
		System.out.println("begin");
		
		// Инициализация фабрики, которая отвечает за создание объектов 
		BeanFactory beanFactory = new XmlBeanFactory(new FileSystemResource("bean_description.xml"));
/*
		// Программное создание бинов 
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
		beanFactory.registerBeanDefinition("user_message_singleton", new RootBeanDefinition(user_bean.UserMessage.class,true));
		beanFactory.registerBeanDefinition("user_message_prototype", new RootBeanDefinition(user_bean.UserMessage.class,false));
			// если нужно добавлять к классам обязательные Properties
		//MutablePropertyValues asPvs = new MutablePropertyValues();
		//asPvs.addPropertyValue("userManager", new RuntimeBeanReference("simpleUserManager"));
			// если нужно добавлять аргументы конструктора 
		ConstructorArgumentValues constructorArguments=new ConstructorArgumentValues();
		constructorArguments.addIndexedArgumentValue(0, "first_default_value", "String");
		constructorArguments.addIndexedArgumentValue(1, "second_default_value", "String");

		RootBeanDefinition rootBeanDefinition=new RootBeanDefinition(user_bean.UserMessageDouble.class,false);
		rootBeanDefinition.setConstructorArgumentValues(constructorArguments);
		beanFactory.registerBeanDefinition("user_message_values",rootBeanDefinition);
		
		//beanFactory.registerBeanDefinition("simpleUserManager", new RootBeanDefinition(user_bean.UserMessage.class, null, true));
		
		// Альтернативное создание фабрики
		//ApplicationContext beanFactory = new ClassPathXmlApplicationContext("beans.xml");
*/		
		Object object=beanFactory.getBean("user_message_singleton");
		Object object2=beanFactory.getBean("user_message_singleton");
		Object object3=beanFactory.getBean("user_message_prototype");
		Object object4=beanFactory.getBean("user_message_prototype");
		Object object5=beanFactory.getBean("user_message_values",new String[]{"this is FIRST dynamic value","this is SECOND dynamic value"});
		//Object object=beanFactory.getBean("user_message", new String[]{"this is startup message"});

		System.out.println("Object1:"+object.getClass().getName().toString()+" Object:"+object.toString());
		System.out.println("Object2:"+object2.getClass().getName().toString()+" Object:"+object2.toString());
		System.out.println("Object3:"+object2.getClass().getName().toString()+" Object:"+object3.toString());
		System.out.println("Object4:"+object2.getClass().getName().toString()+" Object:"+object4.toString());
		System.out.println("Object5:"+object2.getClass().getName().toString()+" Object:"+object5.toString());

		System.out.println("end");
	}
}
