package com.cherkashyn.vitalii.testtask.immobilienscout.service;

import java.io.IOException;

import com.cherkashyn.vitalii.testtask.immobilienscout.exception.AnalyzingException;

public interface PageInformationFactory {
	public PageInformationAware analyze(String url) throws AnalyzingException, IOException;
}
