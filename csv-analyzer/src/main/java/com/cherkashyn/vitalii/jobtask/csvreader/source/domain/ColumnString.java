package com.cherkashyn.vitalii.jobtask.csvreader.source.domain;

public class ColumnString extends Column<String>{

	public ColumnString(String columnUniqueId) {
		super(columnUniqueId);
	}

	@Override
	public Field<String> parseData(String rawData) {
		return new Field<String>(this, rawData);
	}

}
