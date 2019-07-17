public class BooleanEquals{
	public static void main(String[] args){

		Boolean value=new Boolean(true);
		boolean simpleValue=true;

		System.out.println("equals: "+(value.equals(simpleValue)));
		System.out.println(" == :"+(value==simpleValue));
	}
}