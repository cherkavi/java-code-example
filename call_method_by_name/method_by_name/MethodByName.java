package method_by_name;
import method_by_name.MethodName;
import javax.swing.JLabel;
public class MethodByName {

	/**
	 * @param args
	 */
	public static void example_JLabel(){
		JLabel label1=new JLabel("label hello");
		MethodName method_by_name=null;
		try{
			method_by_name=new MethodName(label1,"getText",new Object[]{});
		}catch(Exception ex){
			System.out.println("не удается создать объект с данными параметрами");
		}
		if(method_by_name!=null){
			try{
				System.out.println("Method:"+method_by_name.getValue());
			}catch(Exception ex){
				System.out.println("Ошибка при попытке вызова метода:"+ex.getMessage());
			}
		}else{
			
		}
	}
	public static void example_example(){
		try{
			example temp_example=new example("temp_string1","temp_string2");
			MethodName method_by_name=new MethodName(temp_example,"getField",new Object[]{new Integer(1)});
			System.out.println("Method:"+method_by_name.getValue());
		}catch(Exception e){
			System.out.println("error method");
		}
	}
	public static void main(String[] args) {
		example_JLabel();
		example_example();
	}

}
