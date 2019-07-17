package com.test.xml_generator.values_generator.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateRandomGenerator extends RandomLongSequenceGenerator {

	public DateRandomGenerator(Date dateRangeBegin, Date dateRangeEnd) {
		super(dateRangeBegin.getTime(), dateRangeEnd.getTime());
	}
	
	private SimpleDateFormat sdf=new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
	
	@Override
	public String next() {
		return sdf.format(new Date(Long.parseLong(super.next())));
	}

}
