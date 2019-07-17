package com.cherkashyn.vitalii.testtask.meteogroup.parser.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.cherkashyn.vitalii.testtask.meteogroup.domain.ResultElement;
import com.cherkashyn.vitalii.testtask.meteogroup.parser.Element;

import junit.framework.Assert;

public class ParserStrategyTest {

	@Test
	public void bitePrefix(){
		// given
		ParserStrategy service=getStub();
		String source="example.com/pub/file.txt";
		
		// when
		String result=service.bitePrefix(source, 11);
		
		// then
		Assert.assertEquals("/pub/file.txt", result);
	}

	@Test
	public void bitePrefixOutOfRange(){
		// given
		ParserStrategy service=getStub();
		String source="example.com/pub/file.txt";
		
		// when
		String result=service.bitePrefix(source, 1100);
		
		// then
		Assert.assertEquals("", result);
	}
	
	@Test
	public void biteSuffix(){
		// given
		ParserStrategy service=getStub();
		String source="example.com/pub/file.txt";
		List<ResultElement<?>> result=new ArrayList<ResultElement<?>>();
		
		// when
		String leftOver=service.biteSuffix(source, "/", Element.Path, result);
		
		// then
		Assert.assertEquals("example.com", leftOver);
		Assert.assertTrue(result.size()>0);
		Assert.assertNotNull(ResultElement.getByElement(result, Element.Path));
		Assert.assertEquals("pub/file.txt", ResultElement.getByElement(result, Element.Path).getValue() );
	}
	
	@Test
	public void bitePrefixWithElement(){
		// given
		ParserStrategy service=getStub();
		String source="http://example.com/pub/file.txt";
		List<ResultElement<?>> result=new ArrayList<ResultElement<?>>();
		
		// when
		String leftOver=service.bitePrefix(source, ":", Element.Protocol, result);
		
		// then
		Assert.assertEquals("//example.com/pub/file.txt", leftOver);
		Assert.assertTrue(result.size()>0);
		Assert.assertNotNull(ResultElement.getByElement(result, Element.Protocol));
		Assert.assertEquals("http", ResultElement.getByElement(result, Element.Protocol).getValue() );
	}

	private ParserStrategy getStub() {
		return new ParserStrategy() {
			
			@Override
			List<ResultElement<?>> parse(String url) {
				// TODO Auto-generated method stub
				return null;
			}
		};
		
	}

}
