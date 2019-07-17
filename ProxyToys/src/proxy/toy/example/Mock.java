package proxy.toy.example;

public class Mock implements IMock{
	private String value;
	private int intValue;
	
	public Mock(String value, int intValue) {
		super();
		this.value = value;
		this.intValue = intValue;
	}
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	public int getIntValue() {
		return intValue;
	}
	public void setIntValue(int intValue) {
		this.intValue = intValue;
	}
}

