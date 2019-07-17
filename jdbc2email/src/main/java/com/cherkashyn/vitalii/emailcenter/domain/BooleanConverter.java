package com.cherkashyn.vitalii.emailcenter.domain;

import org.apache.commons.lang3.StringUtils;

class BooleanConverter {
	private final static String BOOLEAN_TRUE="Y";

	static boolean parseBooleanFromChar(String value){
		return StringUtils.equalsIgnoreCase(StringUtils.trim(value), BOOLEAN_TRUE );			
	}

}
