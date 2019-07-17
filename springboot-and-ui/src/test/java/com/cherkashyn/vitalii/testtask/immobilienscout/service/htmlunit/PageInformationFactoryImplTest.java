package com.cherkashyn.vitalii.testtask.immobilienscout.service.htmlunit;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.Test;

import com.cherkashyn.vitalii.testtask.immobilienscout.exception.AnalyzingException;
import com.cherkashyn.vitalii.testtask.immobilienscout.service.PageInformationAware;

import junit.framework.Assert;

public class PageInformationFactoryImplTest {
	private final static String FILE_TEST_PATH="test2.html";
	private final static String FILE_TEST2_PATH="test3.html";
	private final static String FILE_TEST3_PATH="test4.html";
		
	@Test
	public void checkReadData() throws AnalyzingException, IOException{
		// given
		String url=new File(Thread.currentThread().getContextClassLoader().getResource(FILE_TEST_PATH).getFile()).toURI().toURL().toString();
		String[] urls=new String[]{"http://robokassa.ru","http://xxxYYYzzz.www", "http://www.w3.org", "default-page.asp"};

		// when
		PageInformationAware pageInfo=new PageInformationFactoryImpl().analyze(url);
		
		Assert.assertEquals("Title of the test file", pageInfo.getTitle());
		Assert.assertEquals(1, pageInfo.getContentsByTag("h1").size());
		Assert.assertEquals("head #1", pageInfo.getContentsByTag("h1").get(0));
		Assert.assertEquals(3, pageInfo.getContentsByTag("h2").size());
		List<String> hrefs=pageInfo.getTagsAttribute("a", "href");
		for(int index=0;index<urls.length;index++){
			Assert.assertTrue(hrefs.contains(urls[index]));
		}
	}
	
	
	@Test
	public void readNonExistsVersion() throws AnalyzingException, IOException{
		// given
		String url=new File(Thread.currentThread().getContextClassLoader().getResource(FILE_TEST_PATH).getFile()).toURI().toURL().toString();
		
		// when
		PageInformationAware pageInfo=new PageInformationFactoryImpl().analyze(url);
		
		// then 
		Assert.assertNull(pageInfo.getHtmlDocumentVersion());
	}
	
	@Test
	public void readExistingVersion() throws AnalyzingException, IOException{
		// given
		String url=new File(Thread.currentThread().getContextClassLoader().getResource(FILE_TEST2_PATH).getFile()).toURI().toURL().toString();
		
		// when
		PageInformationAware pageInfo=new PageInformationFactoryImpl().analyze(url);
		
		// then 
		Assert.assertNotNull(pageInfo.getHtmlDocumentVersion());
		System.out.println(pageInfo.getHtmlDocumentVersion());
	}
	
	@Test
	public void readBrokeExistingVersion() throws AnalyzingException, IOException{
		// given
		String url=new File(Thread.currentThread().getContextClassLoader().getResource(FILE_TEST3_PATH).getFile()).toURI().toURL().toString();
		
		// when
		PageInformationAware pageInfo=new PageInformationFactoryImpl().analyze(url);
		
		// then 
		Assert.assertNull(pageInfo.getHtmlDocumentVersion());
	}
}
