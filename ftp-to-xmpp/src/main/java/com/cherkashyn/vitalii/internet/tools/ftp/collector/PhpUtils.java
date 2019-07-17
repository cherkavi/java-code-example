package com.cherkashyn.vitalii.internet.tools.ftp.collector;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.cherkashyn.vitalii.internet.tools.ftp.collector.domain.DisplayElement;

public class PhpUtils {
	/** name of files on ftp server */
	public final static String ORDER_FILE_PATTERN="order[0-9]*.txt";
	
	private PhpUtils(){
	}
	
	private static final String DIGITS_FILTER="[^0-9]*";
	
    public static Date decodeDate(String fileName){
    	String digits=fileName.replaceAll(DIGITS_FILTER, StringUtils.EMPTY);
    	if(digits.length()>0){
    		return new Date(Long.parseLong(digits)*1000);
    	}else{
    		return null;
    	}
    }
    
    
    private final static String DIRECTORY_DELIMITER="/";
    
    public static String domainName(String directory){
    	return StringUtils.trim(StringUtils.substringAfterLast(directory, DIRECTORY_DELIMITER));
    }

    private final static String MARKER_IP="ip:";

    /**
     * @param fileContent - ip:212.90.36.138,   phone:333,  name: vlad
     * @return
     */
	public static String getIpAddress(String fileContent) {
		String rawValue=StringUtils.substringAfterLast(fileContent, MARKER_IP);
		rawValue=StringUtils.trim(StringUtils.substringBeforeLast(rawValue, MARKER_PHONE));
		if(rawValue.length()>0){
			return StringUtils.substring(rawValue, 0, rawValue.length()-1);
		}else{
			return StringUtils.EMPTY;
		}
	}

	private final static String MARKER_NAME="name:";
    /**
     * @param fileContent - ip:212.90.36.138,   phone:333,  name: vlad
     * @return
     */
	public static String getClientName(String fileContent) {
		return StringUtils.substringAfterLast(fileContent, MARKER_NAME);
	}

	private final static String MARKER_PHONE="phone:";
    /**
     * @param fileContent - ip:212.90.36.138,   phone:333,  name: vlad
     * @return
     */
	public static String getClientPhone(String fileContent) {
		String rawValue=StringUtils.substringAfterLast(fileContent, MARKER_PHONE);
		rawValue=StringUtils.trim(StringUtils.substringBeforeLast(rawValue, MARKER_NAME));
		if(rawValue.length()>0){
			return StringUtils.substring(rawValue, 0, rawValue.length()-1);
		}else{
			return StringUtils.EMPTY;
		}
	}
	
	public static DisplayElement convert(String directory, String fileName, String fileContent) {
		return new DisplayElement(PhpUtils.getIpAddress(fileContent), PhpUtils.getClientName(fileContent), PhpUtils.getClientPhone(fileContent), PhpUtils.decodeDate(fileName), PhpUtils.domainName(directory)); 	
	}

}
