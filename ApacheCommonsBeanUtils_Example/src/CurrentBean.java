import java.util.HashMap;


public class CurrentBean{
	private Integer integerValue;
	private String name;
	

	public Integer getIntegerValue() {
		return integerValue;
	}
	public void setIntegerValue(Integer integerValue) {
		this.integerValue = integerValue;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public void printValue(Integer value){
		System.out.println("CurrentBean#printValue:"+value.toString());
	}
}
