import java.util.ArrayList;


public class EnterPoint {
	public static void main(String[] args){
		System.out.println("begin");
		Object value=getList();
		// присваивание параметру значения null
		value=getNull();
		if(value instanceof String){
			System.out.println("String");
		}else if (value instanceof ArrayList){
			System.out.println("ArrayList");
		}else if (value instanceof Object){
			System.out.println("Object");
		}
		// else if (value instanceof null){}
		System.out.println("end");
	}
	
	private static ArrayList<String> getList(){
		ArrayList<String> returnValue=new ArrayList<String>();
		returnValue.add("one");
		returnValue.add("two");
		returnValue.add("three");
		return returnValue;
	}
	
	private static ArrayList<String> getNull(){
		return null;
	}
}
