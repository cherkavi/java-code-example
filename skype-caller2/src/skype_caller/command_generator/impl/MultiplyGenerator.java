package skype_caller.command_generator.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

import skype_caller.command_generator.ACommandGenerator;

public class MultiplyGenerator extends ACommandGenerator{
	
	private LinkedList<Date> listOfExecuteDate;
	/**
	 * set list of time for execute the command 
	 * @param date
	 */
	public void setExecuteTime(List<Date> date){
		CollectionUtils.filter(date, new Predicate(){
			@Override
			public boolean evaluate(Object arg0) {
				return arg0!=null;
			}
		});
		Collections.sort(date, new Comparator<Date>(){
			@Override
			public int compare(Date arg0, Date arg1) {
				if((arg0==null)||(arg1==null)){
					return 0;
				}else{
					return (int)(arg0.getTime()-arg1.getTime());
				}
			}
		});
		this.listOfExecuteDate=new LinkedList<Date>(date);
	}
	
	private Date nextExecuteTime=null;
	
	@Override
	public Long getSecondsSleepTimeBeforeExecuteCommand() {
		Date currentDate=new Date();
		while(true){
			if(nextExecuteTime==null){
				if(this.listOfExecuteDate.isEmpty()){
					// the list is empty 
					return null;
				}else{
					this.nextExecuteTime=this.listOfExecuteDate.removeFirst();
				}
			}
			if(isSecondDateInFutureTruncSeconds(currentDate, nextExecuteTime)){
				return getSecondsBetweenDates(currentDate, this.nextExecuteTime);
			}else{
				nextExecuteTime=null;
				continue;
			}
		}
	}

}
