package com.cherkashyn.vitalii.test.dynamic.compiler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

import junit.framework.TestCase;

import org.apache.commons.lang3.StringUtils;

public class CompilerUtils {
	private final static String	JAVA_EXTENSION	= ".java";
	private final static String	JAVA_COMPILED	= ".class";

	private final static File	DIRECTORY_TEMP;
	static {
		String pathToTemp = System.getProperty("java.io.tmpdir");
		if (!StringUtils.endsWith(pathToTemp, File.separator)) {
			pathToTemp += File.separator;
		}
		DIRECTORY_TEMP = new File(pathToTemp + "automation_tests");
	}

	private static File createJavaFile(File directory,
			ClassTestInformation classInfo) {
		String pathToDir = directory.getAbsolutePath();
		if (!StringUtils.endsWith(pathToDir, File.separator)) {
			pathToDir += File.separator;
		}
		String pathToJavaPackage = pathToDir
				+ StringUtils.replace(classInfo.getPackageName(), ".",
						File.separator) + File.separator;
		File dirToJavaSource = new File(pathToJavaPackage);

		if (!dirToJavaSource.exists()) {
			dirToJavaSource.mkdirs();
		}

		return new File(pathToJavaPackage + classInfo.getClassName()
				+ JAVA_EXTENSION);
	}

	@SuppressWarnings("resource")
	public static TestCase compile(ClassTestInformation classInfo,
			Object... parameters) throws IOException, ClassNotFoundException,
			InstantiationException, IllegalAccessException,
			NoSuchMethodException, SecurityException, IllegalArgumentException,
			InvocationTargetException {

		// create file
		File fileSource = createJavaFile(DIRECTORY_TEMP, classInfo);

		new FileWriter(fileSource).append(classInfo.getClassBody()).close();
		fileSource.deleteOnExit();
		// System.out.println("created:" + fileSource.getAbsolutePath());

		// Compile source file.
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		compiler.run(null, null, null, fileSource.getAbsolutePath());
		getCompiledFile(fileSource).deleteOnExit();

		// System.out.println("compiled");

		// Load and instantiate compiled class.
		// addSoftwareLibrary(fileCompiled);
		URLClassLoader classLoader = URLClassLoader
				.newInstance(new URL[] { DIRECTORY_TEMP.toURI().toURL() });

		@SuppressWarnings("unchecked")
		Class<? extends TestCase> clazz = (Class<? extends TestCase>) Class
				.forName(
						classInfo.getPackageName() + "."
								+ classInfo.getClassName(), true, classLoader);

		if (parameters == null || parameters.length == 0) {
			return clazz.newInstance();
		} else {
			Class<?>[] parametersClasses = getClasses(parameters);
			Constructor<? extends TestCase> constructor = clazz
					.getConstructor(parametersClasses);
			return constructor.newInstance(parameters);
		}

	}

	private static File getCompiledFile(File fileSource) {
		String pathToFile = fileSource.getAbsolutePath();
		return new File(StringUtils.removeEnd(pathToFile, JAVA_EXTENSION)
				+ JAVA_COMPILED);
	}

	// weak place - null, subclasses/interfaces
	/**
	 * for certain types only, not for nulls,subclasses, interfaces
	 * 
	 * @param parameters
	 * @return
	 */
	private static Class<?>[] getClasses(Object[] objects) {
		Class<?>[] returnValue = new Class<?>[objects.length];
		for (int index = 0; index < objects.length; index++) {
			returnValue[index] = objects[index].getClass();
		}
		return returnValue;
	}

	@Override
	protected void finalize() throws Throwable {
		DIRECTORY_TEMP.deleteOnExit();
	}
}
