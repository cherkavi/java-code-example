package com.cherkashyn.vitalii.jobtask.csvreader.source.domain;

/**
 * represent one field from row 
 *
 * @author technik
 *
 */
public class Field<T> {
	private Column<T> metaData;
	private T value;
	
	public Field(Column<T> metaData, T value) {
		super();
		this.metaData = metaData;
		this.value = value;
	}

	public Column<T> getColumn() {
		return metaData;
	}

	public T getValue() {
		return value;
	}

	@Override
	public String toString() {
		return "value=" + value;
	}
		
}
