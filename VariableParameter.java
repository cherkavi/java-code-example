
public class VariableParameter {
	public static void main(String[] args){
		VariableParameter parameter=new VariableParameter();
		StringBuffer currentValue=new StringBuffer("value");
		System.out.println("before: "+currentValue);
		parameter.changeStringBuffer(currentValue);
		System.out.println("after: "+currentValue);
	}
	
	public VariableParameter(){
	}
	
	public void changeStringBuffer(StringBuffer value){
		value=new StringBuffer("this is changed value");
		//value.append("this is append");
	}
}
