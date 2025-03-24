package com.ubs.omnia.tools.db2csv.exception;

public class GenericConverterException extends Exception{

	private static final long serialVersionUID = 1L;

	public GenericConverterException() {
		super();
	}

	public GenericConverterException(String message) {
		super(message);
	}

	public GenericConverterException(Throwable cause) {
		super(cause);
	}

	public GenericConverterException(String message, Throwable cause) {
		super(message, cause);
	}


}
