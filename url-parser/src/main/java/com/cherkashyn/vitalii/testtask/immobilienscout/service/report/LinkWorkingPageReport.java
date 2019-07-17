package com.cherkashyn.vitalii.testtask.immobilienscout.service.report;

import java.util.Iterator;
import java.util.List;

import com.cherkashyn.vitalii.testtask.immobilienscout.service.HttpUtils;
import com.cherkashyn.vitalii.testtask.immobilienscout.service.PageInformationAware;

public class LinkWorkingPageReport extends LinkAwarePageReport{
	private final static String TITLE="accessible/not-accessible links";
	private final static String MESSAGE="%s/%s"; 

	@Override
	protected String getReportLineName() {
		return TITLE;
	}

	@Override
	protected String getReportLineInfo(PageInformationAware pageInformation) {
		List<String> links=this.getAllLinks(pageInformation);
		
		int external=0;
		int accessible=0;
		
		Iterator<String> iterator=links.iterator();
		while(iterator.hasNext()){
			String nextLine=iterator.next();
			if(!isExternal(nextLine)){
				continue;
			}
			external++;
			if(HttpUtils.checkUrl(nextLine)){
				accessible++;
			}
		}
		return String.format(MESSAGE, Integer.toString(accessible), Integer.toString(external));
	}

}
