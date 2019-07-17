package com.cherkashyn.vitalii.testtask.immobilienscout.service.report;

import java.util.List;

import com.cherkashyn.vitalii.testtask.immobilienscout.dto.PageDescription;
import com.cherkashyn.vitalii.testtask.immobilienscout.service.PageInformationAware;

public interface PageReport {
	public List<PageDescription> get(PageInformationAware pageInformation);
}
