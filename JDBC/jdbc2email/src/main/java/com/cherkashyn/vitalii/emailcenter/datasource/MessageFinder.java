package com.cherkashyn.vitalii.emailcenter.datasource;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.cherkashyn.vitalii.emailcenter.domain.SendMessage;

public class MessageFinder {
	
	@Autowired
	private DataSource dataSource;
	
	/**
	 * SQL query for retrieving values from DB 
	 */
	private String findNext;
	
	public MessageFinder(String findNext) {
		this.findNext = findNext;
	}

	public List<SendMessage> findNext(){
		return new JdbcTemplate(dataSource).query(findNext, new SendMessage.ResultSetMapper());
	}
	
}
