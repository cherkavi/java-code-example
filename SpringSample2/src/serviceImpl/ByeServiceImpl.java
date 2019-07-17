package serviceImpl;

import domain.Name;
import service.ByeService;

public class ByeServiceImpl implements ByeService {
	public String sayBye(Name name) {
		return "Bye " + name.getFirstName() + " " + name.getLastName();
	}
}