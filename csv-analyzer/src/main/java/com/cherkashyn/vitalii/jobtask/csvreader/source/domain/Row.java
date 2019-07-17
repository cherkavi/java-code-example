package com.cherkashyn.vitalii.jobtask.csvreader.source.domain;

import java.util.Arrays;

/**
 * represent one row from CSV file
 * @author technik
 */
public class Row {
	private Column<?>[] columns;
	private Field<?>[] data;
	
	public Row(Column<?>[] columns, Field<?>[] fields) {
		this.columns=columns;
		this.data=fields;
	}

	public Field<?>[] getData() {
		return data.clone();
	}

	public Column<?>[] getColumns(){
		return columns.clone();
	}

	@Override
	public String toString() {
		return "Row data=" + Arrays.toString(data);
	}
	
}
