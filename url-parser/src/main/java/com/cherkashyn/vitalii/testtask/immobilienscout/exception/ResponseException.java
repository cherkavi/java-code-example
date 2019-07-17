package com.cherkashyn.vitalii.testtask.immobilienscout.exception;

public class ResponseException extends AnalyzingException{

	private static final long serialVersionUID = 1L;

	public ResponseException() {
		super();
	}

	public ResponseException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public ResponseException(String arg0) {
		super(arg0);
	}

	public ResponseException(Throwable arg0) {
		super(arg0);
	}

}
