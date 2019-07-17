package spring_another_property;

public class Bean {
	private String stringValue;
	private int intValue;
	public String getStringValue() {
		return stringValue;
	}
	public void setStringValue(String stringValue) {
		this.stringValue = stringValue;
	}
	public int getIntValue() {
		return intValue;
	}
	public void setIntValue(int intValue) {
		this.intValue = intValue;
	}
	
	@Override
	public String toString() {
		return "Bean [intValue=" + intValue + ", stringValue=" + stringValue
				+ "]";
	}
	
	
}
