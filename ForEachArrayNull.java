
public class ForEachArrayNull {

	/**
	 * Вывод - для метода foreach нельзя применять значение null, иначе NullPointerException
	 * (nullable object is not allowed) 
	 * @param args
	 */
	public static void main(String[] args){
		System.out.println(">>> begin");
		String[] values=null;
		for(String value:values){
			System.out.println("currentValue:"+value);
		}
		System.out.println(">>>  end");
	}
}
