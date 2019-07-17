package main_implementation;

import main_interface.IValues;

public class IntegerValue implements IValues<Integer>{
	private Integer value;
	@Override
	public Integer getValue() {
		return value;
	}

	@Override
	public void setValue(Integer value) {
		this.value=value;
	}

}
