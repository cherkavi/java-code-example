package com.cherkashyn.vitalii.test.dynamic;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.cherkashyn.vitalii.test.dynamic.compiler.CompilerUtils;
import com.cherkashyn.vitalii.test.dynamic.compiler.TestInformation;
import com.cherkashyn.vitalii.test.dynamic.creator.SuiteTestClass;

public class AppTest extends TestCase {

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() throws Exception {
		TestSuite suite = new TestSuite();

		TestInformation[] testInformation = new TestInformation[] {
				new TestInformation("first"), new TestInformation("second"),
				new TestInformation("third", "error text is\"nt work ") };

		for (TestInformation eachInformation : testInformation) {
			suite.addTest((TestCase) CompilerUtils.compile(new SuiteTestClass(
					"test_value", "FirstClass", testInformation).generate(),
					eachInformation.getMethodName()));
		}

		return suite;
	}

}
