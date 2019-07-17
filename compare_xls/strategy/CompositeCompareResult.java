package compare_xls.strategy;

import java.util.List;

public class CompositeCompareResult extends CompareResult{
	private List<CompareResult> list;
	
	public CompositeCompareResult(List<CompareResult> list){
		this.list=list;
	}

	@Override
	public String toString() {
		StringBuffer returnValue=new StringBuffer();
		returnValue.append("CompositeCompareResult:   ");
		for(int counter=0;counter<list.size();counter++){
			if(counter!=0)returnValue.append("\n   ");
			returnValue.append(this.list.get(counter).toString());
		}
		return returnValue.toString();
	}
	
	
}
