package com.cherkashyn.vitalii.emailcenter.exception;

/**
 * exception during sending message 
 */
public class SendException extends Exception{
	private static final long serialVersionUID = 1L;

	public SendException(String message) {
		super(message);
	}

	public SendException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
