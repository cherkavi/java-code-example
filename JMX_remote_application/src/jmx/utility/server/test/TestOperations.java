package jmx.utility.server.test;

import org.apache.log4j.Logger;

import jmx.utility.server.description_decorator.AMBeanOperation;
import jmx.utility.server.description_decorator.AMBeanOperationParameter;
import jmx.utility.server.description_decorator.AMBeanOperationSkip;
import jmx.utility.server.interfaces.IMBeanOperations;

public class TestOperations implements IMBeanOperations{
	private Logger logger=Logger.getLogger(this.getClass());
	
	@AMBeanOperationSkip
	public void setLogger(Logger logger){
		this.logger=logger;
	}
	
	@AMBeanOperation(description="Description: print to log with <header> and <footer> ")
	public void printToLog(
							@AMBeanOperationParameter(description="header of message")
							String header, 
							@AMBeanOperationParameter(description="message")
							String message){
		logger.debug("#printToLog");
		logger.info(header+message);
	}
	
	@AMBeanOperation(description="calculate amount of elements ")
	public Integer addMethod(
							@AMBeanOperationParameter(description="first argument")
							Integer a, 
							@AMBeanOperationParameter(description="second argument")
							Integer b
							){
		logger.debug("#addMethod:"+a+"  +  "+b);
		return a+b;
	}
}
