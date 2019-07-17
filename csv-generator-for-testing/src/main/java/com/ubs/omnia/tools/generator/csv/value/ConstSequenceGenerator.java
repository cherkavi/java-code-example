package com.ubs.omnia.tools.generator.csv.value;

public class ConstSequenceGenerator implements ValueNextGenerator{

	private final String[] values;
	private int valueIndex=0;
	
	public ConstSequenceGenerator(String ... values){
		if(values==null || values.length==0){
			throw new IllegalArgumentException("need to set some values");
		}
		this.values=values;
	}
	
	@Override
	public void next(){
		valueIndex++;
		if(valueIndex==values.length){
			valueIndex=0;
		}
	}
	
	@Override
	public String getValue() {
		return values[valueIndex];
	}

}
