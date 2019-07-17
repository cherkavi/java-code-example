
public class Test2 {
	
	public static void main(String[] args){
		TempAnother temp=new TempAnother("this is temp value ");
		ITemp iTemp=(ITemp)temp;
		System.out.println(iTemp.getClass().getName());
		doIt(iTemp);
	}
	
	private static void doIt(ITemp value){
		value.doIt();
		System.out.println("Class: "+value.getClass().getName());
	}
}

class TempAnother implements ITemp{
	private String value;
	public TempAnother(String value){
		this.value=value;
	}
	
	public void print(){
		System.out.println(value);
	}
	
	public void doIt(){
		print();
	}
}

interface ITemp{
	public void doIt();
}