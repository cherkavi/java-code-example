package com.cherkashyn.vitalii.computer_shop.opencart.rest.writer;

import java.lang.annotation.Annotation;
import java.text.MessageFormat;
import java.text.ParseException;
import java.util.Date;

import org.jboss.resteasy.spi.StringParameterUnmarshaller;
import org.springframework.stereotype.Component;

import com.cherkashyn.vitalii.computer_shop.opencart.rest.RestUtils;

@Component
public class DateParameterUnmarshaller implements StringParameterUnmarshaller<Date>{
	
	@Override
	public Date fromString(String stringDate) {
		try{
			return RestUtils.dateParse(stringDate);
		}catch(ParseException pe){
			throw new RuntimeException(MessageFormat.format("can't parse Date:{} when expected: {}", stringDate, RestUtils.dateParseTemplate()), pe);
		}
	}

	@Override
	public void setAnnotations(Annotation[] annotations) {
		// private SimpleDateFormat format;
		// DateParameter format=FindAnnotation.findAnnotation(annotations, DateParameter.class);
		// this.format=new SimpleDateFormat(format.value());
	}

}
