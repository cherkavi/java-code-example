import java.util.ArrayList;

public class ArrayList_Insert {
	public static void main(String[] args){
		ArrayList<String> list=new ArrayList<String>();
		for(int counter=0;counter<10;counter++){
			list.add("");
		}
		list.add("one");
		printArrayList(list);
		list.set(2, "insert_element");
		printArrayList(list);
		list.set(2, "new insert element - replacer");
		Float value=1.7f;
		System.out.println("value:"+value.intValue());
	}
	
	private static void printArrayList(ArrayList<String> list){
		for(int counter=0;counter<list.size();counter++){
			System.out.println(counter+" : "+list.get(counter));
		}
	}
}
