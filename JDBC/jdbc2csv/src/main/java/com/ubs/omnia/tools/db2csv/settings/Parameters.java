package com.ubs.omnia.tools.db2csv.settings;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.Map;

import com.ubs.omnia.tools.db2csv.exception.GenericConverterException;

public class Parameters {
	/**  connection with database */
	public static class JdbcParameters{
		String url;
		String userName;
		String password;
		String driverName;
		
		public Connection connect() throws GenericConverterException{
			try{
				Class.forName(driverName);
			}catch(ClassNotFoundException e){
				throw new GenericConverterException("can't access to class: "+driverName);
			}
			try{
				return DriverManager.getConnection(url, userName, password);
			}catch(SQLException e){
				throw new GenericConverterException(MessageFormat.format("can''t connect to DB: ", url, userName, password));
			}
		}
	}

	/** sql query with parameters */
	public static class Query{
		public String sqlQuery;
		public Map<String, String> parameters;

		public Query(String text){
			this.sqlQuery=text;
		}
		
		public String getSql() {
			return this.sqlQuery;
		}
	}
	
	
	/** DB connection */
	private JdbcParameters jdbcParameters;
	/** sql query */
	private Query query;
	/** path to output data */
	private String outputUrl;
	/** CSV file column separator */
	private String delimiter;
	
	public JdbcParameters getJdbcParameters() {
		return jdbcParameters;
	}
	public void setJdbcParameters(JdbcParameters jdbcParameters) {
		this.jdbcParameters = jdbcParameters;
	}
	public Query getQuery() {
		return query;
	}
	public void setQuery(Query query) {
		this.query = query;
	}
	public String getOutputUrl() {
		return outputUrl;
	}
	public void setOutputUrl(String outputUrl) {
		this.outputUrl = outputUrl;
	}
	public String getDelimiter() {
		return delimiter;
	}
	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}
	
}


