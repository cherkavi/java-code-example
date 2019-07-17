package com.cherkashyn.vitalii.testtask.immobilienscout.service.report;

import com.cherkashyn.vitalii.testtask.immobilienscout.service.PageInformationAware;

public class DocumentVersionPageReport extends SingleLinePageReport{
	
	private final static String TITLE="HTML version";

	@Override
	protected String getReportLineName() {
		return TITLE;
	}

	@Override
	protected String getReportLineInfo(PageInformationAware pageInformation) {
		return pageInformation.getHtmlDocumentVersion();
	}

}
