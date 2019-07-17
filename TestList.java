import java.util.ArrayList;
import java.util.List;


/** тестирование изменения List в случае передачи этого List в метод */
public class TestList {
	public static void main(String[] args){
		ArrayList<String> list=new ArrayList<String>();
		list.add("one");
		list.add("two");
		list.add("three");
		list.add("four");
		list.add("one");
		list.add("two");
		list.add("three");
		list.add("one");
		list.add("two");
		list.add("three");
		System.out.println("List before:"+list);
		editList(list);
		System.out.println("List after:"+list);
		
		// show method "indexOf" and "lastIndexOf"
		// has not allowed: 
		/*
		for(String value:list){
			if(list.indexOf(value)!=list.lastIndexOf(value)){
				int firstIndex=list.indexOf(value);
				int lastIndex=list.lastIndexOf(value);
				System.out.println("REMOVE: "+value+":     indexOf:"+firstIndex+"     lastIndexOf:"+lastIndex);
				list.remove(lastIndex);
			}else{
				System.out.println(value+":     indexOf:"+list.indexOf(value)+"     lastIndexOf:"+list.lastIndexOf(value));
			}
		}*/
		ArrayList<String> listWithNotRepeated=new ArrayList<String>();
		for(int counter=0;counter<list.size();counter++){
			String currentValue=list.get(counter);
			int firstIndex=list.indexOf(currentValue);
			int lastIndex=list.lastIndexOf(currentValue);
			if(firstIndex!=lastIndex){
				if(counter==firstIndex){
					listWithNotRepeated.add(currentValue);
				}
			}else{
				// only one value
				listWithNotRepeated.add(currentValue);
			}
		}
		System.out.println("After process:"+listWithNotRepeated);
	}
	
	private static void editList(List<String> list){
		if(list!=null){
			list.add("new value");
			list.remove(0);
		}
	}
}
