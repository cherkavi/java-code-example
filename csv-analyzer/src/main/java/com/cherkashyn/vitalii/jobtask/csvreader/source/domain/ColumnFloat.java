package com.cherkashyn.vitalii.jobtask.csvreader.source.domain;

import com.cherkashyn.vitalii.jobtask.csvreader.exception.DataSourceException;

public class ColumnFloat extends ColumnNotNull<Float>{

	public ColumnFloat(String columnUniqueId) {
		super(columnUniqueId);
	}

	@Override
	protected Field<Float> parseNotNullData(String notNullString) throws DataSourceException {
		Float data=null;
		try{
			// FIXME: maybe need to change "." to "," - depends on target environment and CSV file producer
			data=Float.parseFloat(notNullString.trim());
		}catch(NumberFormatException ex){
			throw new DataSourceException("can't parse string as number:"+notNullString, ex);
		}
		return new Field<Float>(this, data);
	}

}
