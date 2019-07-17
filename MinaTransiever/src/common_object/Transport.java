package common_object;

import java.io.Serializable;

public class Transport implements Serializable{
	private final static long serialVersionUID=1L;
	private String value;
	
	public Transport(){
	}

	public Transport(String value){
		this.value=value;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	
	@Override 
	public String toString(){
		return this.getValue();
	}
}
