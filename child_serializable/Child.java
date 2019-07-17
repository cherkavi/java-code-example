package child_serializable;

import java.io.Serializable;

public class Child extends Parent implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int parentX=2;
	private String parentValue="child value: ";
	
	public Child(){
		System.out.println("child constructor");
	}
	
	public String toString() {
		return this.parentValue+parentX;
	};
}
