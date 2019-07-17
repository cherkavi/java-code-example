package com.ubs.test.camel.exception.processors;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class ProcessorLogger implements Processor{

	// will be implemented into another module
	// Autowired(required=false)
	// SpecialLogger logger
	
	@Override
	public void process(Exchange arg0) throws Exception {
		Exception exception = (Exception)arg0.getProperty(Exchange.EXCEPTION_CAUGHT);
		System.out.println("Exception: "+exception);
		// if(logger!=null)logger.log(exception);
	}

}
