import java.util.concurrent.TimeUnit;


public class FinalizeException {
	public static void main(String[] args){
		System.out.println("begin");
		exec();
		try{
			System.gc();
			TimeUnit.SECONDS.sleep(5);
		}catch(Exception ex){};
		System.out.println(" end ");
	}
	
	private static void exec(){
		new Two();
	}
	
	
	static class One{
		@Override
		protected void finalize() throws Throwable {
			System.out.println("finalize one:");
		}
	}
	
	static class Two extends One{
		@Override
		protected void finalize() throws Throwable {
			// super.finalize - должен быть вызван явно 
			System.out.println("finalize two:");
		}
	}
}
