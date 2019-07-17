package com.cherkashyn.vitalii.jobtask.csvreader.exception;

public class SourceRuntimeException extends GeneralRuntimeException{

	private static final long serialVersionUID = 1L;

	public SourceRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public SourceRuntimeException(String message) {
		super(message);
	}
	
}
