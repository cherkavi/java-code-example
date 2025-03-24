package com.ubs.omnia.tools.db2csv.settings;

import org.apache.commons.lang.StringUtils;

public class ColumnSizeUtils {
	private final static String SQL_COMMENT_BEGIN="/*";
	private final static String SQL_COMMENT_END="*/";

	private static String prepareString(String value){
		return StringUtils.substringsBetween(value,SQL_COMMENT_BEGIN, SQL_COMMENT_END)[0];
	}
	
	public static int[] parseSize(String value) {
		String[] values=StringUtils.split(prepareString(value));
		
		int[] returnValue=new int[values.length];
		for(int index=0;index<returnValue.length;index++){
			try{
				returnValue[index]=Integer.parseInt(values[index].replaceAll("[^0-9]", ""));
			}catch(NumberFormatException ne){
				returnValue[index]=0;
			}
			
		}
		return returnValue;
	}

	private final static String MIDDLE="M"; 
	private final static String LEFT="L"; 
	private final static String RIGHT="R"; 
	
	public static Boolean[] parseAlignment(String value) {
		String[] values=StringUtils.split(prepareString(StringUtils.upperCase(value)));
		
		Boolean[] returnValue=new Boolean[values.length];
		for(int index=0;index<returnValue.length;index++){
			String align=values[index].replaceAll("[^"+MIDDLE+LEFT+RIGHT+"]", "");
			if(StringUtils.startsWith(align, LEFT)){
				returnValue[index]=Boolean.FALSE;
				continue;
			}
			if(StringUtils.startsWith(align, RIGHT)){
				returnValue[index]=Boolean.TRUE;
				continue;
			}
			if(StringUtils.startsWith(align, MIDDLE)){
				returnValue[index]=null;
				continue;
			}
			returnValue[index]=Boolean.TRUE;
		}
		return returnValue;
	}

}
