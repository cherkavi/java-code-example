
public class Resource {
	private static int counter=0;
	//private static int delay=500;
	
	// попробуйте без synchronized
	// public static int getValue(int delay){
	public static synchronized int getValue(int delay){
		int returnValue=counter++;
		try{
			Thread.sleep(delay);
		}catch(Exception ex){
			System.err.println(Thread.currentThread().getName()+" Exception:"+ex.getMessage());
		}
		return returnValue;
	}
}
