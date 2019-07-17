package com.cherkashyn.vitalii.jobtask.csvreader.exception;

public class GeneralRuntimeException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public GeneralRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public GeneralRuntimeException(String message) {
		super(message);
	}
	
}
