package skype_caller.command_generator.impl;

import java.util.Date;

import org.apache.log4j.Logger;

import skype_caller.command_generator.ACommandGenerator;

public class SingleGenerator extends ACommandGenerator {
	private Logger logger=Logger.getLogger(this.getClass());
	private Date dateOfStart;
	
	/** set time for single execute  */
	public void setDateOfStart(Date date){
		this.dateOfStart=date;
	}
	
	@Override
	public Long getSecondsSleepTimeBeforeExecuteCommand() {
		Date currentDate=new Date();
		if(isSecondDateInFutureTruncSeconds(currentDate, dateOfStart)){
			logger.debug("return the time for execute in future ");
			return getSecondsBetweenDates(currentDate, dateOfStart);
		}else{
			logger.info("the date of begin is passed - return null ");
			return null;
		}
	}

}
