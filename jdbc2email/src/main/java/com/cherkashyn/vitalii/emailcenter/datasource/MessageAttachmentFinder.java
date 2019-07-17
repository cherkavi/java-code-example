package com.cherkashyn.vitalii.emailcenter.datasource;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.cherkashyn.vitalii.emailcenter.domain.MessageAttachment;
import com.cherkashyn.vitalii.emailcenter.domain.SendMessage;

public class MessageAttachmentFinder {
	
	@Autowired
	private DataSource dataSource;
	
	private String findSql;
	
	public MessageAttachmentFinder(String findAttachmentById) {
		this.findSql = findAttachmentById;
	}

	public List<MessageAttachment> findAttachments(SendMessage sendMessage){
		return new JdbcTemplate(dataSource).query(findSql, new Object[]{sendMessage.getId()}, new MessageAttachment.ResultSetMapper());
	}

}
