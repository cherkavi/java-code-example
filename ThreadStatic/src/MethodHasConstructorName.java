
public class MethodHasConstructorName {
	
	public static void main(String[] args ){
		System.out.println("begin");
		MethodHasConstructorName object=new MethodHasConstructorName();
		System.out.println(" >>> "+object.MethodHasConstructorName());
		System.out.println("end");
	}
	
	
	public Object MethodHasConstructorName(){
		return "has same name like constructor";
	}
}
