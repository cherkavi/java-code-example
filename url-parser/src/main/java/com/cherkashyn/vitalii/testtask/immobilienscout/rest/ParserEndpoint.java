package com.cherkashyn.vitalii.testtask.immobilienscout.rest;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cherkashyn.vitalii.testtask.immobilienscout.dto.PageDescription;
import com.cherkashyn.vitalii.testtask.immobilienscout.dto.ResponseError;
import com.cherkashyn.vitalii.testtask.immobilienscout.exception.AnalyzingException;
import com.cherkashyn.vitalii.testtask.immobilienscout.service.PageInformationFactory;
import com.cherkashyn.vitalii.testtask.immobilienscout.service.report.PageReport;

@Controller

@RequestMapping("/parser")
public class ParserEndpoint {
	private final static Logger LOGGER=Logger.getLogger(ParserEndpoint.class);
	
	private final static String HTTP_PREFIX="http://";
	private final static String HTTP_PREFIX2="http://";
	
	@Autowired
	PageReport pageReport;
	
	@Autowired
	PageInformationFactory factory;
	
	@RequestMapping(value="/analyze", method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<PageDescription> analyzeUrl( 
			@RequestParam(value="url", required=true) String url) throws AnalyzingException, IOException {
		String fixedUrl=fixHttpPrefix(url);
		LOGGER.debug("URL: "+url);
		return pageReport.get(factory.analyze(fixedUrl));
	}
	
	private String fixHttpPrefix(String url){
		String preparedUrl=StringUtils.trim(url);
		if(!StringUtils.startsWithIgnoreCase(preparedUrl, HTTP_PREFIX) || StringUtils.startsWithIgnoreCase(preparedUrl, HTTP_PREFIX2)){
			return HTTP_PREFIX+preparedUrl;
		}
		return preparedUrl;
	}
	
	 
	@ExceptionHandler(AnalyzingException.class)
	public ResponseEntity<ResponseError> handleParseException(AnalyzingException ex) {
		LOGGER.error("can't analyze the resource:"+ex.getMessage());
		return new ResponseEntity<ResponseError>(new ResponseError("can't be analyzed"), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(IOException.class)
	public ResponseEntity<ResponseError> handleIoException(IOException ex) {
		LOGGER.error("error during reading data from remote resource:"+ex.getMessage());
		return new ResponseEntity<ResponseError>(new ResponseError("can't read data from resource"), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<ResponseError> mandarotyParametersNotFilled(MissingServletRequestParameterException ex) {
		LOGGER.error("mandatory parameter(s) was(were) ommited:"+ex.getMessage());
		return new ResponseEntity<ResponseError>(new ResponseError("api was changed"), HttpStatus.NOT_IMPLEMENTED);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ResponseError> handleException(Exception ex) {
		LOGGER.error("Internal Server Error:"+ex.getMessage());
		return new ResponseEntity<ResponseError>(new ResponseError("Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
