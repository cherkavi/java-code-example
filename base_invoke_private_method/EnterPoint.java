package base_invoke_private_method;

public class EnterPoint {
	public static void main(String[] args){
		System.out.println("begin");
		
		Child child=new Child();
		child.callMethod("publicMethod");
		child.callMethod("defaultMethod");
		child.callMethod("protectedMethod");
		child.callMethod("privateMethod");
		
		System.out.println("end");
	}
}
