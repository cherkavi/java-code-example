package com.cherkashyn.vitalii.barrette.service.exception;

import com.cherkashyn.vitalii.accounting.exception.GeneralServiceException;

public class TransferServiceException extends GeneralServiceException {
	private static final long	serialVersionUID	= 1L;

	public TransferServiceException() {
		super();
	}

	public TransferServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public TransferServiceException(String message) {
		super(message);
	}

	public TransferServiceException(Throwable cause) {
		super(cause);
	}

}
