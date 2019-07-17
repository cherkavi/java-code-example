package bean_utils;

import java.lang.reflect.Method;

import org.apache.commons.beanutils.BeanUtils;

public class Element {
	public static void main(String[] args){
		Element element=new Element(12,"first");
		System.out.println("Get stringValue:"+getObjectByBeanName(element, "stringValue"));
		System.out.println("Get intValue:"+getObjectByBeanName(element, "intValue"));
		try{
			String[] array=BeanUtils.getArrayProperty(element, null);
			if(array!=null){
				for(int counter=0;counter<array.length;counter++){
					System.out.println(counter+" : "+array[counter]);
				}
			}
		}catch(Exception ex){
			System.err.println("Element:"+ex.getMessage());
		}
	}
	
	/** получить get-ер метод на основании имени Bean Property и самого объекта  */
	private static Object getObjectByBeanName(Object object, String fieldName){
		Object returnValue=null;
		try{
			String methodName="get"+(fieldName.substring(0,1).toUpperCase())+fieldName.substring(1,fieldName.length());
			Method method=object.getClass().getMethod(methodName);
			returnValue=method.invoke(object);
		}catch(Exception ex){
			System.out.println("getObjectByBeanName Exception: "+ex.getMessage());
		}
		return returnValue;
	}
	

	public Element(int intValue, String stringValue){
		this.intValue=intValue;
		this.stringValue=stringValue;
	}
	
	
	private String stringValue;
	private int intValue;
	

	public void setStringValue(String value){
		this.stringValue=value;
	}
	
	public String getStringValue(){
		return this.stringValue;
	}


	public int getIntValue() {
		return intValue;
	}


	public void setIntValue(int intValue) {
		this.intValue = intValue;
	}
	
	 
}


