package com.cherkashyn.vitalii.settings.connector;

import java.io.File;
import java.lang.reflect.Field;

import org.apache.commons.configuration.AbstractConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.lang3.StringUtils;

public class JdbcConfiguration {
	private final static JdbcConfiguration INSTANCE=new JdbcConfiguration();
	
	private JdbcConfiguration(){
	}
	
	public static JdbcConfiguration.Data readData(File file) throws ConfigurationException, IllegalArgumentException, IllegalAccessException, InstantiationException{
		AbstractConfiguration configuration=INSTANCE.readConfigurationFromFile(file);
		return readObject(configuration, JdbcConfiguration.Data.class);
	}
	
	
	
	
	private static <T> T readObject(AbstractConfiguration configuration,
			Class<T> clazz) throws IllegalArgumentException, IllegalAccessException, InstantiationException {
		/** new instance of class */
		T returnValue=clazz.newInstance();
		
		Field[] fields=clazz.getDeclaredFields();
		for(Field eachField:fields){
			eachField.setAccessible(true);
			eachField.set(returnValue, configuration.getString(eachField.getName()));
		}
		return returnValue;
	}

	private AbstractConfiguration readConfigurationFromFile(File configFile) throws ConfigurationException {
		AbstractConfiguration configuration=null;
		if(isProperties(configFile)){
			configuration=new PropertiesConfiguration(configFile);
		}
		if(isXml(configFile)){
			configuration=new XMLConfiguration(configFile);
		}
		return configuration;
	}


	private final static String FILE_EXTENSION_PROPERTIES="properties";
	private final static String FILE_EXTENSION_XML="xml";
	
	private static boolean isProperties(File configFile) {
		return isEndFileName(configFile, FILE_EXTENSION_PROPERTIES);
	}
	
	/**
	 * is this XML file  
	 * @param configFile
	 * @return
	 */
	private static boolean isXml(File configFile) {
		return isEndFileName(configFile, FILE_EXTENSION_XML);
	}
	/**
	 * check end of file ( extension ) for equals of parameter 
	 * @param configFile
	 * @param suffix
	 * @return
	 */
	private static boolean isEndFileName(File configFile, String suffix) {
		return StringUtils.endsWithIgnoreCase(configFile.getName(), suffix);
	}

	


	
	
	public static class Data{
		private String login;
		private String password;
		private String jdbcUrl;
		private String driverClass;
		
		public Data(){
		}
		
		public Data(String login, String password, String jdbcUrl,
				String driverClass) {
			super();
			this.login = login;
			this.password = password;
			this.jdbcUrl = jdbcUrl;
			this.driverClass = driverClass;
		}
		
		
		public String getLogin() {
			return login;
		}
		public String getPassword() {
			return password;
		}
		public String getJdbcUrl() {
			return jdbcUrl;
		}
		public String getDriverClass() {
			return driverClass;
		}


		@Override
		public String toString() {
			return "Data [login=" + login + ", password=" + password
					+ ", jdbcUrl=" + jdbcUrl + ", driverClass=" + driverClass
					+ "]";
		}
		
	}
	
	
}
