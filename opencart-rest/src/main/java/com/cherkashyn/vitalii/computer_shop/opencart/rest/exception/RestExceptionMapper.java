package com.cherkashyn.vitalii.computer_shop.opencart.rest.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.springframework.stereotype.Component;

import com.cherkashyn.vitalii.computer_shop.opencart.rest.RestUtils;
import com.cherkashyn.vitalii.computer_shop.opencart.service.exception.ServiceException;

@Provider
@Component
public class RestExceptionMapper implements ExceptionMapper<ServiceException>{

	@Override
	public Response toResponse(ServiceException ex) {
		return RestUtils.buildErrorResponse(ex.getMessage());
	}

}
