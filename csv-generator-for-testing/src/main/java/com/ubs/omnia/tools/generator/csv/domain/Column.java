package com.ubs.omnia.tools.generator.csv.domain;

import com.ubs.omnia.tools.generator.csv.value.ValueGenerator;

public class Column {
	
	private final String name;
	private final ValueGenerator generator;
	
	public Column(String name, ValueGenerator generator){
		this.name=name;
		this.generator=generator;
	}

	public String getName() {
		return name;
	}

	public ValueGenerator getGenerator() {
		return generator;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Column other = (Column) obj;
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}


	/**
	 * @param name - of column 
	 * @return 
	 * <ul>
	 * 	<li> <b>null</b> - was not found by name </li>
	 * 	<li> <b>{@link Column} - column with name </b> </li>
	 * </ul>
	 */
	public static Column getColumnByName(Column[] columns, String name){
		for(Column eachColumn: columns){
			if(eachColumn.getName().equals(name)){
				return eachColumn;
			}
		}
		return null;
	}
	
}
