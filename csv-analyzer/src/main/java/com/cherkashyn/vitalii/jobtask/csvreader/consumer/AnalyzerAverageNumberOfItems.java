package com.cherkashyn.vitalii.jobtask.csvreader.consumer;

import com.cherkashyn.vitalii.jobtask.csvreader.source.domain.Column;
import com.cherkashyn.vitalii.jobtask.csvreader.source.domain.Row;

public class AnalyzerAverageNumberOfItems extends StateAnalyzer<Integer>{
	private Column<Integer> averageColumn;
	
	public AnalyzerAverageNumberOfItems(Column<Integer> stateColumn, Column<Integer> averageColumn) {
		super(stateColumn);
		this.averageColumn=averageColumn;
	}
	
	private float totalAverage=0;
	private int counter;
	
	@Override
	public void calculatePreviousRows() {
		if(this.dataForAnalyze!=null && this.dataForAnalyze.size()>0){
			for(Row eachRow:this.dataForAnalyze){
				Integer itemsCount=Column.findByColumn(this.averageColumn, eachRow).getValue();
				totalAverage+=(itemsCount==null?0:itemsCount.intValue());
			}
			counter++;
		}
	}

	@Override
	public String getResult() {
		this.calculatePreviousRows();
		return "average number of items per order:"+this.totalAverage/(float)counter;
	}

}
