package com.test.jmx_controller;

import com.test.jobbers.JobberManager;

import jmx.utility.server.description_decorator.AMBeanOperation;
import jmx.utility.server.description_decorator.AMBeanOperationSkip;
import jmx.utility.server.interfaces.IMBeanOperations;

public class OperationJobber implements IMBeanOperations{
	private JobberManager manager;
	
	@AMBeanOperationSkip
	public void setJobberManager(JobberManager manager){
		this.manager=manager;
	}

	@AMBeanOperation(description="stop current jobber ")
	public void stopJobber(){
		this.manager.stopJobber(this.manager.getName());
	}
}
