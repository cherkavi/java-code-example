package com.test.xml_generator.values_generator;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.test.xml_generator.values_generator.impl.DateRandomGenerator;

import junit.framework.TestCase;

public class TestDateSequenceGenerator extends TestCase {
	public void testMain() throws ParseException {
		// ISequenceValues generator=new IntegerSequenceGenerator(20, 30);
		ISequenceValues generator=new DateRandomGenerator(new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse("01.01.2011 00:00:00"), 
														  new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse("31.12.2011 23:59:59")
														  );
		
		for(int index=0;index<20;index++){
			System.out.println(index+" >>> "+generator.next());
		}
	}
	
	
}
