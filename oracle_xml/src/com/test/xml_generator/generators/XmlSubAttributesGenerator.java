package com.test.xml_generator.generators;

import com.test.xml_generator.AXmlGenerator;
import com.test.xml_generator.IXmlGenerator;
import com.test.xml_generator.values_generator.ISequenceValues;

/**
 * generator for generate values:
 *  <root>
	  	<property_1>
	  		<value id="1">xxx</value>
	  		<value id="2">xxy</value>
	  		<value id="3">xxz</value>
	  		... 
	  	</property_1>
	  	
	  	<property_2>
	  		<value id="1">xyx</value>
	  		<value id="2">xyy</value>
	  		<value id="3">xyz</value>
	  		... 
	  	</property_2>
	  	
	  	<property_3>
	  		<value id="1">xzx</value>
	  		<value id="2">xzy</value>
	  		<value id="3">xzz</value>
	  		... 
	  	</property_3>
	  	...
    </root>
 */
public class XmlSubAttributesGenerator extends AXmlGenerator{
	private String preambulaFirst;
	private int deepFirst;
	private String subElementName;
	private String subElementAttribute;
	private int deepSecond;
	
	public XmlSubAttributesGenerator(String xmlRootElement,
									 ISequenceValues iterator,
									 String preambulaFirst,
									 int deepFirst,
									 String subElementName,
									 String subElementAttribute,
									 int deepSecond) {
		super(xmlRootElement,iterator);
		this.preambulaFirst=preambulaFirst;
		this.deepFirst=deepFirst;
		this.subElementName=subElementName;
		this.subElementAttribute=subElementAttribute;
		this.deepSecond=deepSecond;
	}

	@Override
	protected StringBuilder getBody() {
		StringBuilder returnValue=new StringBuilder();
		for(int indexFirst=0;indexFirst<this.deepFirst;indexFirst++){
			String elementName=this.preambulaFirst+(indexFirst+1);
			returnValue.append("<").append(elementName).append(">").append("\n");
			
			for(int indexSecond=0;indexSecond<this.deepSecond;indexSecond++){
				returnValue.append(AXmlGenerator.getElementAsString(subElementName, 
										this.iterator.next(), 
										new IXmlGenerator.XmlAttribute(this.subElementAttribute, Integer.toString(indexSecond+1))));
			}
			returnValue.append("</").append(elementName).append(">\n");
		}
		return returnValue;
	}

}
