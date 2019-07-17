package functors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.NotNullPredicate;
import org.apache.commons.lang.StringUtils;

public class FunctorTransformer {
	public static void main(String[] args){
		System.out.println("begin");
		List<? extends Object> list=new ArrayList(Arrays.asList("one","two",new Double(3),null,new Integer(4),"FIVE"));
		Collection collection=org.apache.commons.collections.CollectionUtils.collect(list, new TempTransformer());
		CollectionUtils.filter(collection, NotNullPredicate.INSTANCE);
		System.out.println(listToString(collection));
		System.out.println("-end-");
	}
	
	private static String listToString(Collection<? extends Object> list){
		StringBuilder returnValue=new StringBuilder();
		for(Object value:list){
			returnValue.append(value);
			returnValue.append(" ");
		}
		return returnValue.toString();
	}

}

class TempTransformer implements Transformer{

	@Override
	public Object transform(Object input) {
		if(input instanceof String){
			return StringUtils.reverse((String)input);
		}
		return null;
	}
	
}
