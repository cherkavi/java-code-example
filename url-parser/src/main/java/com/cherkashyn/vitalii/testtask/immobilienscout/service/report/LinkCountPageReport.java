package com.cherkashyn.vitalii.testtask.immobilienscout.service.report;

import com.cherkashyn.vitalii.testtask.immobilienscout.service.PageInformationAware;

public class LinkCountPageReport extends LinkAwarePageReport{
	private final static String TITLE="link count";
	
	@Override
	protected String getReportLineName() {
		return TITLE;
	}

	@Override
	protected String getReportLineInfo(PageInformationAware pageInformation) {
		return Integer.toString( getAllLinks(pageInformation).size() );
	}

}
