package main_implementation;



import main_interface.IValues;

public class StringValue implements IValues<String>{
	private String value;
	@Override
	public String getValue() {
		return value;
	}

	@Override
	public void setValue(String value) {
		this.value=value;
	}

}
