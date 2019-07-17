package private_base_private;

public class Parent {
	private String value;
	private int intValue; 
	
	public Parent(String value, int intValue){
		this.value=value;
		this.intValue=intValue;
	}
	
	private int getIntValue(){
		return this.intValue;
	}
	
	public String getValue(){
		return this.value;
	}
	
	public String toString(){
		return this.value;
	}
}
