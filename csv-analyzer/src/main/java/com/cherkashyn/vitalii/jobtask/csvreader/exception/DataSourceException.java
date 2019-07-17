package com.cherkashyn.vitalii.jobtask.csvreader.exception;

public class DataSourceException extends SourceException{

	private static final long serialVersionUID = 1L;

	public DataSourceException(String message) {
		super(message);
	}

	public DataSourceException(String message, Throwable cause) {
		super(message, cause);
	}

}
