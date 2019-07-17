package com.test.xml_generator.generators;

import com.test.xml_generator.IXmlGenerator;
import com.test.xml_generator.values_generator.ISequenceValues;
import com.test.xml_generator.values_generator.impl.LetterSequenceGenerator;

import junit.framework.TestCase;

public class TestXmlGenerator extends TestCase{
	
	protected ISequenceValues getSequenceGenerator(){
		return new LetterSequenceGenerator(3, 65, 85); 
	}
	
	protected IXmlGenerator getXmlGenerator(){
		// return new XmlGenerator(10, "property_", this.getSequenceValue());

//		return new XmlSubAttributesGenerator("root", 
//											  this.getSequenceGenerator(),
//											  "property_",
//											  10,
//											  "value",
//											  "id",
//											  10
//											  );

		
//		return new XmlSubPairElementGenerator("root", 
//											  this.getSequenceValue(),
//											  "property_",
//											  10,
//											  "value",
//											  10,
//											  "key",
//											  "key_value"
//											  );

		return new XmlMultiValues("root", this.getSequenceGenerator(), "property_", 5, "id", 10); 
		
	}
	
	public void testMain(){
		System.out.println("begin");
		System.out.println(getXmlGenerator().getNextXml());
		System.out.println("-end-");
	}
}
