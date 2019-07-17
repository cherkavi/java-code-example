package enum_switch;

public class EnumSwitch {
	public static void main(String[] args){
		System.out.println("begin");
		Values val=Values.one;
		switch(val){
			case one:System.out.println("this is one");break;
			case two:System.out.println("this is two");break;
			case three:System.out.println("this is three");break;
			default:System.out.println("Default: ");break;
		}
		System.out.println("end");
	}
}


enum Values{
	one, 
	two, 
	three;
	
}