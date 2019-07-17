package com.test.xml_generator.generators;

import com.test.xml_generator.AXmlGenerator;
import com.test.xml_generator.values_generator.ISequenceValues;

/**
 * generator for generate values:
 *  <root>
	  	<property_1>
	  		<value><key>1</key><key_value>xxx</key_value></value>
	  		<value><key>2</key><key_value>xxy</key_value></value>
	  		<value><key>3</key><key_value>xxz</key_value></value>
	  		... 
	  	</property_1>
	  	
	  	<property_2>
	  		<value><key>1</key><key_value>xyx</key_value></value>
	  		<value><key>2</key><key_value>xyy</key_value></value>
	  		<value><key>3</key><key_value>xyz</key_value></value>
	  		... 
	  	</property_2>
	  	
	  	<property_3>
	  		<value><key>1</key><key_value>xzx</key_value></value>
	  		<value><key>2</key><key_value>xzy</key_value></value>
	  		<value><key>3</key><key_value>xzz</key_value></value>
	  		... 
	  	</property_3>
	  	...
    </root>
 */

// FIXME - need to implement this data 
public class XmlCompositeElementGenerator extends AXmlGenerator{
	private final String preambulaFirst;
	private final int deepFirst;
	private final String subElementName;
	private final String subElementKeyName;
	private final String subElementKeyValueName;
	private final int deepSecond;
	
	public XmlCompositeElementGenerator(String xmlRootElement,
									 ISequenceValues iterator,
									 String preambulaFirst,
									 int deepFirst,
									 String subElementName,
									 int deepSecond,
									 String subElementKeyName,
									 String subElementKeyValueName
									 ) {
		super(xmlRootElement,iterator);
		this.preambulaFirst=preambulaFirst;
		this.deepFirst=deepFirst;
		this.subElementName=subElementName;
		this.subElementKeyName=subElementKeyName;
		this.subElementKeyValueName=subElementKeyValueName;
		this.deepSecond=deepSecond;
	}

	@Override
	protected StringBuilder getBody() {
		StringBuilder returnValue=new StringBuilder();
		for(int indexFirst=0;indexFirst<this.deepFirst;indexFirst++){
			String elementName=this.preambulaFirst+(indexFirst+1);
			returnValue.append(AXmlGenerator.getElementOpenTagString(elementName));
			for(int indexSecond=0;indexSecond<this.deepSecond;indexSecond++){
				returnValue.append(AXmlGenerator.getElementOpenTagString(this.subElementName));
				returnValue.append("\n");
				returnValue.append(AXmlGenerator.getElementAsString(this.subElementKeyName, Integer.toString(indexSecond+1)));
				returnValue.append(AXmlGenerator.getElementAsString(this.subElementKeyValueName, this.iterator.next()));
				returnValue.append(AXmlGenerator.getElementCloseTagString(this.subElementName));
			}
			returnValue.append(AXmlGenerator.getElementCloseTagString(elementName));
		}
		return returnValue;
	}

}
