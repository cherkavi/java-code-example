
public class DefaultResource {
	public static void ActionOperation(String preambula, int value){
		for(int counter=0;counter<10;counter++){
			System.out.println("value before: "+preambula+"_"+value);
			value++;
			try{
				Thread.sleep(150);
			}catch(Exception ex){};
		}
	}
	
	private static int staticValue=0;
	// add ==>> synchronized 
	public static synchronized void ActionMethod(String preambula,int value){
		staticValue=value;
		for(int counter=0;counter<10;counter++){
			System.out.println("value before: "+preambula+"_"+staticValue);
			staticValue++;
			try{
				Thread.sleep(150);
			}catch(Exception ex){};
		}
	}

	
	public static void main(String[] args){
		new ThreadAccess("one");
		try{
			Thread.sleep(150);
		}catch(Exception ex){};
		new ThreadAccess("two");
	}
}
