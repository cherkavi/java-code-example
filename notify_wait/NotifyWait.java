package notify_wait;

public class NotifyWait {
	public static void main(String[] args) throws InterruptedException{
		System.out.println("begin");
		Object signal=new Object();
		new Bullet("Bullet1",signal, 250, 10).start();
		
		Thread.sleep(4000);
		System.out.println("pulse");
		synchronized(signal){
			signal.notify();
		}
		
		Thread.sleep(4000);
		System.out.println("pulse");
		synchronized(signal){
			signal.notify();
		}
		
		System.out.println("-end-");
	}
}

class Bullet extends Thread{
	private Object signal;
	private int timeWait;
	private int counter;
	private String outputName;
	
	public Bullet(String outputName, Object signal, int timeWait, int counter){
		this.outputName=outputName;
		this.signal=signal;
		this.timeWait=timeWait;
		this.counter=counter;
	}
	
	private volatile boolean threadRun=true;
	
	public void stopThread(){
		this.threadRun=false;
	}
	
	private int printCounter=0;
	@Override
	public void run() {
		while(threadRun==true){
			
			System.out.println(outputName+" : "+(++printCounter));
			try{
				Thread.sleep(timeWait);
			}catch(Exception ex){};
			if(printCounter%counter==0){
				System.out.println("Wait:");
				try {
					synchronized(this.signal){
						this.signal.wait();
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
