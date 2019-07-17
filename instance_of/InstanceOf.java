package instance_of;

public class InstanceOf {
	public static void main(String[] args){
		System.out.println("begin");
		String value=null;
		
		if(value instanceof String){
			System.out.println("Value is string: "+value);
		}else{
			System.out.println("Value is not String "); // <--------
		}
		System.out.println("end");
	}
}
