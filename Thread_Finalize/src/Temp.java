
public class Temp {
	public static void main(String[] args){
		System.out.println("Main: start");
		ThreadControl thread=new ThreadControl();
		System.out.println("Main: end");
		
		try{
			Thread.sleep(750);
		}catch(Exception ex){};
		System.out.println("action");

		thread.setRun(false);
		//thread.interrupt();
	}
	
}
