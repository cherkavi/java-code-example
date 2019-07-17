package constructor_call_child_method;

class Child extends Parent{
	
	{
		System.out.println("Child#anonym block");
	}
	
	public Child(){
		System.out.println("Child#constructor");
	}
	
	@Override
	public void methodMain() {
		System.out.println("Child#methodMain: ");
	};
}
