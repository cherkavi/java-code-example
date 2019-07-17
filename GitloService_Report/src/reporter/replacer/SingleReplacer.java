package reporter.replacer;

/** выдать в качестве ответа строку с текстом */
public class SingleReplacer extends ReplaceValue{
	private String value;
	/** объект, содержащий одну строку с текстом */
	public SingleReplacer(String value){
		this.value=value;
	}

	/** объект, содержащий одну строку с текстом */
	public SingleReplacer(int value){
		this.value=Integer.valueOf(value).toString();
	}
	
	@Override
	public String getReplaceValue() {
		return value;
	}

}
