package com.cherkashyn.vitalii.test.dynamic.compiler;

public class TestInformation {

	private String	methodName;
	private String	exceptionText;

	public TestInformation(String methodName) {
		this(methodName, null);
	}

	public TestInformation(String methodName, String exceptionText) {
		super();
		this.methodName = methodName;
		this.exceptionText = exceptionText;
	}

	public String getMethodName() {
		return methodName;
	}

	/**
	 * 
	 * @return <ul>
	 *         <li><b>null</b> - method passed</li>
	 *         <li><b>text</b> - method failed with text</li>
	 *         </ul>
	 */
	public String getExceptionText() {
		return exceptionText;
	}

}
