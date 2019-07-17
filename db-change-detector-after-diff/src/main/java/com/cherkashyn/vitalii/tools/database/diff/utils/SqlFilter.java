package com.cherkashyn.vitalii.tools.database.diff.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class SqlFilter {
	private final static String MARKER_ADD=">";
	private final static String MARKER_COMMENT="--";
	private final static String MARKER_ENGINE=")";
	private final static String[] MARKERS_EXCLUDE=new String[]{MARKER_ENGINE, MARKER_COMMENT};
	
	public static List<String> filter(List<String> list){
		List<String> returnValue=new ArrayList<String>(list.size()/2);
		for(String eachLine:list){
			String filteredLine=filter(eachLine);
			if(filteredLine!=null){
				returnValue.add(filteredLine);
			}
		}
		return returnValue;
	}

	private static String filter(String eachLine) {
		String clearData=StringUtils.trim(eachLine);
		if(StringUtils.startsWith(clearData, MARKER_ADD)){
			clearData=StringUtils.trim(StringUtils.removeStart(clearData, MARKER_ADD));
			for(String eachExclude:MARKERS_EXCLUDE){
				if(StringUtils.startsWith(clearData, eachExclude)){
					return null;
				}
			}
			return clearData;
		}else{
			return null;
		}
	}
}
