package com.cherkashyn.vitalii.testtask.immobilienscout.dto;

public class ResponseError {
	private final String message;

	public ResponseError(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
	
}
