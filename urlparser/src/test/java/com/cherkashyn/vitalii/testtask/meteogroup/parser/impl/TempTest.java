package com.cherkashyn.vitalii.testtask.meteogroup.parser.impl;

import java.util.List;

import org.junit.Test;

import com.cherkashyn.vitalii.testtask.meteogroup.domain.ResultElement;
import com.cherkashyn.vitalii.testtask.meteogroup.parser.Element;

import junit.framework.Assert;

public class TempTest {
	
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

}
