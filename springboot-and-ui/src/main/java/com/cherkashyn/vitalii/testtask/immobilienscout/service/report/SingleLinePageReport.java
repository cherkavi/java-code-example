package com.cherkashyn.vitalii.testtask.immobilienscout.service.report;

import java.util.Arrays;
import java.util.List;

import com.cherkashyn.vitalii.testtask.immobilienscout.dto.PageDescription;
import com.cherkashyn.vitalii.testtask.immobilienscout.service.PageInformationAware;

public abstract class SingleLinePageReport implements PageReport{

	@Override
	public List<PageDescription> get(PageInformationAware pageInformation) {
		return Arrays.asList(new PageDescription(getReportLineName(), getReportLineInfo(pageInformation)));
	}
	
	protected abstract String getReportLineName();
	protected abstract String getReportLineInfo(PageInformationAware pageInformation);

}
