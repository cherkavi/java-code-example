package com.cherkashyn.vitalii.computer_shop.opencart.service.exception;

import com.cherkashyn.vitalii.computer_shop.opencart.exception.GenericException;

public class ServiceException extends GenericException{

	private static final long serialVersionUID = 1L;

	public ServiceException() {
		super();
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(Throwable cause) {
		super(cause);
	}

	
}
