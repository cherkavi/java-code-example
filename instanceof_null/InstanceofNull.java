package instanceof_null;


/** проверка работы оператора instanceof на определение null */
public class InstanceofNull {
	public static void main(String[] args){
		System.out.println("begin");
		
		Integer value=5;
		if(value instanceof Number){
			System.out.println("value is Number");
		}else{
			System.out.println("value is not Number");
		}
		
		String temp=null;
		if(temp instanceof String){
			System.out.println("temp is String");
		}else{
			System.out.println("temp is not String ");
		}
		
		System.out.println("-end-");
	}
}
