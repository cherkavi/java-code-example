package com.ubs.omnia.tools.db2csv.exception;


public class GenericWriterException extends GenericConverterException {

	private static final long serialVersionUID = 1L;

	public GenericWriterException() {
		super();
	}

	public GenericWriterException(String message, Throwable cause) {
		super(message, cause);
	}

	public GenericWriterException(String message) {
		super(message);
	}

	public GenericWriterException(Throwable cause) {
		super(cause);
	}

}
