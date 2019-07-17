package com.cherkashyn.vitalii.testtask.immobilienscout.service.report;

import com.cherkashyn.vitalii.testtask.immobilienscout.service.PageInformationAware;

public class TitlePageReport extends SingleLinePageReport{
	private final static String TITLE="title";
	@Override
	protected String getReportLineName() {
		return TITLE;
	}

	@Override
	protected String getReportLineInfo(PageInformationAware pageInformation) {
		return pageInformation.getTitle();
	}

}
