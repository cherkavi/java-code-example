package constructor_call_child_method;

class Parent {
	
	{
		System.out.println("Parent#anonym block");
	}
	
	public Parent(){
		System.out.println("Parent#constructor");
		System.out.println("call method main"); 
		this.methodMain();
	}
	
	public void methodMain(){
		System.out.println("Parent#methodMain: ");
	}
}

