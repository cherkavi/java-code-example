package com.cherkashyn.vitalii.testtask.immobilienscout.service.report;

import com.cherkashyn.vitalii.testtask.immobilienscout.service.PageInformationAware;

public class LoginFormPageReport extends SingleLinePageReport{
	private final static String PAGE_CONTAINS_LOGIN_FORM="page contains login form:";
	private final static String ANSWER_YES="YES";
	private final static String ANSWER_NO="no";
	
	@Override
	protected String getReportLineName() {
		return PAGE_CONTAINS_LOGIN_FORM;
	}

	@Override
	protected String getReportLineInfo(PageInformationAware pageInformation) {
		if(pageInformation.hasLoginForm()){
			return ANSWER_YES;
		}else{
			return ANSWER_NO;
		}
	}

}
