package com.test.xml_generator.generators;

import com.test.xml_generator.AXmlGenerator;
import com.test.xml_generator.values_generator.ISequenceValues;

public class XmlMultiValues extends AXmlGenerator {
	private final String propertyPreambula;
	private final int propertyCount;
	private final String attributeName;
	private final int attributeCount;
	
	public XmlMultiValues(String xmlRootElement, 
						  ISequenceValues iterator,
						  String propertyPreambula,
						  int propertyCount,
						  String attributeName,
						  int attributeCount) {
		super(xmlRootElement, iterator);
		this.propertyPreambula=propertyPreambula;
		this.propertyCount=propertyCount;
		this.attributeName=attributeName;
		this.attributeCount=attributeCount;
	}

	@Override
	protected StringBuilder getBody() {
		StringBuilder returnValue=new StringBuilder();
		for(int propertyIndex=0;propertyIndex<this.propertyCount;propertyIndex++){
			for(int attributeIndex=0;attributeIndex<this.attributeCount;attributeIndex++){
				returnValue.append(
				AXmlGenerator.getElementAsString(this.propertyPreambula+Integer.toString(propertyIndex+1), 
						 this.iterator.next(), 
						 new XmlAttribute(this.attributeName, Integer.toString(attributeIndex+1))));
				
			}
		}
		return returnValue;
	}

}
