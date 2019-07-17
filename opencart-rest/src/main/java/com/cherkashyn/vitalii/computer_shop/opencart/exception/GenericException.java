package com.cherkashyn.vitalii.computer_shop.opencart.exception;

public class GenericException extends Exception{

	private static final long serialVersionUID = 1L;

	public GenericException() {
		super();
	}

	public GenericException(String message, Throwable cause) {
		super(message, cause);
	}

	public GenericException(String message) {
		super(message);
	}

	public GenericException(Throwable cause) {
		super(cause);
	}

	
}
