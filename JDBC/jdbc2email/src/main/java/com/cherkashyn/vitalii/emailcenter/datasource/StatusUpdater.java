package com.cherkashyn.vitalii.emailcenter.datasource;


import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class StatusUpdater {
	@Autowired
	private DataSource dataSource;
	
	private final String sqlSuccessfully;
	private final String sqlError;

	public StatusUpdater(String sqlSuccessfully, String sqlError) {
		super();
		this.sqlSuccessfully = sqlSuccessfully;
		this.sqlError = sqlError;
	}

	public void sendSuccessfully(Long idMessage){
		new JdbcTemplate(dataSource).update(sqlSuccessfully, idMessage);
	}
	
	public void sendError(Long idMessage, String errorDescription){
		new JdbcTemplate(dataSource).update(sqlError, StringUtils.EMPTY, idMessage );
		// new JdbcTemplate(dataSource).update(sqlError, new PreparedStatementSetter(){
	}
	
}
