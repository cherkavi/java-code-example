package com.test.xml_generator.generators;

import com.test.xml_generator.AXmlGenerator;
import com.test.xml_generator.values_generator.ISequenceValues;

/**
 * generator for files:
 *  <root>
	  	<property_1>xxx</property_1>
	  	<property_2>xxy</property_2>
	  	<property_3>xxz</property_3>
	  	... 
    </root>
 */
public class XmlGenerator extends AXmlGenerator {
	private int propertyCount;
	private String tagPreambula;
	
	
	
	public XmlGenerator(int propertyCount, 
						String propertyPreambula,
						ISequenceValues sequenceValues
						){
		super("root", sequenceValues);
		this.propertyCount=propertyCount;
		this.tagPreambula=propertyPreambula;
	}
	
	protected StringBuilder getBody(){
		StringBuilder returnValue=new StringBuilder();
		for(int propertyIndex=0;propertyIndex<propertyCount;propertyIndex++){
			returnValue.append(getElementAsString(this.tagPreambula+(propertyIndex+1), 
										   		  this.iterator.next()));
		}
		return returnValue;
	}

}

