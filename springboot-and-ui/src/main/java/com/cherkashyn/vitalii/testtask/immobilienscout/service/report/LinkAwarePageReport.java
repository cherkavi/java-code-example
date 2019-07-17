package com.cherkashyn.vitalii.testtask.immobilienscout.service.report;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.cherkashyn.vitalii.testtask.immobilienscout.service.PageInformationAware;

public abstract class LinkAwarePageReport extends SingleLinePageReport{

	protected List<String> getAllLinks(PageInformationAware pageInformation){
		return filterNotNull(pageInformation.getTagsAttribute("a", "href"));
	}

	private List<String> filterNotNull(List<String> links) {
		List<String> returnValue=new ArrayList<String>(links.size());
		java.util.Iterator<String> iterator=links.iterator();
		while(iterator.hasNext()){
			String candidate=iterator.next();
			if(!StringUtils.isEmpty(candidate)){
				returnValue.add(candidate);
			}
		}
		return returnValue;
	}
	
	private final static String EXTERNAL_PREAMBULA="http://";
	private final static String EXTERNAL_PREAMBULA2="https://";
	
	protected boolean isExternal(String url){
		String preparedUrl=StringUtils.trimToNull(url);
		return StringUtils.startsWithIgnoreCase(preparedUrl, EXTERNAL_PREAMBULA) || StringUtils.startsWithIgnoreCase(preparedUrl, EXTERNAL_PREAMBULA2);  
		
		 
	}

}
