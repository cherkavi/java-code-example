package serviceImpl;

import domain.Name;
import service.HelloService;

public class HelloServiceImpl implements HelloService {
	public String sayHello(Name name) {
		return "Hello  " + name.getFirstName() + " " + name.getLastName();
	}
}