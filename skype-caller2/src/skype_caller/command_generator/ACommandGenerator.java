package skype_caller.command_generator;

import java.util.Date;

import skype_caller.command.ASkypeCommand;

/**
 * root interface for command generator's 
 */
public abstract class ACommandGenerator {
	
	private ASkypeCommand command;

	public ASkypeCommand getCommand() {
		return command;
	}

	public void setCommand(ASkypeCommand command) {
		this.command=command;
	}
	/** 
	 * 
	 * @return
	 * <ul>
	 * 	<li><b>null</b> - not need to execute command in future</li>
	 * 	<li><b>seconds</b> - for execute this command in future - seconds count </li>
	 * </ul>
	 */
	public abstract Long getSecondsSleepTimeBeforeExecuteCommand();

	@Override
	public String toString(){
		if((this.name!=null)&&(this.command!=null)){
			return this.name+" "+this.command.toString();
		}else{
			if(this.name!=null){
				return name;
			}else{
				return "";
			}
		}
	}
	
	private String name="ACommandGenerator";
	public void setName(String name){
		this.name=name;
	}
	
	public static long getSecondsBetweenDates(Date dateBegin, Date dateEnd){
		if((dateEnd!=null)&&(dateBegin!=null)){
			return Math.abs((long)(dateEnd.getTime()-dateBegin.getTime())/1000);
		}else{
			return 0L;
		}
	}

	public static boolean isSecondDateInFutureTruncSeconds(Date date1, Date date2){
		if(date2.after(date1)){
			return getSecondsBetweenDates(date1, date2)!=0;
		}else{
			return false;
		}
	}
}
