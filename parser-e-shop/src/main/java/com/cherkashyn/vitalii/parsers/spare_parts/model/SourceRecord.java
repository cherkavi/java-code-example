package com.cherkashyn.vitalii.parsers.spare_parts.model;

import org.apache.commons.lang3.StringUtils;

public class SourceRecord {
	private String number;
	private String resultFirstElement;
	
	
	public SourceRecord(String number, String resultFirstElement) {
		super();
		this.number = StringUtils.trimToNull(number);
		this.resultFirstElement = StringUtils.trimToNull(resultFirstElement);
	}
	
	public String getNumber() {
		return number;
	}
	
	public String getResultFirstElement() {
		return resultFirstElement;
	}
	
	
}
