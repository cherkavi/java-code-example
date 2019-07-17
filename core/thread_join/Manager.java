package thread_join;

import java.util.concurrent.TimeUnit;

public class Manager {
	
	/**
	 * example of call #join on each other Threads
	 * ( this is deadlock example )
	 * @param args
	 */
	public static void main(String[] args){
		System.out.println("begin");
		SimpleThread t1=new SimpleThread();
		SimpleThread t2=new SimpleThread();
		t1.setThread(t2);t1.setDelay(2000);
		t2.setThread(t1);t2.setDelay(1000);
		t1.start();
		t2.start();
		System.out.println("=end=");
	}
}

class SimpleThread extends Thread{
	private long delay=2000;
	public void setDelay(long value){
		this.delay=value;
	}
	
	private Thread thread;
	public void setThread(Thread thread){
		this.thread=thread;
	}
	
	@Override
	public void run() {
		System.out.println(this.getName()+" : start ");
		try {
			TimeUnit.MILLISECONDS.sleep(this.delay);
			System.out.println(this.getName()+" Join ");
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(this.getName()+" :  end  ");
	}
}
