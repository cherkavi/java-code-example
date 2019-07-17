/**
 * this class consist private method first_method 
 */
public class Parent {
	static{
		System.out.println("Parent static noname block");
	}
	{
		System.out.println("Parent noname block");
	}
	public Parent(){
		System.out.println("Parent constructor");
	}
	private final void first_method(){
		System.out.println("Parent.first_method call");
	}
	protected String get_value(){
		return "hello from parent";
	}
}
