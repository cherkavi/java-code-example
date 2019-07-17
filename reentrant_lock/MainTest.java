package reentrant_lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class MainTest {
	public static void main(String[] args){
		System.out.println("main: begin");
		Worker worker=new Worker();
		new Thread(new Manager(worker,1)).start();
		new Thread(new Manager(worker,2)).start();
		System.out.println("main: end");
	}
	
	/*
	
			main: begin							
			Manager#run: try to start #jobOne 
			Manager#run: try to start #jobTwo 
			main: end
			Worker#jobOne begin
			Worker#jobTwo: queueLength:0
			Worker#jobOne end					 
			Manager#run: end 					
			Worker#jobTwo: begin				
			Worker#jobTwo: end					
			Manager#run: end 					
			
			Summary: ReentrantLock#lock - locked or wait for ReentrantLock#lock from another Thread
			ReentrantLock#tryLock - return true if locked and return false if another thread was ReentrantLock#lock 
	 */
}

/** class for run {@link Worker#jobOne()} or {@link Worker#jobTwo()} in different Thread */
class Manager implements Runnable{
	private Worker worker;
	private int counter;
	
	public Manager(Worker worker, int counter){
		this.worker=worker;
		this.counter=counter;
	}

	@Override
	public void run() {
		if(this.counter==1){
			System.out.println("Manager#run: try to start #jobOne ");
			this.worker.jobOne();
		}else{
			System.out.println("Manager#run: try to start #jobTwo ");
			this.worker.jobTwo();
		}
		System.out.println("Manager#run: end ");
	}
}

class Worker{
	private ReentrantLock lock=new ReentrantLock();
	
	public void jobOne(){
		if(lock.tryLock()==false){
			System.out.println("Worker#jobOne queueLength:"+lock.getQueueLength());
			lock.lock();
		}
		System.out.println("Worker#jobOne begin");
		try {
			TimeUnit.SECONDS.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Worker#jobOne end");
		lock.unlock();
	}
	
	public void jobTwo(){
		if(lock.tryLock()==false){
			System.out.println("Worker#jobTwo: queueLength:"+lock.getQueueLength());
			lock.lock();
		}
		System.out.println("Worker#jobTwo: begin");
		try {
			TimeUnit.SECONDS.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Worker#jobTwo: end");
		lock.unlock();
	}
}
