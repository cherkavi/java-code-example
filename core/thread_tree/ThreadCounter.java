package thread_tree;

import java.util.concurrent.TimeUnit;

public class ThreadCounter extends Thread{
	private volatile int counter=0;
	
	public void run(){
		do{
			counter++;
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(this.getName()+" : "+counter);
		}while(counter<10);
	}
}
