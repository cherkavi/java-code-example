package com.cherkashyn.vitalii.accounting.exception;

public class GeneralServiceException extends GeneralException {
	private static final long	serialVersionUID	= 1L;

	public GeneralServiceException() {
		super();
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
