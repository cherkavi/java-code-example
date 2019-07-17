package com.ubs.omnia.tools.generator.csv.value;

import java.util.Random;

public class ConstRandomGenerator implements ValueNextGenerator{

	private final String[] values;
	private int valueIndex=0;
	private final Random random;
	
	public ConstRandomGenerator(String ... values){
		if(values==null || values.length==0){
			throw new IllegalArgumentException("need to set some values");
		}
		this.values=values;
		random=new Random();
	}
	
	@Override
	public void next(){
		valueIndex=random.nextInt(values.length);
	}
	
	@Override
	public String getValue() {
		return values[valueIndex];
	}

}
