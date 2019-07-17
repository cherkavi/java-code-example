interface my_interface{
	public String get_string(String s);
}
class my_class implements my_interface{

	public String get_string(String s) {
		return s;
	}
	
}
public class temp {
	public static void main(String args[]){
		my_class temp=new my_class();
		if(temp instanceof my_class){
			System.out.println("temp instanceof my_class");
		}
		if(temp instanceof my_interface){
			System.out.println("temp instanceof my_interface");
		}
	}
}
