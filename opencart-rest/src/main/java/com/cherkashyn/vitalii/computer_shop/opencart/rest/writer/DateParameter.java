package com.cherkashyn.vitalii.computer_shop.opencart.rest.writer;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.jboss.resteasy.annotations.StringParameterUnmarshallerBinder;

@Retention(RetentionPolicy.RUNTIME)
@StringParameterUnmarshallerBinder(DateParameterUnmarshaller.class)
public @interface DateParameter {
	String value();
}
