package com.cherkashyn.vitalii.computer_shop.opencart.rest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.core.Response;

public class RestUtils {
	
	public final static SimpleDateFormat FORMAT_DATE=new SimpleDateFormat("dd.MM.yyyy");
	public final static SimpleDateFormat FORMAT_DATETIME=new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

	/**
	 * 
	 * @return
	 */
	public static String dateParseTemplate(){
		return FORMAT_DATE.getDateFormatSymbols().toString();
	}
	
	/**
	 * 
	 * @return
	 */
	public static String dateTimeParseTemplate(){
		return FORMAT_DATETIME.getDateFormatSymbols().toString();
	}
	/**
	 * convert from String representation to Date ( without Time !!! ) 
	 * <br/> {@link RestUtils#FORMAT_DATE}
	 * @param dateStringRepresentation
	 * @return
	 * @throws ParseException
	 */
	public static Date dateParse(String dateStringRepresentation) throws ParseException{
		return FORMAT_DATE.parse(dateStringRepresentation);
	}

	/**
	 * convert from String representation to Date+Time
	 * <br/> {@link RestUtils#FORMAT_DATETIME}
	 * @param dateStringRepresentation
	 * @return
	 * @throws ParseException
	 */
	public static Date dateTimeParse(String dateStringRepresentation) throws ParseException{
		return FORMAT_DATETIME.parse(dateStringRepresentation);
	}
	
	/**
	 * convert from Date to String representation 
	 * <br/> {@link RestUtils#FORMAT_DATE}
	 * @param date
	 * @return
	 */
	public static String dateConvert(Date date){
		return FORMAT_DATE.format(date);
	}
	
	/**
	 * convert from DateTime to String representation 
	 * <br/> {@link RestUtils#FORMAT_DATETIME}
	 * @param date
	 * @return
	 */
	public static String dateTimeConvert(Date date){
		return FORMAT_DATETIME.format(date);
	}
	

	public static Response buildPositiveResponse(Object entity){
		return Response.status(Response.Status.OK).entity(entity).build();
	}

	public static Response buildCreatedResponse(Object entity){
		return Response.status(Response.Status.CREATED).entity(entity).build();
	}

	public static Response buildErrorResponse(String message) {
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(message).build();
	}

}
