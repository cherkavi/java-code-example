package com.ubs.omnia.tools.db2csv.settings;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.configuration.AbstractConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.XMLConfiguration;

import com.ubs.omnia.tools.db2csv.exception.GenericConverterException;
import com.ubs.omnia.tools.db2csv.settings.Parameters.Query;

public class ParameterReader {
	
	public static Parameters readFromXml(File file) throws MalformedURLException, GenericConverterException{
		return readFromXml(file.toURI().toURL());
	}

	public static Parameters readFromPropertiesFile(File file) throws MalformedURLException, GenericConverterException{
		return readFromPropertiesFile(file.toURI().toURL());
	}
	
	public static Parameters readFromXml(URL url) throws GenericConverterException{
		AbstractConfiguration configuration=null;
		try {
			configuration = new XMLConfiguration(url.getFile());
		} catch (ConfigurationException e) {
			throw new GenericConverterException("can't read configuration from file: ", e);
		}
		
		return readParameters(configuration);
	}

	public static Parameters readFromPropertiesFile(URL url) throws GenericConverterException{
		AbstractConfiguration configuration=null;
		try {
			configuration = new PropertiesConfiguration(url.getFile());
		} catch (ConfigurationException e) {
			throw new GenericConverterException("can't read configuration from file: ", e);
		}
		return readParameters(configuration);
	}
	
	
	private static Parameters readParameters(AbstractConfiguration configuration){
		Parameters returnValue=new Parameters();
		Parameters.JdbcParameters jdbcParameter=new Parameters.JdbcParameters();
		jdbcParameter.driverName=configuration.getString("jdbc.class");
		jdbcParameter.url=configuration.getString("jdbc.url");
		jdbcParameter.userName=configuration.getString("jdbc.login");
		jdbcParameter.password=configuration.getString("jdbc.password");
		returnValue.setJdbcParameters(jdbcParameter);
		
		returnValue.setQuery(new Query(configuration.getString("query.sql")));
		
		returnValue.setOutputUrl(configuration.getString("output.path"));
		
		return returnValue;
	}
}
