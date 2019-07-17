
public class ShutdownHook {
	public static void main(String[] args){
		System.out.println("begin");
		
		Runtime.getRuntime().addShutdownHook(new Thread(){
			public void run(){
				System.out.println("System is shutting down");
			}
		});
		System.out.println(" end ");
	}
}
