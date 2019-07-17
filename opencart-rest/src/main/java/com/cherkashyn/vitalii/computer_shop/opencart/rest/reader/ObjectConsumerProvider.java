package com.cherkashyn.vitalii.computer_shop.opencart.rest.reader;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.Consumes;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;

import org.springframework.stereotype.Service;

@Service
@Provider
@Consumes("application/json")
public class ObjectConsumerProvider implements MessageBodyReader<Object>{

	@Override
	public boolean isReadable(final Class<?> arg0, final Type arg1, final Annotation[] arg2,
			final MediaType arg3) {
		// TODO REST read objects from Http query ( check for readable ) 
		return false;
	}

	@Override
	public Object readFrom(final Class<Object> arg0, final Type arg1, final Annotation[] arg2, final MediaType arg3, final MultivaluedMap<String, String> arg4, final InputStream arg5) throws IOException, WebApplicationException {
		// TODO REST read objects from Http query ( convert from String to object  ) 
		return null;
	}


}
