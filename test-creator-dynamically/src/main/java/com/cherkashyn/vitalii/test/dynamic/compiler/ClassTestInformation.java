package com.cherkashyn.vitalii.test.dynamic.compiler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClassTestInformation {
	private String					packageName;
	private String					className;
	private String					classBody;
	private List<TestInformation>	result;

	public ClassTestInformation(String packageName, String className,
			String classBody, TestInformation... tests) {
		this(packageName, className, classBody, Arrays.asList(tests));
	}

	public ClassTestInformation(String packageName, String className,
			String classBody, List<TestInformation> tests) {
		super();
		this.packageName = packageName;
		this.className = className;
		this.classBody = classBody;
		this.result = tests;
	}

	public String getPackageName() {
		return packageName;
	}

	public String getClassName() {
		return className;
	}

	public String getClassBody() {
		return classBody;
	}

	public List<TestInformation> getResult() {
		// return unmodifiable list
		return new ArrayList<TestInformation>(this.result);
	}

}
