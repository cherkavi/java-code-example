package com.cherkashyn.vitalii.test.dynamic.creator;

import com.cherkashyn.vitalii.test.dynamic.compiler.ClassTestInformation;

/**
 * generate text of java which can be compiled
 */
public interface TestClassCreator {
	/**
	 * @return generated value
	 */
	public ClassTestInformation generate();
}
