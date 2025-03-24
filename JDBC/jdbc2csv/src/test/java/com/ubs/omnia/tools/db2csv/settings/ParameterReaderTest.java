package com.ubs.omnia.tools.db2csv.settings;

import java.net.URL;

import junit.framework.Assert;

import org.junit.Test;

import com.ubs.omnia.tools.db2csv.exception.GenericConverterException;

public class ParameterReaderTest {
	private final static String CONFIGURATION_XML_PATH = "/test_configuration.xml";
	private final static String CONFIGURATION_PROPERTIES_PATH = "/test_configuration.properties";
	

	@Test
	public void readConfigurationFromXmlFile() throws GenericConverterException {
		// given
		URL configurationURL = ParameterReaderTest.class
				.getResource(CONFIGURATION_XML_PATH);

		// when
		Parameters parameters = ParameterReader.readFromXml(configurationURL);

		// then
		Assert.assertNotNull(parameters);
		Assert.assertNotNull(parameters.getJdbcParameters());
		Assert.assertNotNull(parameters.getJdbcParameters().driverName);
		Assert.assertEquals("test_class", parameters.getJdbcParameters().driverName);
		
		Assert.assertNotNull(parameters.getJdbcParameters().password);
		Assert.assertEquals("test_password", parameters.getJdbcParameters().password);
		
		Assert.assertNotNull(parameters.getJdbcParameters().userName);
		Assert.assertEquals("test_login", parameters.getJdbcParameters().userName);
		
		Assert.assertNotNull(parameters.getJdbcParameters().url);
		Assert.assertEquals("test_url", parameters.getJdbcParameters().url);
		
		Assert.assertNotNull(parameters.getOutputUrl());
		Assert.assertEquals("test_path", parameters.getOutputUrl());
		
		Assert.assertNotNull(parameters.getQuery());
		Assert.assertNotNull(parameters.getQuery().sqlQuery);
		Assert.assertEquals("test select", parameters.getQuery().sqlQuery);
		
	}

	@Test
	public void readConfigurationFromPropertiesFile() throws GenericConverterException {
		// given
		URL configurationURL = ParameterReaderTest.class
				.getResource(CONFIGURATION_PROPERTIES_PATH);

		// when
		Parameters parameters = ParameterReader.readFromPropertiesFile(configurationURL);

		// then
		Assert.assertNotNull(parameters);
		Assert.assertNotNull(parameters.getJdbcParameters());
		Assert.assertNotNull(parameters.getJdbcParameters().driverName);
		Assert.assertEquals("test_class", parameters.getJdbcParameters().driverName);
		
		Assert.assertNotNull(parameters.getJdbcParameters().password);
		Assert.assertEquals("test_password", parameters.getJdbcParameters().password);
		
		Assert.assertNotNull(parameters.getJdbcParameters().userName);
		Assert.assertEquals("test_login", parameters.getJdbcParameters().userName);
		
		Assert.assertNotNull(parameters.getJdbcParameters().url);
		Assert.assertEquals("test_url", parameters.getJdbcParameters().url);
		
		Assert.assertNotNull(parameters.getOutputUrl());
		Assert.assertEquals("test_path", parameters.getOutputUrl());
		
		Assert.assertNotNull(parameters.getQuery());
		Assert.assertNotNull(parameters.getQuery().sqlQuery);
		Assert.assertEquals("test select", parameters.getQuery().sqlQuery);
		
	}

}
