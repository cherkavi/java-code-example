package com.cherkashyn.vitalii.testtask.immobilienscout.service.report;

import java.util.ArrayList;
import java.util.List;

import com.cherkashyn.vitalii.testtask.immobilienscout.dto.PageDescription;
import com.cherkashyn.vitalii.testtask.immobilienscout.service.PageInformationAware;

public class CompositePageReport implements PageReport{
	private final PageReport[] reports;
	
	public CompositePageReport(PageReport ... bunchOfReports) {
		this.reports=bunchOfReports.clone();
	}
	
	@Override
	public List<PageDescription> get(PageInformationAware pageInformation) {
		List<PageDescription> returnValue=new ArrayList<PageDescription>(this.reports.length);
		for(int index=0;index<this.reports.length;index++){
			returnValue.addAll(this.reports[index].get(pageInformation));
		}
		return returnValue;
	}

}
