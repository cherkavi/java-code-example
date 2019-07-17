import java.util.*;

public class example {
	public static void main(String[] args){
		//check();
		//changeList();
		changeUserObject();
	}
	
	/** демонстрация изменения пользовательского объекта*/
	private static void changeUserObject(){
		ControlObject control=new ControlObject("main");
		System.out.println("Before:"+control+"    value:"+control.getString());
		changer(control);
		System.out.println("After:"+control+"value:"+control.getString());
	}
	
	
	private static void changer(ControlObject control){
		System.out.println("changer     Before:"+control+"   value:"+control.getString());
		control=new ControlObject("new value");
		System.out.println("changer     After:"+control+"   value:"+control.getString());
		//control.setString("new value");
	}
	
	
	/** проверка возможности замены для ArrayList*/
	private static void changeList(){
		ArrayList<String> list=new ArrayList<String>();
		list.add("main_1");
		System.out.println("BEFORE:");
		Changer changer=new Changer();
		System.out.println("list:"+list);
		changer.changeList(list);
		System.out.println("AFTER:");
		System.out.println("list:"+list);
	}
	
	/** проверка модификатора final для ArrayList*/
	private static void check(){
		final ArrayList<String> final_list=new ArrayList<String>();
		final_list.add("final_one");
		final_list.add("final_two");
		final_list.add("final_three");
		
		ArrayList<String> list=new 	ArrayList<String>();
		list.add("one");
		list.add("two");
		list.add("three");

		System.out.println("-----------Adding value-----------------------");
		System.out.println("Before:");
		System.out.println("final_list:"+final_list);
		System.out.println("list:"+list);
		
		addStringToList(final_list,"adding_value");
		addStringToList(list,"adding_value");
		
		System.out.println("After:");
		System.out.println("final_list:"+final_list);
		System.out.println("list:"+list);
		System.out.println("---------------------------------------------");
		System.out.println("------------Replace object into method-------");
		System.out.println("Before:");
		System.out.println("final_list:"+final_list);
		System.out.println("list:"+list);

		replaceList(final_list,"single_value");
		replaceList(list,"single_value");
	
		System.out.println("After:");
		System.out.println("final_list:"+final_list);
		System.out.println("list:"+list);
		System.out.println("--------------------------------------------");
		System.out.println("-----------Replace object local");
		System.out.println("Before:");
		System.out.println("list:"+list);

		list=new ArrayList<String>();
		list.add("one");
		list.add("two");
	
		System.out.println("After:");
		System.out.println("list:"+list);
		System.out.println("--------------------------------------------");
		
	}
	
	private static void addStringToList(ArrayList<String> list,String value){
		list.add(value);
	}
	
	private static void replaceList(ArrayList<String> list, String first_value){
		System.out.println("replaceList Before:"+list);
		list=new ArrayList<String>();
		list.add(first_value);
		System.out.println("replaceList After:"+list);
	}
}
