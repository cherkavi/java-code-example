package com.cherkashyn.vitalii.testtask.meteogroup.parser.impl;

import java.util.List;

import com.cherkashyn.vitalii.testtask.meteogroup.domain.ResultElement;

class MailParser extends ParserStrategy{
	public final static String PROTOCOL="mailto";
	
	MailParser() {
		super(PROTOCOL);
	}

	@Override
	List<ResultElement<?>> parse(String url) {
		// TODO Auto-generated method stub
		return null;
	}

}
