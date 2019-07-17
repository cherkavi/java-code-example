package test.service.activator;

import test.service.interfaces.IService;

public class Service implements IService{

	@Override
	public String getString() {
		return "this is temp value";
	}

}
