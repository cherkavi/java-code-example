package com.cherkashyn.vitalii.jobtask.csvreader.source.domain;

import com.cherkashyn.vitalii.jobtask.csvreader.exception.DataSourceException;

/**
 * split up data: null & not-null
 * @author technik
 *
 */
abstract class ColumnNotNull<T> extends Column<T>{

	public ColumnNotNull(String columnUniqueId) {
		super(columnUniqueId);
	}

	public Field<T> parseData(String rawData) throws DataSourceException{
		if(rawData==null){
			return new Field<T>(this, (T)null);
		}
		return parseNotNullData(rawData);
	}
	
	/**
	 * parse not null data
	 * @return
	 * @throws DataSourceException 
	 */
	protected abstract Field<T> parseNotNullData(String notNullString) throws DataSourceException;

}