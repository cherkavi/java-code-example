package com.cherkashyn.vitalii.tools.sqlexecutor;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.stringtemplate.v4.ST;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SqlexecutorApplication {
	private final static String SEPARATOR = "=";
	private static final Logger LOG = Logger.getLogger(SqlexecutorApplication.class.getName());

	public static void main(String[] args) throws SQLException{
		initLogLevel();
		try {
			LOG.info("read parameters");
			InputParameters inputParameters = readFromCommandLine(args);
			LOG.info("obtain SQL from file:");
			String sql = enrichTemplate(inputParameters.getSqlTemplate(), inputParameters.getTemplateValues());
			LOG.info(sql);
			LOG.info("obtain connection ");
			Connection connection = getDataSource(inputParameters.getBrandServerSettings());
			try {
                int resultRecords = connection.createStatement().executeUpdate(sql);
            	LOG.info("affected records: "+resultRecords);
			} finally{
                closeQuietly(connection);
            }
		} catch (RuntimeException e) {
			System.out.println("execution with errors : "+e.getMessage());
			System.exit(1);
		}
	}

	private static void initLogLevel() {
		LOG.setLevel(Level.OFF);
		String logLevel = StringUtils.upperCase(StringUtils.trimToNull(System.getProperty("LogLevel")));
		if(logLevel==null){
			return;
		}
		try{
			Level level = Level.parse(logLevel);
			LOG.setLevel(level);
			// LOG.addHandler(new ConsoleHandler());
		}catch(IllegalArgumentException ex){
		}
	}

	private static void closeQuietly(Connection connection) {
		try{
            connection.close();
        }catch(SQLException ex){}
	}

//freemarker
//	static String enrichTemplate(File sqlTemplate, List<String> templateValues) {
//		Template template = null;
//		try {
//			Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
//			cfg.setDirectoryForTemplateLoading(sqlTemplate.getParentFile());
//			cfg.setDefaultEncoding("UTF-8");
//			cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
//			template = cfg.getTemplate(sqlTemplate.getName().replaceAll("\\\\", "/"));
//		} catch (IOException ex) {
//			throw new IllegalArgumentException("can't parse template : "+sqlTemplate, ex);
//		}
//		StringWriter writer = new StringWriter();
//		try{
//			template.process(parseToPairs(templateValues), writer);
//		}catch (IOException | TemplateException ex){
//			throw new IllegalArgumentException("can't execute template : "+sqlTemplate, ex);
//		}
//		return writer.toString();
//	}

	static String enrichTemplate(File sqlTemplate, List<String> templateValues){
		ST template = new ST(readFileAsString(sqlTemplate));
		parseToPairs(templateValues).entrySet().forEach(entry->template.add(entry.getKey(), entry.getValue()));
		return template.render();
	}

	private static String readFileAsString(File sqlTemplate) {
		try(FileReader input = new FileReader(sqlTemplate)){
			return IOUtils.readLines(input).stream().reduce((previous, current)->previous+current).orElse(StringUtils.EMPTY);
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("file was not found : "+sqlTemplate);
		} catch (IOException ex) {
			throw new IllegalArgumentException("can't read file  : "+sqlTemplate, ex);
		}
	}

	static Map<String, String> parseToPairs(List<String> templateValues) {
		Map<String, String> returnValue = new HashMap<>();
		templateValues.stream().forEach(value->{
			returnValue.put(StringUtils.trim(StringUtils.substringBefore(value, SEPARATOR)),
					StringUtils.trim(StringUtils.substringAfter(value, SEPARATOR)));
		});
		return returnValue;
	}

	static Connection getDataSource(File brandServerSettings){
		Yaml yaml = new Yaml();
		try(InputStream reader = new FileInputStream(brandServerSettings)){
			Iterable<?> dataValues = (Iterable<?>) yaml.loadAll(reader);
			for(Map<?,?> each : (Iterable<Map<?,?>>)dataValues){
				Map<?,?> datasource = (Map<?,?>)each.get("datasource");
				if(datasource==null){
					continue;
				}
				Map<?,?> datasourceBrandServer = (Map<?,?>)datasource.get("brandserver");
				if(datasourceBrandServer==null){
					continue;
				}

				Class.forName((String) datasourceBrandServer.get("driver-class-name"));
				return DriverManager.getConnection((String)datasourceBrandServer.get("url"),
						(String)datasourceBrandServer.get("username"),
						(String)datasourceBrandServer.get("password"));
			}
			throw new IllegalArgumentException("can't find datasource.brandserver");
		}catch(IOException ex){
			throw new IllegalArgumentException("can't read yaml file: "+brandServerSettings);
		}catch(ClassNotFoundException cnex){
			throw new IllegalArgumentException("can't obtain jdbc driver class by name "+cnex);
		}catch(SQLException sqlex){
			throw new IllegalStateException("can't obtain SQL connection "+sqlex);
		}
	}

	static InputParameters readFromCommandLine(String[] args){
		InputParameters returnValue = new InputParameters();
		CmdLineParser parser = new CmdLineParser(returnValue);
		try {
			parser.parseArgument(args);
		} catch (CmdLineException e) {
			parser.printSingleLineUsage(System.out);System.out.println();
			throw new IllegalArgumentException("can't read input parameters "+args);
		}
		if(returnValue.getSqlTemplate()==null || returnValue.getBrandServerSettings()==null){
			parser.printSingleLineUsage(System.out);System.out.println();
			throw new IllegalArgumentException("file(s) should be provided");
		}
		return returnValue;
	}

}
