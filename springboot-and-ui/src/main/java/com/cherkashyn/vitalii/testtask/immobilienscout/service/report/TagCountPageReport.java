package com.cherkashyn.vitalii.testtask.immobilienscout.service.report;

import com.cherkashyn.vitalii.testtask.immobilienscout.service.PageInformationAware;

public class TagCountPageReport extends SingleLinePageReport{
	private final static String TITLE="count of tag %s";
	private final String tag;
	
	
	public TagCountPageReport(String tagName) {
		this.tag=tagName;
	}
	
	@Override
	protected String getReportLineName() {
		return String.format(TITLE, tag);
	}

	@Override
	protected String getReportLineInfo(PageInformationAware pageInformation) {
		return Integer.toString(pageInformation.getContentsByTag(tag).size());
	}

}
