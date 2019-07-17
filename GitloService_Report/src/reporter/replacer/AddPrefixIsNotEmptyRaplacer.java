package reporter.replacer;

public class AddPrefixIsNotEmptyRaplacer extends ReplaceValue{
	private String value;
	private String prefix;
	
	public AddPrefixIsNotEmptyRaplacer(String prefix, String value){
		this.prefix=prefix;
		this.value=value;
	}
	
	@Override
	public String getReplaceValue() {
		if((value!=null)&&(!value.equals(""))){
			return prefix+value;
		}else{
			return "";
		}
	}

}
