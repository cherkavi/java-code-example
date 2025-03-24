package com.cherkashyn.vitalii.emailcenter.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

/**
 * ID_MESSAGE, FILE_NAME, STORED_FILE_NAME
 */
public class MessageAttachment {
	private Long idMessage;
	private String fileName;
	private String filePath;
	
	
	public Long getIdMessage() {
		return idMessage;
	}

	public String getFileName() {
		return fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public static class ResultSetMapper implements RowMapper<MessageAttachment>{
		
		@Override
		public MessageAttachment mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			MessageAttachment returnValue=new MessageAttachment();
			returnValue.idMessage=resultSet.getLong("ID_MESSAGE");
			returnValue.fileName=resultSet.getString("FILE_NAME");
			returnValue.filePath=resultSet.getString("STORED_FILE_NAME");
			return returnValue;
		}
	}
}
