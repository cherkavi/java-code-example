package jmx.utility.server.test;

import org.apache.log4j.Logger;

import jmx.utility.server.description_decorator.AMBeanAttribute;
import jmx.utility.server.description_decorator.AMBeanAttributeSkip;
import jmx.utility.server.interfaces.IMBeanAttributes;

public class TestAttribute implements IMBeanAttributes{
	private Logger logger=Logger.getLogger(this.getClass());
	private int intValue;
	private String stringValue;

	@AMBeanAttributeSkip
	@AMBeanAttribute(description="Description: test for get/set int value  ")
	public int getIntValue() {
		logger.debug("getIntValue:"+this.intValue);
		return intValue;
	}
	public void setIntValue(int intValue) {
		logger.debug("setIntValue:"+intValue);
		this.intValue = intValue;
	}
	
	public String getStringValue() {
		logger.debug("getStringValue:"+this.stringValue);
		return stringValue;
	}
	@AMBeanAttribute(description="Description: test for get/set String value ")
	public void setStringValue(String stringValue) {
		logger.debug("setStringValue:"+stringValue);
		this.stringValue = stringValue;
	}
	
	
}
