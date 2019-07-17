
public class TestAndOperator {
	public static void main(String[] args){
		System.out.println("begin");
		boolean value=true;
		value=(value&&returnTrue());
		value&=returnTrue();
		System.out.println("value:"+value);
		System.out.println("end");
	}
	
	private static boolean returnFalse(){
		return false;
	}
	private static boolean returnTrue(){
		return true;
	}
}
