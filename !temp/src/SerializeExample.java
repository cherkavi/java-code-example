import java.io.Serializable;


public class SerializeExample {
	public static void main(String[] args){
		Serializable object=new Temp("this is value");
		System.out.println("Object: "+object.toString());
	}
}


class Temp implements Serializable{
	private final static long serialVersionUID=1L;
	private String text;
	
	public Temp(String value){
		this.text=value;
	}
	
	public String getValue(){
		return this.text;
	}
	
	public String toString(){
		return this.text;
	}
}