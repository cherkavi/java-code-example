package thread_lifetime;

public class Level1 extends Thread{
	public Level1(){
		this.flagRun=true;
		this.start();
		new Level2();
	}
	
	private boolean flagRun=false;
	
	public void stopThread(){
		this.flagRun=false;
	}
	
	@Override
	public void run(){
		while(flagRun){
			System.out.println("Level1 pulse");
			try{
				Thread.sleep(2000);
			}catch(Exception ex){};
		}
	}
	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		System.out.println("Level1 - finalize");
	}
}
