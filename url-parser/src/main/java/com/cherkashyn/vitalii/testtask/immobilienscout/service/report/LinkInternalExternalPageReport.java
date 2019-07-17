package com.cherkashyn.vitalii.testtask.immobilienscout.service.report;

import java.util.Iterator;
import java.util.List;

import com.cherkashyn.vitalii.testtask.immobilienscout.service.PageInformationAware;

public class LinkInternalExternalPageReport extends LinkAwarePageReport{
	private final static String TITLE="links Internal/External";
	private final static String MESSAGE="%s/%s"; 

	@Override
	protected String getReportLineName() {
		return TITLE;
	}

	@Override
	protected String getReportLineInfo(PageInformationAware pageInformation) {
		List<String> links=this.getAllLinks(pageInformation);
		int internal=0;
		int external=0;
		Iterator<String> iterator=links.iterator();
		while(iterator.hasNext()){
			String nextLine=iterator.next();
			if(isExternal(nextLine)){
				external++;
			}else{
				internal++;
			}
		}
		return String.format(MESSAGE, Integer.toString(internal), Integer.toString(external));
	}
	
	

}
