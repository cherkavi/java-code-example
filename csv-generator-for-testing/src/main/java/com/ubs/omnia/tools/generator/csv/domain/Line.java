package com.ubs.omnia.tools.generator.csv.domain;


public class Line {
	
	private final Column[] columns;
	
	public Line(Column ... columns){
		this.columns=columns;
	}

	public Column getColumnByName(String name){
		return Column.getColumnByName(columns, name);
	}
	
	public Column[] getColumns(){
		return this.columns;
	}
}
