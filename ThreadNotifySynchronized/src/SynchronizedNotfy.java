import java.util.concurrent.TimeUnit;


public class SynchronizedNotfy {

	public static void main(String[] args){
		System.out.println("begin");
		Object controlObject=new Object();
		new ThreadNotifier(controlObject, 2000);
		new ThreadListener(controlObject,500);
		System.out.println("end");
	}
	
}

class ThreadNotifier extends Thread{
	private Object controlObject;
	private long timeWait=0;
	public ThreadNotifier(Object controlObject, int timeWait){
		this.controlObject=controlObject;
		this.timeWait=timeWait;
		this.start();
	}
	
	@Override
	public void run(){
		while(true){
			synchronized(this.controlObject){
				System.out.println("Notifier");
				this.controlObject.notify();
			}
			try{Thread.sleep(timeWait);}catch(Exception ex){};
		}
	}
}

class ThreadListener extends Thread{
	private Object controlObject;
	private long timeWait;
	public ThreadListener(Object controlObject, int timeWait){
		this.controlObject=controlObject;
		this.timeWait=timeWait;
		this.start();
	}
	
	@Override
	public void run(){
		while(true){
			for(int counter=0;counter<5; counter++){
				System.out.println(counter);
				try{Thread.sleep(timeWait);}catch(Exception ex){};
			}
			synchronized(this.controlObject){
				System.out.println("Wait");
				try{this.controlObject.wait();}catch(Exception ex){};
				
			}
		}
	}
}
