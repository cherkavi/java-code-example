package com.cherkashyn.vitalii.barrette.service.exception;

public class GeneralServiceException extends Exception{
	private static final long serialVersionUID = 1L;

	public GeneralServiceException() {
		super();
	}

	public GeneralServiceException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public GeneralServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public GeneralServiceException(String message) {
		super(message);
	}

	public GeneralServiceException(Throwable cause) {
		super(cause);
	}
	
}
