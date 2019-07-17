package com.ubs.omnia.tools.generator.csv.value;

public class AppendGenerator implements ValueGenerator{
	
	private ValueGenerator[] generators;
	
	public AppendGenerator(ValueGenerator ... generators){
		if(generators==null || generators.length==0){
			throw new IllegalArgumentException("generators can't be null/empty ");
		}
		this.generators=generators;
	}
	
	@Override
	public String getValue() {
		StringBuilder returnValue=new StringBuilder();
		for(ValueGenerator eachGenerator:generators){
			returnValue.append(eachGenerator.getValue());
		}
		return returnValue.toString();
	}

}
