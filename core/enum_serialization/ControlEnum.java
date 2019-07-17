package enum_serialization;

import java.util.ArrayList;

public enum ControlEnum {
	one,
	two,
	three,
	four;

	private ArrayList<String> parameters=new ArrayList<String>();
	
	public void addParameters(String value){
		this.parameters.add(value);
	}
	
	public int getParametersCount(){
		return this.parameters.size();
	}
	
	public String getParameter(int index){
		return this.parameters.get(index);
	}
	
	private String value=null;
	
	public void setString(String value){
		this.value=value;
	}
	
	public String getString(){
		return this.value;
	}
	
}

