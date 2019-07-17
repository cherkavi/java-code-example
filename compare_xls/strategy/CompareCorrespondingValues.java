package compare_xls.strategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import compare_xls.correctors.ICorrector;
import compare_xls.correctors.ReplaceCorrector;
import compare_xls.source.IGridSource;

public class CompareCorrespondingValues implements ICompare{
	private Logger logger=Logger.getLogger(this.getClass());
	private ReplaceCorrector[] correctors=null;
	
	public CompareCorrespondingValues(ReplaceCorrector ... correctors) {
		this.correctors=correctors;
	}


	@Override
	public List<CompareResult> compare(IGridSource original,
									   IGridSource destination) {
		ArrayList<CompareResult> returnValue=new ArrayList<CompareResult>();
		while(original.hasNext()){
			String[] originalRecords=original.next();
			// check for empty records and empty string 
			if( (originalRecords!=null) && (!isEmpty(originalRecords[original.getKeyIndex()]))){
				String[] controlRecords=destination.getRecordsByKey(originalRecords[original.getKeyIndex()]);
				if(!isEmpty(controlRecords)){
					CompareResult result=compareValues(originalRecords[original.getKeyIndex()], 
													   originalRecords, 
													   original.getCompareMap(), 
													   controlRecords);
					if(result!=null){
						returnValue.add(result);
					}
				}
			}else{
				logger.debug("Record does not have a keyValue:"+Arrays.toString(originalRecords));
			}
		}
		return returnValue;
	}

	
	private CompareResult compareValues(String key,
										String[] originalRecords,
										Map<Integer, Integer> compareMap, 
										String[] controlRecords) {
		ArrayList<CompareResult> returnValue=new ArrayList<CompareResult>();
		Iterator<Integer> keys=compareMap.keySet().iterator();
		while(keys.hasNext()){
			Integer indexOriginal=keys.next();
			Integer indexControl=compareMap.get(indexOriginal);
			CompareResult compareResult=compareValue(key, originalRecords,indexOriginal, controlRecords, indexControl);
			if(compareResult!=null){
				returnValue.add(compareResult);
			}
		}
		if(returnValue.size()>0){
			if(returnValue.size()==1){
				return returnValue.get(0);
			}else{
				return new CompositeCompareResult(returnValue);
			}
		}else{
			return null;
		}
	}

	private CompareResult compareValue(String key,
									   String[] originalRecords, int originalIndex, 
									   String[] controlRecords, int controlIndex){
		try{
			String originalValue=originalRecords[originalIndex];
			String controlValue=controlRecords[controlIndex];
			ICorrector corrector=this.getCorrectorByIndex(originalIndex);
			if(corrector!=null){
				originalValue=corrector.correctValue(originalValue);
			}
			
			if(!originalValue.trim().equals(controlValue.trim())){
				return new CompareResult(key, originalIndex, controlIndex, originalValue, controlValue);
			}else{
				return null;
			}
		}catch(Exception ex){
			logger.error("Check   OriginalIndex:"+originalIndex+"   ControlIndex:"+controlIndex);
			return new CompareResult(key, originalIndex, controlIndex, Arrays.toString(originalRecords), Arrays.toString(controlRecords));
		}
	}
	

	private ICorrector getCorrectorByIndex(int originalIndex) {
		if(this.correctors==null){
			return null;
		}
		for(int index=0;index<this.correctors.length;index++){
			if(this.correctors[index].getIndex()==originalIndex){
				return this.correctors[index];
			}
		}
		return null;
	}


	private boolean isEmpty(String[] controlRecords) {
		if(controlRecords==null){
			return true;
		}
		if(controlRecords.length==0){
			return true;
		}
		if(controlRecords.length<20){
			System.out.println("maybe");
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
