package com.test.xml_generator;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public interface IXmlGenerator {
	/**
	 * represented one attirubte for XML element  
	 */
	public static class XmlAttribute{
		final private String name;
		final private String value;
		/**
		 * @param name - name of attribute 
		 * @param value - value of attribute 
		 */
		public XmlAttribute(String name, String value){
			this.name=name;
			this.value=value;
		}
		/*
		 *@return name of attribute 
		 */
		public String getName() {
			return name;
		}
		/**
		 * @return value of attribute 
		 */
		public String getValue() {
			return value;
		}
		
		@Override
		public String toString() {
			StringBuilder returnValue=new StringBuilder();
			returnValue
			.append(" ")
			.append(name)
			.append("=\"")
			.append(value)
			.append("\" ");
			return returnValue.toString();
		}
		
		/**
		 * return list of attributes as String representation 
		 * @param listOfAttributes
		 * @return
		 */
		public static String listAsString(List<XmlAttribute> listOfAttributes) {
			if(listOfAttributes==null || listOfAttributes.isEmpty()){
				return "";
			}
			StringBuffer returnValue=new StringBuffer();
			Iterator<XmlAttribute> iterator=listOfAttributes.iterator();
			while(iterator.hasNext()){
				returnValue.append(iterator.next());
			}
			return returnValue.toString();
		}
		
		/**
		 * return array of attributes as String representation 
		 * @param listOfAttributes
		 * @return
		 */
		public static String arrayAsString(XmlAttribute[] listOfAttributes) {
			if(listOfAttributes!=null && listOfAttributes.length!=0){
				return listAsString(Arrays.asList(listOfAttributes));
			}else{
				return "";
			}
			
		}
	}
	
	
	/** @return next XML text */
	String getNextXml();
}
