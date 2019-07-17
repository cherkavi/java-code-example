package child_serializable;

public class Parent {
	private int x=1;
	private String value="parent value: ";
	
	public Parent(){
		System.out.println("parent constructor");
	}
	
	public String getValue(){
		return value+x;
	}
	
	public void changeValue(String value){
		this.value=value;
	}
	
}
