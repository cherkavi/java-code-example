package com.test.jmx_controller;

import jmx.utility.server.description_decorator.AMBeanAttribute;
import jmx.utility.server.interfaces.IMBeanAttributes;

public class AttributeJobberName implements IMBeanAttributes{
	private String jobberName;

	@AMBeanAttribute(description="get current jobber on work")
	public String getJobberName() {
		return jobberName;
	}

	public void setJobberName(String jobberName) {
		this.jobberName = jobberName;
	}
	
	
}
