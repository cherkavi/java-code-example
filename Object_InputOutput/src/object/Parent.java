package object;

import java.io.Serializable;

public class Parent implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String field_information="Parent information";
	
	public Parent(){
		
	}
	public Parent(String information){
		this.field_information=information;
	}
	public String getInformation(){
		return this.field_information;
	}
}
