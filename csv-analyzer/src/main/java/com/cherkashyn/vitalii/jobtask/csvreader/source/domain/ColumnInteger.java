package com.cherkashyn.vitalii.jobtask.csvreader.source.domain;

import com.cherkashyn.vitalii.jobtask.csvreader.exception.DataSourceException;

public class ColumnInteger extends ColumnNotNull<Integer>{

	public ColumnInteger(String columnUniqueId) {
		super(columnUniqueId);
	}

	@Override
	protected Field<Integer> parseNotNullData(String notNullString) throws DataSourceException {
		Integer data=null;
		try{
			data=Integer.parseInt(notNullString.trim());
		}catch(NumberFormatException ex){
			throw new DataSourceException("can't parse string as number:"+notNullString, ex);
		}
		return new Field<Integer>(this, data);
	}

}
