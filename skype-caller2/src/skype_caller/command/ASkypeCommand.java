package skype_caller.command;

import org.apache.commons.lang.StringUtils;

/**
 * command for execute 
 */
public abstract class ASkypeCommand {
	
	private String recipient;
	private String name="";
	
	/** set recipient for this command  */
	public void setRecipient(String recipient){
		this.recipient=recipient;
	}
	
	/** get recipient for this command */
	public String getRecipient(){
		return this.recipient;
	}

	/** execute command  */
	public abstract void execute();


	public void setName(String name){
		this.name=name;
	}
	
	@Override 
	public String toString(){
		return StringUtils.join(new Object[]{this.name, this.recipient}, " ");
	}
}
