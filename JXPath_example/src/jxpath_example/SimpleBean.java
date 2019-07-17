package jxpath_example;

import java.util.Arrays;
import java.util.List;

public class SimpleBean {
	private boolean booleanValue;
	private List<Integer> listInt;
	private List<SubObject> listSubObject;
	private SubObject simpleObject;

	public static class Builder{
		public static SimpleBean build(){
			SimpleBean returnValue=new SimpleBean();
			returnValue.booleanValue=true;
			returnValue.listInt=Arrays.asList(1,2,3,4);
			returnValue.listSubObject=Arrays.asList(new SubObject(1.2f, "two"),new SubObject(1.3f, "three"), new SubObject(1.4f, "four"));
			returnValue.simpleObject=new SubObject(1.1f, "one");
			return returnValue;
		}
	}
	
	public boolean isBooleanValue() {
		return booleanValue;
	}
	public void setBooleanValue(boolean booleanValue) {
		this.booleanValue = booleanValue;
	}
	public List<Integer> getListInt() {
		return listInt;
	}
	public void setListInt(List<Integer> listInt) {
		this.listInt = listInt;
	}
	public List<SubObject> getListSubObject() {
		return listSubObject;
	}
	public void setListSubObject(List<SubObject> listSubObject) {
		this.listSubObject = listSubObject;
	}
	public SubObject getSimpleObject() {
		return simpleObject;
	}
	public void setSimpleObject(SubObject simpleObject) {
		this.simpleObject = simpleObject;
	}
	@Override
	public String toString() {
		return "SimpleBean [booleanValue=" + booleanValue + ", listInt="
				+ listInt + ", listSubObject=" + listSubObject
				+ ", simpleObject=" + simpleObject + "]";
	}
	
	
}
