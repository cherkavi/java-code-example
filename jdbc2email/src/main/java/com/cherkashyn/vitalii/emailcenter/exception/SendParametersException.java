package com.cherkashyn.vitalii.emailcenter.exception;

public class SendParametersException extends SendException{
	private static final long serialVersionUID = 1L;

	public SendParametersException(String message) {
		super(message);
	}

	public SendParametersException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
