package compare_xls.strategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import compare_xls.source.IGridSource;

public class CompareOriginalExists extends ACompareSymmetric{
	private Logger logger=Logger.getLogger(this.getClass());
	
	@Override
	public List<CompareResult> compare(IGridSource original,
									   IGridSource destination) {
		ArrayList<CompareResult> returnValue=new ArrayList<CompareResult>();
		while(original.hasNext()){
			String[] originalRecords=original.next();
			if( (originalRecords!=null) && (!isEmpty(originalRecords[original.getKeyIndex()]))){
				String[] controlRecords=destination.getRecordsByKey(originalRecords[original.getKeyIndex()]);
				if(isEmpty(controlRecords)){
					returnValue.add(new CompareResult(originalRecords[original.getKeyIndex()], 
													  original.getKeyIndex(), 
													  destination.getKeyIndex(), 
													  originalRecords[original.getKeyIndex()], 
													  null // controlRecords[destination.getKeyIndex()]
													  )
					);
				}
			}else{
				logger.debug("Record does not have a keyValue:"+Arrays.toString(originalRecords));
			}
		}
		return returnValue;
	}

	
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
}
