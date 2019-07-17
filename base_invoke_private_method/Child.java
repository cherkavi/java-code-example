package base_invoke_private_method;


@SuppressWarnings("unused")
public class Child extends Parent{
	
	public void publicMethod(){
		System.out.println("Child#publicMethod");
	}
	
	void defaultMethod(){
		System.out.println("Child#defaultMethod");
	}
	
	protected void protectedMethod(){
		System.out.println("Child#protectedMethod");
	}
	
	private void privateMethod(){
		System.out.println("Child#privateMethod");
	}
}
