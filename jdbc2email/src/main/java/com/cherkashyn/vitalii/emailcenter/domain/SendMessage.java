package com.cherkashyn.vitalii.emailcenter.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

/**
 * object for reading from DB and sending to SMTP
 */
public class SendMessage {
	/** ID_MESSAGE  					NUMBER                NOT NULL   -- ИД сообщения */
	private Long id;
	/** RECEIVER_EMAIL                 VARCHAR2(100 BYTE)               -- Email получателя */
	private String recipient;
	/**
	 *TITLE_MESSAGE                  VARCHAR2(250 BYTE)               -- Заголовок сообщения 
	 */
	private String subject;
	
	/**
	 * TEXT_MESSAGE                   VARCHAR2(4000 BYTE)              -- Текст сообщения
	 */
	private String text;
	
	/**
	 * HAS_ATTACHED_FILES             CHAR(1 BYTE)          NOT NULL   -- Y - есть присоединенные файлы 
	 */
	private boolean attachment;
	
	/**
	 * SENDER_EMAIL                   VARCHAR2(250 BYTE)    NOT NULL   -- Email отправителя 
	 */
	private String from;
	
	/**
	 * DELAY_NEXT_LETTER              NUMBER(10,0)          NOT NULL   -- Задержка отправки следующего письма
	 */
	private long delayBeforeNext;
	
	/**
	 * SMTP_SERVER                    VARCHAR2(1000 BYTE)   NOT NULL   -- SMTP сервер
	 */
	private String smtpServer;
	
	/**
	 * SMTP_PORT                      NUMBER(10,0)          NOT NULL   -- SMTP порт 
	 */
	private int smtpPort;
	
	/**
	 * SMTP_SSL                       CHAR(1 BYTE)          NOT NULL   -- Y - есть SMTP
	 */
	private boolean smtpSsl;
	
	/**
	 *NEED_AUTORIZATION              CHAR(1 BYTE)          NOT NULL   -- Y - нужна авторизация 
	 */
	private boolean smtpAuthorization;
	
	/**
	 * SMTP_USER                      VARCHAR2(100 BYTE)               -- SMTP пользователь 
	 */
	private String smtpUser;
	
	/**
	 * SMTP_PASSWORD                  VARCHAR2(100 BYTE)               -- SMTP пароль 
	 */
	private String smtpPassword;
	
	
	/*
	 * Ignored fields  
	SENDINGS_QUANTITY              NUMBER                           -- Количество отправок
	ERROR_SENDINGS_QUANTITY        NUMBER                           -- Количество ошибок отправки
	STATE_RECORD                   VARCHAR2(50 BYTE)     NOT NULL   -- Состояние сообщения
	STORED_MESSAGE_FILE_NAME       VARCHAR2(1000 BYTE)              -- Файл сообщения (если сообщение хранится не в базе, а в файле)
	
	-- Данные отправителя
	MAX_ERROR_MESSAGES             NUMBER                           -- Максимальное количество ошибочных сообщени
	 */
	
	public Long getId() {
		return id;
	}

	public String getRecipient() {
		return recipient;
	}

	public String getSubject() {
		return subject;
	}

	public String getText() {
		return text;
	}

	public boolean hasAttachment() {
		return attachment;
	}

	public String getFrom() {
		return from;
	}

	public long getDelayBeforeNext() {
		return delayBeforeNext;
	}

	public String getSmtpServer() {
		return smtpServer;
	}

	public int getSmtpPort() {
		return smtpPort;
	}

	public boolean isSmtpSsl() {
		return smtpSsl;
	}

	public boolean isSmtpAuthorization() {
		return smtpAuthorization;
	}

	public String getSmtpUser() {
		return smtpUser;
	}

	public String getSmtpPassword() {
		return smtpPassword;
	}

	public static class ResultSetMapper implements RowMapper<SendMessage>{

		@Override
		public SendMessage mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			SendMessage returnValue=new SendMessage();
			returnValue.id=resultSet.getLong("ID_MESSAGE");
			returnValue.recipient=resultSet.getString("RECEIVER_EMAIL");
			returnValue.subject=resultSet.getString("TITLE_MESSAGE");
			returnValue.text=resultSet.getString("TEXT_MESSAGE");
			returnValue.attachment=parseBooleanFromChar(resultSet.getString("HAS_ATTACHED_FILES"));
			returnValue.from=resultSet.getString("SENDER_EMAIL");
			returnValue.smtpServer=resultSet.getString("SMTP_SERVER");
			returnValue.smtpPort=resultSet.getInt("SMTP_PORT");
			returnValue.smtpSsl=parseBooleanFromChar(resultSet.getString("SMTP_SSL"));
			returnValue.smtpAuthorization=parseBooleanFromChar(resultSet.getString("NEED_AUTORIZATION"));
			returnValue.smtpUser=resultSet.getString("SMTP_USER");
			returnValue.smtpPassword=resultSet.getString("SMTP_PASSWORD");
			returnValue.delayBeforeNext=resultSet.getLong("DELAY_NEXT_LETTER");
			return returnValue;
		}
		
		
		
		/**
		 * parse String value for "Y", "y" value 
		 * @param value
		 * @return
		 */
		private boolean parseBooleanFromChar(String value){
			return BooleanConverter.parseBooleanFromChar(value);
		}
		
	}

	@Override
	public String toString() {
		return "SendMessage [id=" + id + ", recipient=" + recipient
				+ ", subject=" + subject + "]";
	}

	
}
