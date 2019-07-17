package param_in_method;

public class VariableParametersIntoMethod {
	
	/**   
	 * демонстраци€ возможных вариантов передачи в качестве параметров измен€емого числа значений
	 * 
	 * */
	public static void main(String[] args){
		System.out.println("ѕеременное кол-во параметров не указано: "+checkParameters());
		System.out.println("ѕеременное кол-во параметров равно null: "+checkParameters(null));
		System.out.println("ѕеременное кол-во параметров равно 2 элементам: "+checkParameters("1","2"));
		System.out.println("ѕеременное кол-во параметров равно массиву из трех элементов: "+checkParameters(new Object[]{"3","4","5"}));
		
	}
	
	private static String checkParameters(Object ... objects){
		if(objects==null){
			return "NULL";
		}else{
			return Integer.toString(objects.length);
		}
	}
}
