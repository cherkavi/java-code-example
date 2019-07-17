package thread_lifetime;

public class Level2 extends Thread{
	public Level2(){
		this.setDaemon(true);
		this.start();
	}
	
	@Override
	public void run(){
		while(true){
			System.out.println("Level2 pulse");
			try{
				Thread.sleep(2000);
			}catch(Exception ex){};
		}
	}
	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		System.out.println("Level2 - finalize");
	}
	
}
