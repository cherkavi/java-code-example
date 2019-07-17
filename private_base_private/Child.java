package private_base_private;

public class Child extends Parent{
	private String value;
	
	public Child(){
		super("parent value", 1);
		this.value="child value";
	}
	
	private int getIntValue(){
		return 2;
	}
	
	public String getValue(){
		return super.getValue()+" "+this.value;
	}
	
	public String toString(){
		return this.value;
	}
}
