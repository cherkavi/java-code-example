package com.cherkashyn.vitalii.tools.persist;

public class PersistException extends Exception{
	private static final long serialVersionUID = 1L;

	public PersistException(String message, Throwable cause) {
		super(message, cause);
	}

	public PersistException(String message) {
		super(message);
	}

	
}
