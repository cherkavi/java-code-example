package com.ubs.omnia.tools.generator.csv.value;

public class ConstGenerator implements ValueGenerator{
	private final String value;
	
	public ConstGenerator(String constValue){
		this.value=constValue;
	}
	
	@Override
	public String getValue() {
		return value;
	}

}
