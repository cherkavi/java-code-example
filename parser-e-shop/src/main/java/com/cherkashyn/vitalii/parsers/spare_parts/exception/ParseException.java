package com.cherkashyn.vitalii.parsers.spare_parts.exception;

public class ParseException extends Exception{

	private static final long serialVersionUID = 1L;

	public ParseException() {
		super();
	}

	public ParseException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public ParseException(String arg0) {
		super(arg0);
	}

	public ParseException(Throwable arg0) {
		super(arg0);
	}

}
