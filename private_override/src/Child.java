/**
 * this class constist method first_method which overrided parent method first_method
 */
public class Child extends Parent{
	static{
		System.out.println("Child static noname block");
	}
	
	{
		System.out.println("Child noname block");
	}
	public Child(){
		System.out.println("Child constructor");
	}
	public void first_child(){
		super.get_value();
		System.out.println("Child.first_method");
	}
	
}
