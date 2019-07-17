package jxpath_example;

public class SubObject {
	private float floatValue;
	private String tempString;
	
	public SubObject(float floatValue, String tempString) {
		super();
		this.floatValue = floatValue;
		this.tempString = tempString;
	}

	public float getFloatValue() {
		return floatValue;
	}
	
	public void setFloatValue(float floatValue) {
		this.floatValue = floatValue;
	}
	public String getTempString() {
		return tempString;
	}
	public void setTempString(String tempString) {
		this.tempString = tempString;
	}

	@Override
	public String toString() {
		return "SubObject [floatValue=" + floatValue + ", tempString="
				+ tempString + "]";
	}

	
}
