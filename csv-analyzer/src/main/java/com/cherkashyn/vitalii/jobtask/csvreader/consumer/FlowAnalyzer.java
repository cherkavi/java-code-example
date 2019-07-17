package com.cherkashyn.vitalii.jobtask.csvreader.consumer;

import com.cherkashyn.vitalii.jobtask.csvreader.source.domain.Column;
import com.cherkashyn.vitalii.jobtask.csvreader.source.domain.Field;
import com.cherkashyn.vitalii.jobtask.csvreader.source.domain.Row;

public abstract class FlowAnalyzer<T> extends Analyzer{
	protected Column<T> targetColumn;
	
	public FlowAnalyzer(Column<T> column){
		this.targetColumn=column;
	}

	@Override
	public void read(Row row) {
		Field<T> targetField=Column.findByColumn(this.targetColumn, row);
		if(targetField==null){
			throw new IllegalArgumentException("row doesn't have a column of type:"+this.targetColumn);
		}
		T valueFromField=targetField.getValue();
		if(valueFromField==null){
			return;
		}
		readNotNullValue(valueFromField);
	}

	protected abstract void readNotNullValue(T valueFromField);

}
