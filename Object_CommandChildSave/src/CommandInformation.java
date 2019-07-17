
public class CommandInformation extends Command{
	private final static long serialVersionUID=2L;
	
	public CommandInformation(){
		super("CommandInformation for example");
	}
	
	@Override
	public boolean action() {
		System.out.println("this is method of CommandInformation class");
		return true;
	}

	private String getString(){
		return "CommandInformation getString private method";
	}
	
	@Override
	public int getResult() {
		System.out.println("getResult:"+getString());
		return 1;
	}

}
