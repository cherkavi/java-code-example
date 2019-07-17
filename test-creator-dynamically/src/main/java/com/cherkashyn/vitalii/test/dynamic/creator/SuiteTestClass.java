package com.cherkashyn.vitalii.test.dynamic.creator;

import org.apache.commons.lang3.StringUtils;

import com.cherkashyn.vitalii.test.dynamic.compiler.ClassTestInformation;
import com.cherkashyn.vitalii.test.dynamic.compiler.TestInformation;

public class SuiteTestClass implements TestClassCreator {

	private static final Object	LINE_DELIMITER	= "\n";
	private final String		packageName;
	private final String		className;
	private TestInformation[]	tests;

	public SuiteTestClass(String packageName, String className,
			TestInformation... info) {
		this.packageName = packageName;
		this.className = className;
		this.tests = info;
	}

	@Override
	public ClassTestInformation generate() {
		return new ClassTestInformation(packageName, className,
				generateClassText(packageName, className, tests));
	}

	private String generateClassText(String packageName, String className,
			TestInformation[] methodsInformation) {
		StringBuilder body = new StringBuilder();
		this.addPackageName(body, packageName);
		this.addNecessaryImports(body);
		this.addClassBegin(body, className);
		for (TestInformation eachMethod : methodsInformation) {
			this.addTestMethod(body, eachMethod);
		}
		this.addClassEnd(body);
		return body.toString();
	}

	private void addTestMethod(StringBuilder body, TestInformation eachMethod) {
		body.append(String.format("public void %s() {",
				eachMethod.getMethodName()));
		body.append(LINE_DELIMITER);

		if (eachMethod.getExceptionText() == null) {
			body.append("assertTrue(true);");
			body.append(LINE_DELIMITER);
		} else {
			String stringMessage = StringUtils.replace(
					eachMethod.getExceptionText(), "\"", "\\\"");
			body.append(String.format("assertTrue(\"%s\", false);",
					stringMessage));
			body.append(LINE_DELIMITER);
		}

		body.append("}");
		body.append(LINE_DELIMITER);

	}

	private void addClassBegin(StringBuilder body, String className) {
		body.append(String.format("public class %s extends TestCase{",
				className));
		body.append(LINE_DELIMITER);
		body.append(String.format("public %s(String testMethodName){",
				className));
		body.append("super(testMethodName);");
		body.append(LINE_DELIMITER);
		body.append("}");
		body.append(LINE_DELIMITER);
	}

	private void addClassEnd(StringBuilder body) {
		body.append("}");
		body.append(LINE_DELIMITER);
	}

	private void addNecessaryImports(StringBuilder body) {
		body.append("import junit.framework.Test;");
		body.append(LINE_DELIMITER);
		body.append("import junit.framework.TestCase;");
		body.append(LINE_DELIMITER);
		body.append(LINE_DELIMITER);
	}

	private void addPackageName(StringBuilder body, String packageName) {
		body.append(String.format("package %s;", packageName));
		body.append(LINE_DELIMITER);
	}

}
