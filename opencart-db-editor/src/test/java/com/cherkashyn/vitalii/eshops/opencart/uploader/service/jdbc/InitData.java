package com.cherkashyn.vitalii.eshops.opencart.uploader.service.jdbc;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.hsqldb.cmdline.SqlFile;
import org.hsqldb.cmdline.SqlToolError;

public class InitData {
	private static final String FILE_DDL_SCRIPT="/test/sql/ddl.sql";
	// private static final String HSQLDB_URL="jdbc:hsqldb:mem:opencart";
	// private static final String JDBC_URL="jdbc:hsqldb:file:opencart";
	private static final String JDBC_URL="jdbc:mysql://127.0.0.1:3306/opencart_test";
	private static final String JDBC_LOGIN="root";
	private static final String JDBC_PASSWORD="root";
	private static final String JDBC_DRIVER= com.mysql.jdbc.Driver.class.getName();
	
	public static void init(Connection connection) throws SqlToolError, SQLException, IOException, URISyntaxException{
		URL url = InitData.class.getResource(FILE_DDL_SCRIPT);
		org.hsqldb.cmdline.SqlFile sqlFile=new SqlFile(new File(url.toURI()));
		sqlFile.setAutoClose(true);
		sqlFile.setConnection(connection);
		sqlFile.execute();
	}
	
	public static DataSource createDataSource(){
		BasicDataSource dataSource=new BasicDataSource();
		dataSource.setDefaultAutoCommit(false);
		dataSource.setUrl(JDBC_URL);
		dataSource.setUsername(JDBC_LOGIN);
		dataSource.setPassword(JDBC_PASSWORD);
		dataSource.setDriverClassName(JDBC_DRIVER);
		return dataSource;
	}
	
}

