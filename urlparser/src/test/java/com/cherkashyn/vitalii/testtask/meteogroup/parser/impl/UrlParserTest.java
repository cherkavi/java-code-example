package com.cherkashyn.vitalii.testtask.meteogroup.parser.impl;

import java.util.List;

import org.junit.Test;

import com.cherkashyn.vitalii.testtask.meteogroup.domain.ResultElement;
import com.cherkashyn.vitalii.testtask.meteogroup.parser.Element;

import junit.framework.Assert;

public class UrlParserTest {

	@Test
	public void checkHttpUrl(){
		// given
		String url="https://en.wikipedia.org/wiki/Uniform_Resource_Locator";
		
		// when
		List<ResultElement<?>> elements=UrlParser.parse(url);
		
		
		// then
		Assert.assertNotNull(ResultElement.getByElement(elements, Element.Protocol));
		Assert.assertEquals("https", ResultElement.getByElement(elements, Element.Protocol).getValue());

		
		Assert.assertNull(ResultElement.getByElement(elements, Element.User));
		Assert.assertNull(ResultElement.getByElement(elements, Element.Password));
		
		Assert.assertNotNull(ResultElement.getByElement(elements, Element.Host));
		Assert.assertEquals("en.wikipedia.org", ResultElement.getByElement(elements, Element.Host).getValue());
		
		Assert.assertNull(ResultElement.getByElement(elements, Element.Port));

		Assert.assertNotNull(ResultElement.getByElement(elements, Element.Path));
		Assert.assertEquals("wiki/Uniform_Resource_Locator", ResultElement.getByElement(elements, Element.Path).getValue());

		Assert.assertNull(ResultElement.getByElement(elements, Element.Query));

		Assert.assertNull(ResultElement.getByElement(elements, Element.Fragment));
	}
	
	@Test
	public void checkFileUrl(){
		// given
		String url="file:/data/letters/to_mom.txt";
		
		// when
		List<ResultElement<?>> elements=UrlParser.parse(url);
		
		
		// then
		Assert.assertNotNull(ResultElement.getByElement(elements, Element.Protocol));
		Assert.assertEquals("file", ResultElement.getByElement(elements, Element.Protocol).getValue());

		
		Assert.assertNull(ResultElement.getByElement(elements, Element.User));
		Assert.assertNull(ResultElement.getByElement(elements, Element.Password));
		
		Assert.assertNotNull(ResultElement.getByElement(elements, Element.Host));
		Assert.assertEquals("", ResultElement.getByElement(elements, Element.Host).getValue());
		
		Assert.assertNull(ResultElement.getByElement(elements, Element.Port));

		Assert.assertNotNull(ResultElement.getByElement(elements, Element.Path));
		Assert.assertEquals("data/letters/to_mom.txt", ResultElement.getByElement(elements, Element.Path).getValue());

		Assert.assertNull(ResultElement.getByElement(elements, Element.Query));

		Assert.assertNull(ResultElement.getByElement(elements, Element.Fragment));
	}
	
	
	@Test
	public void checkHttpUrlWithoutPath(){
		// given
		String url="gopher://gopher.voa.gov";
		
		// when
		List<ResultElement<?>> elements=UrlParser.parse(url);
		
		// then
		Assert.assertNotNull(ResultElement.getByElement(elements, Element.Protocol));
		Assert.assertEquals("gopher", ResultElement.getByElement(elements, Element.Protocol).getValue());

		Assert.assertNotNull(ResultElement.getByElement(elements, Element.Host));
		Assert.assertEquals("gopher.voa.gov", ResultElement.getByElement(elements, Element.Host).getValue());
		
		Assert.assertNull(ResultElement.getByElement(elements, Element.User));
		Assert.assertNull(ResultElement.getByElement(elements, Element.Password));
		
		Assert.assertNotNull(ResultElement.getByElement(elements, Element.Host));
		Assert.assertEquals("gopher.voa.gov", ResultElement.getByElement(elements, Element.Host).getValue());
		
		Assert.assertNull(ResultElement.getByElement(elements, Element.Port));

		Assert.assertNull(ResultElement.getByElement(elements, Element.Path));

		Assert.assertNull(ResultElement.getByElement(elements, Element.Query));

		Assert.assertNull(ResultElement.getByElement(elements, Element.Fragment));
	}
	
	@Test
	public void checkHttpUrlWithPort(){
		// given
		String url="http://ecsdxxxxxxxx.epam.com:9091/intelligenza-collecttiva/service/vision/get-wpa.action";
		
		// when
		List<ResultElement<?>> elements=UrlParser.parse(url);
		
		// then
		Assert.assertNotNull(ResultElement.getByElement(elements, Element.Protocol));
		Assert.assertEquals("http", ResultElement.getByElement(elements, Element.Protocol).getValue());

		Assert.assertNull(ResultElement.getByElement(elements, Element.User));
		Assert.assertNull(ResultElement.getByElement(elements, Element.Password));
		
		Assert.assertNotNull(ResultElement.getByElement(elements, Element.Host));
		Assert.assertEquals("ecsdxxxxxxxx.epam.com", ResultElement.getByElement(elements, Element.Host).getValue());
		
		Assert.assertNotNull(ResultElement.getByElement(elements, Element.Port));
		Assert.assertEquals(9091, ResultElement.getByElement(elements, Element.Port).getValue());

		Assert.assertNotNull(ResultElement.getByElement(elements, Element.Path));
		Assert.assertEquals("intelligenza-collecttiva/service/vision/get-wpa.action", ResultElement.getByElement(elements, Element.Path).getValue());

		Assert.assertNull(ResultElement.getByElement(elements, Element.Query));

		Assert.assertNull(ResultElement.getByElement(elements, Element.Fragment));
	}
	
	@Test
	public void checkHttpUrlWithQuery(){
		// given
		String url="http://www.example.com:8080/services/hotel-search-results.jsp?Ne=292&N=4294967240#order";
		
		// when
		List<ResultElement<?>> elements=UrlParser.parse(url);
		
		// then
		Assert.assertNotNull(ResultElement.getByElement(elements, Element.Protocol));
		Assert.assertEquals("http", ResultElement.getByElement(elements, Element.Protocol).getValue());

		Assert.assertNull(ResultElement.getByElement(elements, Element.User));
		Assert.assertNull(ResultElement.getByElement(elements, Element.Password));
		
		Assert.assertNotNull(ResultElement.getByElement(elements, Element.Host));
		Assert.assertEquals("www.example.com", ResultElement.getByElement(elements, Element.Host).getValue());
		
		Assert.assertNotNull(ResultElement.getByElement(elements, Element.Port));
		Assert.assertEquals(8080, ResultElement.getByElement(elements, Element.Port).getValue());

		Assert.assertNotNull(ResultElement.getByElement(elements, Element.Path));
		Assert.assertEquals("services/hotel-search-results.jsp", ResultElement.getByElement(elements, Element.Path).getValue());

		Assert.assertNotNull(ResultElement.getByElement(elements, Element.Query));
		Assert.assertEquals("Ne=292&N=4294967240", ResultElement.getByElement(elements, Element.Query).getValue());
		

		Assert.assertNotNull(ResultElement.getByElement(elements, Element.Fragment));
		Assert.assertEquals("order", ResultElement.getByElement(elements, Element.Fragment).getValue());
	}
	
	
	@Test
	public void checkFtpSimpleUrl(){
		// given
		String url="ftp://example.com/pub/file.txt";
		
		// when
		List<ResultElement<?>> elements=UrlParser.parse(url);
		
		// then
		Assert.assertNotNull(ResultElement.getByElement(elements, Element.Protocol));
		Assert.assertEquals("ftp", ResultElement.getByElement(elements, Element.Protocol).getValue());

		Assert.assertNull(ResultElement.getByElement(elements, Element.User));
		Assert.assertNull(ResultElement.getByElement(elements, Element.Password));

		Assert.assertNotNull(ResultElement.getByElement(elements, Element.Host));
		Assert.assertEquals("example.com", ResultElement.getByElement(elements, Element.Host).getValue());
		
		Assert.assertNull(ResultElement.getByElement(elements, Element.Port));

		Assert.assertNotNull(ResultElement.getByElement(elements, Element.Path));
		Assert.assertEquals("pub/file.txt", ResultElement.getByElement(elements, Element.Path).getValue());

		Assert.assertNull(ResultElement.getByElement(elements, Element.Query));

		Assert.assertNull(ResultElement.getByElement(elements, Element.Fragment));
	}

	
	@Test
	public void checkFtpUrlWithoutPassword(){
		// given
		String url="ftps://John@example.com:2222/pub/file.txt";
		
		// when
		List<ResultElement<?>> elements=UrlParser.parse(url);
		
		// then
		Assert.assertNotNull(ResultElement.getByElement(elements, Element.Protocol));
		Assert.assertEquals("ftps", ResultElement.getByElement(elements, Element.Protocol).getValue());

		Assert.assertNotNull(ResultElement.getByElement(elements, Element.User));
		Assert.assertEquals("John", ResultElement.getByElement(elements, Element.User).getValue());
		
		Assert.assertNull(ResultElement.getByElement(elements, Element.Password));

		Assert.assertNotNull(ResultElement.getByElement(elements, Element.Host));
		Assert.assertEquals("example.com", ResultElement.getByElement(elements, Element.Host).getValue());
		
		Assert.assertNotNull(ResultElement.getByElement(elements, Element.Port));
		Assert.assertEquals(2222, ResultElement.getByElement(elements, Element.Port).getValue());

		Assert.assertNotNull(ResultElement.getByElement(elements, Element.Path));
		Assert.assertEquals("pub/file.txt", ResultElement.getByElement(elements, Element.Path).getValue());

		Assert.assertNull(ResultElement.getByElement(elements, Element.Query));

		Assert.assertNull(ResultElement.getByElement(elements, Element.Fragment));
	}
	
	@Test
	public void checkFtpFullUrl(){
		// given
		String url="ftps://John:JohnPassword@example.com:2222/pub/file.txt";
		
		// when
		List<ResultElement<?>> elements=UrlParser.parse(url);
		
		// then
		Assert.assertNotNull(ResultElement.getByElement(elements, Element.Protocol));
		Assert.assertEquals("ftps", ResultElement.getByElement(elements, Element.Protocol).getValue());

		Assert.assertNotNull(ResultElement.getByElement(elements, Element.User));
		Assert.assertEquals("John", ResultElement.getByElement(elements, Element.User).getValue());
		
		Assert.assertNotNull(ResultElement.getByElement(elements, Element.Password));
		Assert.assertEquals("JohnPassword", ResultElement.getByElement(elements, Element.Password).getValue());

		Assert.assertNotNull(ResultElement.getByElement(elements, Element.Host));
		Assert.assertEquals("example.com", ResultElement.getByElement(elements, Element.Host).getValue());
		
		Assert.assertNotNull(ResultElement.getByElement(elements, Element.Port));
		Assert.assertEquals(2222, ResultElement.getByElement(elements, Element.Port).getValue());

		Assert.assertNotNull(ResultElement.getByElement(elements, Element.Path));
		Assert.assertEquals("pub/file.txt", ResultElement.getByElement(elements, Element.Path).getValue());

		Assert.assertNull(ResultElement.getByElement(elements, Element.Query));

		Assert.assertNull(ResultElement.getByElement(elements, Element.Fragment));
	}

	@Test
	public void checkPhoneNumber(){
		// given
		String url="tel:+1-816-555-1212";
		
		// when
		List<ResultElement<?>> elements=UrlParser.parse(url);
		
		// then
		Assert.assertNotNull(ResultElement.getByElement(elements, Element.Protocol));
		Assert.assertEquals("tel", ResultElement.getByElement(elements, Element.Protocol).getValue());

		Assert.assertNull(ResultElement.getByElement(elements, Element.User));
		
		Assert.assertNull(ResultElement.getByElement(elements, Element.Password));

		Assert.assertNull(ResultElement.getByElement(elements, Element.Host));
		
		Assert.assertNull(ResultElement.getByElement(elements, Element.Port));

		Assert.assertNotNull(ResultElement.getByElement(elements, Element.Path));
		Assert.assertEquals("+1-816-555-1212", ResultElement.getByElement(elements, Element.Path).getValue());

		Assert.assertNull(ResultElement.getByElement(elements, Element.Query));

		Assert.assertNull(ResultElement.getByElement(elements, Element.Fragment));
	}
	
	// TODO
	public void checkEMail(){
		// given
		String url="mailto:John.Doe@example.com";
		
		// when
		List<ResultElement<?>> elements=UrlParser.parse(url);
		
		// then
		Assert.assertNotNull(ResultElement.getByElement(elements, Element.Protocol));
		Assert.assertEquals("mailto", ResultElement.getByElement(elements, Element.Protocol).getValue());

		Assert.assertNotNull(ResultElement.getByElement(elements, Element.User));
		Assert.assertEquals("John.Doe", ResultElement.getByElement(elements, Element.User));
		
		Assert.assertNull(ResultElement.getByElement(elements, Element.Password));

		Assert.assertNotNull(ResultElement.getByElement(elements, Element.Host));
		Assert.assertEquals("example.com", ResultElement.getByElement(elements, Element.Host));
		
		Assert.assertNull(ResultElement.getByElement(elements, Element.Port));

		Assert.assertNotNull(ResultElement.getByElement(elements, Element.Path));
		Assert.assertEquals("+1-816-555-1212", ResultElement.getByElement(elements, Element.Path).getValue());

		Assert.assertNull(ResultElement.getByElement(elements, Element.Query));

		Assert.assertNull(ResultElement.getByElement(elements, Element.Fragment));
	}
	
	@Test
	public void checkSms(){
		// given
		String url="sms:+19725551212?body=hello%20there";
		
		// when
		List<ResultElement<?>> elements=UrlParser.parse(url);
		
		// then
		Assert.assertNotNull(ResultElement.getByElement(elements, Element.Protocol));
		Assert.assertEquals("sms", ResultElement.getByElement(elements, Element.Protocol).getValue());

		Assert.assertNull(ResultElement.getByElement(elements, Element.User));
		
		Assert.assertNull(ResultElement.getByElement(elements, Element.Password));

		Assert.assertNull(ResultElement.getByElement(elements, Element.Host));
		
		
		Assert.assertNull(ResultElement.getByElement(elements, Element.Port));

		Assert.assertNotNull(ResultElement.getByElement(elements, Element.Path));
		Assert.assertEquals("+19725551212", ResultElement.getByElement(elements, Element.Path).getValue());

		Assert.assertNotNull(ResultElement.getByElement(elements, Element.Query));
		Assert.assertEquals("body=hello%20there", ResultElement.getByElement(elements, Element.Query).getValue());
		

		Assert.assertNull(ResultElement.getByElement(elements, Element.Fragment));
	}
	
}
