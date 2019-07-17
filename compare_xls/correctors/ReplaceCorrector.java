package compare_xls.correctors;

public class ReplaceCorrector implements ICorrector{
	private int index;
	private String findValue;
	private String replaceValue;
	
	public ReplaceCorrector(int index, String findValue, String replaceValue){
		this.index=index;
		this.findValue=findValue;
		this.replaceValue=replaceValue;
	}

	@Override
	public String correctValue(String value) {
		if(findValue.equals(value)){
			return replaceValue;
		}
		return value;
	}

	@Override
	public int getIndex() {
		return this.index;
	}
	
	
}
