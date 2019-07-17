package bonpay.jobber.executor.test;

import org.apache.log4j.Logger;
import bonpay.osgi.service.interf.IExecutor;

/** объект-заглушка для Executor */
public class Executor implements IExecutor{
	private Logger logger=Logger.getLogger(this.getClass());
	
	@Override
	public void execute() {
		logger.debug("Executor.execute: ");
	}

	@Override
	public void stop() {
		logger.debug("Executor.stop: ");
	}
}
