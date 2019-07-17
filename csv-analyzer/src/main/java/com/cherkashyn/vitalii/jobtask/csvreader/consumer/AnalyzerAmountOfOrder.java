package com.cherkashyn.vitalii.jobtask.csvreader.consumer;

import com.cherkashyn.vitalii.jobtask.csvreader.source.domain.Column;

public class AnalyzerAmountOfOrder extends StateAnalyzer<Integer>{

	public AnalyzerAmountOfOrder(Column<Integer> stateColumn) {
		super(stateColumn);
	}
	
	private int orderAmount=0;

	@Override
	public void calculatePreviousRows() {
		this.orderAmount++;
	}

	@Override
	public String getResult() {
		return "total amount of orders:"+this.orderAmount;
	}

}
