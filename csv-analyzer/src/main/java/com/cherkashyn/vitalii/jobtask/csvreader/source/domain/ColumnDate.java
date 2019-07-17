package com.cherkashyn.vitalii.jobtask.csvreader.source.domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.cherkashyn.vitalii.jobtask.csvreader.exception.DataSourceException;

/**
 * NOT THREAD safe, replace sdf of type {@link SimpleDateFormat} to {@link DateTimeFormat}
 * @author technik
 *
 */
public class ColumnDate extends ColumnNotNull<Date>{
	private final SimpleDateFormat sdf;
	
	public ColumnDate(String columnId, String pattern){
		super(columnId);
		// maybe need to catch RuntimeException and translate it to "standard" exception
		this.sdf=new SimpleDateFormat(pattern);
	}
	
	@Override
	protected Field<Date> parseNotNullData(String notNullString) throws DataSourceException {
		try {
			return new Field<Date>(this, this.sdf.parse(notNullString.trim()));
		} catch (ParseException e) {
			throw new DataSourceException("can't parse date:"+notNullString);
		}
	}

}
