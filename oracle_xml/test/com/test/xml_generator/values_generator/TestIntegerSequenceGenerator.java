package com.test.xml_generator.values_generator;

import com.test.xml_generator.values_generator.impl.RandomIntegerSequenceGenerator;

import junit.framework.TestCase;

public class TestIntegerSequenceGenerator extends TestCase {
	public void testMain(){
		// ISequenceValues generator=new IntegerSequenceGenerator(20, 30);
		int minValue=20;
		int maxValue=30;
		ISequenceValues generator=new RandomIntegerSequenceGenerator(minValue, maxValue);
		for(int index=0;index<20;index++){
			int nextValue=Integer.parseInt(generator.next());
			assertTrue(minValue<=nextValue);
			assertTrue(maxValue>=nextValue);
			System.out.println(index+" >>> "+generator.next());
		}
	}
}
