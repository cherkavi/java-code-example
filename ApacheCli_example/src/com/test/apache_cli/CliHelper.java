package com.test.apache_cli;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.lang.ArrayUtils;

/**
 * class helper for parsing input parameters 
 */
public class CliHelper {
	public static enum Type{
		/**
		 * parameter represent String value 
		 */
		String(String.class){

			@Override
			Object parseStringRepresentation(java.lang.String stringRepresentation) {
				return stringRepresentation;
			}
			
		},
		/**
		 * parameter represent Integer value 
		 */
		Integer(Number.class){

			@Override
			Object parseStringRepresentation(
					java.lang.String stringRepresentation) {
				return java.lang.Integer.parseInt(stringRepresentation);
			}
			
		},
		/**
		 * parameter represent No-Value ( for present or not Present parameter )
		 */
		Void(null){

			@Override
			Object parseStringRepresentation(
					java.lang.String stringRepresentation) {
				return null;
			}
			
		}
		;
		private Class<?> clazz;
		
		Type(Class<?> clazz){
			this.clazz=clazz;
		}
		
		abstract Object parseStringRepresentation(String stringRepresentation);
		
		public Class<?> getClassOfType(){
			return this.clazz;
		}
	}
	
	public static class ParserException extends Exception {
		private final static long serialVersionUID=1L;
		
		public ParserException(String message){
			super(message);
		}
		public ParserException(Exception ex){
			super(ex);
		}
		public ParserException(String message, Exception ex){
			super(message, ex);
		}
	}
	
	/**
	 * parser for elements 
	 */
	public static class Parser{
		private final CommandLineParser commandLineParser;
		
		private Options options;
		private Map<Option, Type> optionsMap=new HashMap<Option, Type>();
		
		private Parser(){
			commandLineParser=new GnuParser();
			options=new Options();
		}
		
		public Parser appendParameter(String name, 
									  Type type, 
									  boolean mandatory){
			Option option=new Option(name ,"description "+name); // OptionBuilder.create();
			option.setArgName(name);
			option.setLongOpt(null);
			option.setRequired(mandatory);
			
			if(type.getClassOfType()!=null){
				option.setType(type.getClassOfType());
				// option.setOptionalArg(true);
				option.setArgs(1);
			}
			this.options.addOption(option);
			this.optionsMap.put(option, type);
			return this;
		}
		
		private Object getValueFromOption(Option nextOption, String nextOptionValue) {
			return this.optionsMap.get(nextOption).parseStringRepresentation(nextOptionValue);
		}
		
		
		/**
		 * parse string and return result of the parsing 
		 * @param stringForParse
		 * @return
		 */
		public Map<String, Object> parseString(String[] stringsForParse) throws ParserException{
			try{
				CommandLine commandLine=this.commandLineParser.parse(options, stringsForParse);
				Map<String, Object> returnValue=new HashMap<String, Object>();
				Iterator<Option> iterator=optionsMap.keySet().iterator();
				while(iterator.hasNext()){
					Option nextOption=iterator.next();
					String key=nextOption.getArgName();
					String value=commandLine.getOptionValue(key);
					if(isOptionsContains(commandLine.getOptions(), key)){
						// System.out.println("next Key:"+key+"   Value:"+value);
						if(nextOption.isRequired()){
							returnValue.put(key, getValueFromOption(nextOption, value));
						}else{
							try{
								returnValue.put(key, getValueFromOption(nextOption, value));
							}catch(Exception ex){
								returnValue.put(key, null );
							}
						}
					}
				}
				return returnValue;
			}catch(Exception ex){
				throw new ParserException(ex);
			}
		}

		private boolean isOptionsContains(Option[] optionsArray, String optionName) {
			for(Option option:optionsArray){
				if(optionName .equals(option.getArgName()) || optionName.equals(option.getLongOpt())){
					return true;
				}
			}
			return false;
		}

	}
	
	/** 
	 * build parser 
	 * @return
	 */
	public static Parser buildParser(){
		return new Parser();
	}
	
}
