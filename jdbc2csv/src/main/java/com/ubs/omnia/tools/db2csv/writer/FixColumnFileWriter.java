package com.ubs.omnia.tools.db2csv.writer;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.ubs.omnia.tools.db2csv.exception.GenericWriterException;
import com.ubs.omnia.tools.db2csv.settings.Parameters;

public class FixColumnFileWriter extends FileWriter {
	private final int[] columnsSize;
	private final Boolean[] columnsAlignment;
	
	public FixColumnFileWriter(Parameters parameters, int[] predefinedColumnsSize)
			throws GenericWriterException {
		this(parameters, predefinedColumnsSize, createDefaultAlignment(predefinedColumnsSize.length));
	}
	
	private static Boolean[] createDefaultAlignment(int length) {
		Boolean[] returnValue=new Boolean[length];
		for(int index=0; index<length; index++){
			returnValue[index]=true;
		}
		return returnValue;
	}
	public FixColumnFileWriter(Parameters parameters, int[] predefinedColumnsSize, Boolean[] alignment)
			throws GenericWriterException {
		super(parameters);
		if(alignment==null || predefinedColumnsSize==null || predefinedColumnsSize.length!=alignment.length){
			throw new IllegalArgumentException("check length of columns and aligmnets for column ");
		}
		this.columnsSize=predefinedColumnsSize;
		this.columnsAlignment=alignment;
	}

	@Override
	public void writeHeader(List<String> headers) throws GenericWriterException {
		super.writeHeader(fixSizes(headers));
	}
	
	
	@Override
	public void writeNext(List<String> values) throws GenericWriterException {
		super.writeNext(fixSizes(values));
	}

	
	List<String> fixSizes(List<String> values) {
		List<String> returnValue=new ArrayList<String>(values.size());
		int index=0;
		for(String eachValue:values){
			returnValue.add(setLength(eachValue, columnsSize, columnsAlignment, index));
			index++;
		}
		return returnValue;
	}

	private final static String PAD=" ";
	
	String setLength(String eachValue, int[] columns, Boolean[] alignment, int index) {
		if(columns.length==0 || columns.length<=index){
			return omitNullValue(eachValue);
		}
		if(alignment[index]==null){
			return StringUtils.center(omitNullValue(eachValue), columns[index], PAD);
		}else{
			if(alignment[index]){
				return StringUtils.leftPad(omitNullValue(eachValue), columns[index], PAD);
			}else{
				return StringUtils.rightPad(omitNullValue(eachValue), columns[index], PAD);
			}
		}
		
	}
	
	String omitNullValue(String controlValue){
		if(controlValue==null){
			return StringUtils.EMPTY;
		}
		return controlValue;
	}
	
	
}
