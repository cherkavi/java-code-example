package tools.transformer.test;

import org.apache.log4j.Logger;

import tools.transformer.core.common_model.IModel;
import tools.transformer.core.destination.IDestinationIterator;
import tools.transformer.core.exceptions.ETransformerException;
import tools.transformer.core.instance.IInstance;

public class TestDestination implements IDestinationIterator{
	private Logger logger=Logger.getLogger(this.getClass());
	
	public void deInit() throws ETransformerException {
		logger.info("destination deInit");
	}

	public void init() throws ETransformerException {
		logger.info("destination init");
	}

	public void nextIteration(IModel next) throws ETransformerException {
		logger.info("destination: next Value for save: "+next);
	}

	public void nextInstanceBegin(IInstance instance) {
		logger.info("destination : next instance Begin");
	}

	public void nextInstanceEnd(IInstance instance) {
		logger.info("destination: next instance End");
	}

}
