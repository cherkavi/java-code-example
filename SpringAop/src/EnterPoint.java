import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.test.GreetingService;


public class EnterPoint {
	public static void main(String[] args){
		System.out.println("begin");
		ApplicationContext appContext = new ClassPathXmlApplicationContext("classpath:configuration.xml");
		GreetingService service=(GreetingService)appContext.getBean("greetingService");
		service.sayGreeting();
		service.sayGoodbye();
		System.out.println("-end-");
	}
}
