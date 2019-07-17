
/** показывает что автораспаковка из "Integer" в "int" не работает(NullPointerException) в случае когда значение Integer - null */
public class AutoPackIntegerFromNull {
	public static void main(String[] args){
		int value=getValue();
		System.out.println("value:"+value);
	}
	
	private static Integer getValue(){
		return null;
	}
}
