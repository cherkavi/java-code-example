package com.cherkashyn.vitalii.jobtask.csvreader.consumer;

import com.cherkashyn.vitalii.jobtask.csvreader.source.domain.Column;

public class AnalyzerSum extends FlowAnalyzer<Integer>{

	private int amount=0;
	private String preambula;

	public AnalyzerSum(String resultPreambula, Column<Integer> column) {
		super(column);
		this.preambula=resultPreambula;
	}
	
	@Override
	protected void readNotNullValue(Integer valueFromField) {
		amount+=(valueFromField==null)?0:valueFromField.intValue();
	}

	@Override
	public String getResult() {
		return this.preambula+Integer.toString(amount);
	}

}
