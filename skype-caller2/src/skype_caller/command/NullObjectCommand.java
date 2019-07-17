package skype_caller.command;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

public class NullObjectCommand extends ASkypeCommand{
	private Logger logger=Logger.getLogger(this.getClass());
	@Override
	public void execute() {
		logger.info("!!! CALL AND DROP !!! BEGIN");
		try{
			TimeUnit.SECONDS.sleep(20);
		}catch(Exception ex){
		}
		logger.info("!!! CALL AND DROP !!! END");
	}

}
