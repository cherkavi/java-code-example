package compare_xls.strategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.bag.HashBag;

import compare_xls.source.IGridSource;

public class CompareFindDoubles extends ACompareSymmetric{
	
	private boolean isEmpty(String[] controlRecords) {
		if(controlRecords==null){
			return true;
		}
		if(controlRecords.length==0){
			return true;
		}
		if(controlRecords.length<20){
			// System.out.println("maybe");
		}
		boolean returnValue=true;
		for(int counter=0;counter<controlRecords.length;counter++){
			if((controlRecords[counter]!=null)&&(controlRecords[counter].trim().length()!=0)){
				returnValue=false;
				break;
			}
		}
		return returnValue;
	}

	private boolean isEmpty(String value){
		if(value==null)return true;
		if(value.trim().length()==0)return true;
		return false;
	}
	
	@Override
	public List<CompareResult> compare(IGridSource original,
									   IGridSource destination) {
		ArrayList<CompareResult> returnValue=new ArrayList<CompareResult>();
		HashBag bag=new HashBag();
		
		while(original.hasNext()){
			String[] originalRecords=original.next();
			if( (originalRecords!=null) && (!isEmpty(originalRecords[original.getKeyIndex()]))){
				bag.add(originalRecords[original.getKeyIndex()]);
			}
		}
		HashSet<String> values=new HashSet<String>();
		for(Object nextValue:bag){
			int count=bag.getCount(nextValue);
			if(count>1){
				values.add(nextValue.toString());
			}
		}
		for(String value:values){
			returnValue.add(new CompareResult(value, 0, 0, null, null));
		}
		return returnValue;
	}

}
