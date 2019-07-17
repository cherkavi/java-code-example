package com.cherkashyn.vitalii.parsers.spare_parts;

import junit.framework.Assert;

import org.junit.Test;

import com.cherkashyn.vitalii.parsers.spare_parts.exception.ParseException;
import com.cherkashyn.vitalii.parsers.spare_parts.model.PositionModel;
import com.gargoylesoftware.htmlunit.WebClient;

/**
 * Unit test for simple App.
 */
public class UrlParserTest {

	@SuppressWarnings("deprecation")
	private WebClient getWebClient(){
		WebClient client=new WebClient();
		client.setJavaScriptEnabled(false);
		client.setCssEnabled(false);
		return client;
	}
	
	@Test
	public void testRequest() throws ParseException{
		UrlParser parser=new UrlParser(getWebClient());
		PositionModel model=parser.findNumber("443621701000");
		Assert.assertEquals("http://www.skarab.cz/cs/zbozi/1328/",model.getDescribePageUrl());
		Assert.assertEquals("http://www.skarab.cz/fotky/tlumic-pt-70170-124202163-72-predni-1328.jpg",model.getImageBigUrl());
		Assert.assertEquals("http://www.skarab.cz/fotky/th/tlumic-pt-70170-124202163-72-predni-1328-2.jpg",model.getImageUrl());
		Assert.assertEquals("443621701000",model.getKodCatalog());
		Assert.assertEquals("1328",model.getKodScarab());
		Assert.assertEquals("4436210050",model.getKodSkp());
		Assert.assertEquals("1242020630 WABCO",model.getKodVirobniy());
		System.out.println(model.toString());
	}

}
