package com.cherkashyn.vitalii.indirector.workers.service.exception;

public class RepeatInsertException extends ServiceException{
	private static final long serialVersionUID = 1L;

	public RepeatInsertException() {
		super();
	}

	public RepeatInsertException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public RepeatInsertException(String message, Throwable cause) {
		super(message, cause);
	}

	public RepeatInsertException(String message) {
		super(message);
	}

	public RepeatInsertException(Throwable cause) {
		super(cause);
	}

}
