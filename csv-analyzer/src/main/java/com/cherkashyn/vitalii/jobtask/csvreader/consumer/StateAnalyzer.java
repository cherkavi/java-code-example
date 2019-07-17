package com.cherkashyn.vitalii.jobtask.csvreader.consumer;

import java.util.ArrayList;
import java.util.List;

import org.omg.CORBA.portable.ValueFactory;

import com.cherkashyn.vitalii.jobtask.csvreader.source.domain.Column;
import com.cherkashyn.vitalii.jobtask.csvreader.source.domain.Field;
import com.cherkashyn.vitalii.jobtask.csvreader.source.domain.Row;

public abstract class StateAnalyzer<T> extends Analyzer{

	private Column<T> targetColumn;
	private T previousState=null;
	protected List<Row> dataForAnalyze;
	{
		clearState();
	}

	public StateAnalyzer(Column<T> stateColumn) {
		this.targetColumn=stateColumn;
	}
	
	@Override
	public void read(Row row) {
		Field<T> targetField=Column.findByColumn(this.targetColumn, row);
		if(targetField==null){
			throw new IllegalArgumentException("row doesn't have a column of type:"+this.targetColumn);
		}
		T valueFromField=targetField.getValue();
		
		if(isNewState(valueFromField)){
			this.previousState=valueFromField;
			calculatePreviousRows();
			clearState();
		}
		storageRow(row);
	}

	public abstract void calculatePreviousRows();

	private void clearState() {
		this.dataForAnalyze=new ArrayList<Row>();
	}

	private void storageRow(Row row) {
		this.dataForAnalyze.add(row);
	}

	protected boolean isNewState(T valueFromField) {
		if(this.previousState==null && valueFromField==null){
			return false;
		}
		if(this.previousState==null && valueFromField!=null){
			return true;
		}
		if(this.previousState!=null && valueFromField==null){
			return true;
		}
		
		return !this.previousState.equals(valueFromField);
	}

}
