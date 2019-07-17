package functors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

public class FunctorFilter {
	public static void main(String[] args){
		System.out.println("begin");
		List<? extends Object> list=new ArrayList(Arrays.asList("one","two",new Double(3),null,new Integer(4),"FIVE"));
		org.apache.commons.collections.CollectionUtils.filter(list, new FilterPredicate());
		System.out.println(listToString(list));
		System.out.println("-end-");
	}
	
	private static String listToString(List<? extends Object> list){
		StringBuilder returnValue=new StringBuilder();
		for(Object value:list){
			returnValue.append(value);
			returnValue.append(" ");
		}
		return returnValue.toString();
	}
}

class FilterPredicate implements Predicate{

	@Override
	public boolean evaluate(Object object) {
		return(object instanceof String);
	}
	
}