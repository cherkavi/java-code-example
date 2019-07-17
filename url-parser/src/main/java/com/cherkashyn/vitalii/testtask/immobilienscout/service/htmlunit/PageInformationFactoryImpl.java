package com.cherkashyn.vitalii.testtask.immobilienscout.service.htmlunit;

import java.io.IOException;
import java.net.MalformedURLException;

import com.cherkashyn.vitalii.testtask.immobilienscout.exception.AnalyzingException;
import com.cherkashyn.vitalii.testtask.immobilienscout.exception.ResponseException;
import com.cherkashyn.vitalii.testtask.immobilienscout.exception.URLFormatException;
import com.cherkashyn.vitalii.testtask.immobilienscout.service.PageInformationAware;
import com.cherkashyn.vitalii.testtask.immobilienscout.service.PageInformationFactory;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class PageInformationFactoryImpl implements PageInformationFactory{

	@Override
	public PageInformationAware analyze(String url) throws AnalyzingException, IOException {
		WebClient client=null;
		try{
			client=new WebClient();
			// not a thread safe !!! 
			HtmlPage page=null;
			try {
				page=client.getPage(url);
			} catch (FailingHttpStatusCodeException e) {
				throw new ResponseException(e);
			} catch (MalformedURLException e) {
				throw new URLFormatException();
			} catch (IOException e) {
				throw e;
			}
			return new PageInformationAwareImpl(page);
		}finally{
			closeQuietly(client);
		}
	}

	private static final void closeQuietly(WebClient client) {
		if(client==null){
			return;
		}
		try{
			client.close();
		}catch(RuntimeException ex){
		}
	}

}
