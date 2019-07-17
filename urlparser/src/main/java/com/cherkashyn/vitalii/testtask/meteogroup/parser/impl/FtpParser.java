package com.cherkashyn.vitalii.testtask.meteogroup.parser.impl;

import java.util.List;

import com.cherkashyn.vitalii.testtask.meteogroup.domain.ResultElement;
import com.cherkashyn.vitalii.testtask.meteogroup.domain.StringElement;
import com.cherkashyn.vitalii.testtask.meteogroup.parser.Element;

class FtpParser extends HttpParser{
	public final static String PROTOCOL_FTP="ftp";
	public final static String PROTOCOL_FTP_SECURE="ftps";
	private final static String HOST_DELIMITER="@";
	private final static String CREDENTIALS_DELIMITER=":";
	
	FtpParser() {
		super(PROTOCOL_FTP, PROTOCOL_FTP_SECURE);
	}

	/**
	 * parse string between {@link Element#Protocol} and {@link Element#Path} <br />
	 * try to find elements: {@link Element#User},{@link Element#Password}, {@link Element#Host} <br /> 
	 * <b>example:</b>John:JohnPassword@example.com:2222 <br />
	 * @param hostString
	 * @param elements
	 */
	protected void parseHostString(String hostString, List<ResultElement<?>> result){
		int indexHostDelimiter=hostString.indexOf(HOST_DELIMITER);
		if(indexHostDelimiter<0){
			parseHost(hostString, result);
			return;
		}

		parseHost(hostString.substring(indexHostDelimiter+1), result);
		String leftOver=hostString.substring(0,  indexHostDelimiter);
		
		if(leftOver.contains(CREDENTIALS_DELIMITER)){
			leftOver=this.biteSuffix(leftOver, CREDENTIALS_DELIMITER, Element.Password, result);
			result.add(new StringElement(Element.User, leftOver));
		}else{
			result.add(new StringElement(Element.User, leftOver));
		}
		
	}

	private void parseHost(String hostString, List<ResultElement<?>> result) {
		String leftOver=this.biteSuffix(hostString, PORT_DELIMITER, Element.Port, result, false, new IntegerConverter());
		result.add(new StringElement(Element.Host, leftOver));
		return;
	}

}
