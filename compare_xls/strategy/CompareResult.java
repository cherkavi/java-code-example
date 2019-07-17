package compare_xls.strategy;

public class CompareResult {
	private String key;
	private int originalIndex;
	private int controlIndex;
	private String valueOriginal;
	private String valueControl;
	
	public CompareResult(String key, int originalIndex, int controlIndex, String valueOriginal, String valueControl){
		this.key=key;
		this.originalIndex=originalIndex;
		this.controlIndex=controlIndex;
		this.valueOriginal=valueOriginal;
		this.valueControl=valueControl;
	}
	
	public CompareResult(){
	}

	@Override
	public String toString() {
		return "CompareResult [" 
				+ ", key=" + key
				+ ", valueOriginal=" + valueOriginal 
				+ ", valueControl=" + valueControl 
				+ ", originalIndex=" + originalIndex 
				+ ", controlIndex=" + controlIndex
				+ "]";
	}
	
	
}
