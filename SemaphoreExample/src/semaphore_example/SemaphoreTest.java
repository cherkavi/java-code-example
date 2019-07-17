package semaphore_example;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaphoreTest implements Runnable {
	public static void main(String[] args){
		System.out.println("begin");
		Semaphore semaphore=new Semaphore(2); // Mutext - semaphore with only one position
		Thread t1=new Thread(new SemaphoreTest(semaphore),"t1");
		Thread t2=new Thread(new SemaphoreTest(semaphore),"t2");
		Thread t3=new Thread(new SemaphoreTest(semaphore),"t3");
		Thread t4=new Thread(new SemaphoreTest(semaphore),"t4");
		t1.start();
		t2.start();
		t3.start();
		t4.start();
		System.out.println("-end-");
	}
	
	private Semaphore semaphore;
	SemaphoreTest(Semaphore semaphore){
		this.semaphore=semaphore;
	}
	
	
	public void run(){
		String currentThreadName=Thread.currentThread().getName();
		try{
			System.out.println(currentThreadName+" AvailablePermits:"+this.semaphore.availablePermits());
			System.out.println(currentThreadName+" QueueLength:"+this.semaphore.getQueueLength());
			System.out.println(currentThreadName+" DrainPermits:"+this.semaphore.drainPermits());
			// this.semaphore.acquire();
			System.out.println(currentThreadName+" thread: "+Thread.currentThread().getName()+" execute >>> begin");
			TimeUnit.SECONDS.sleep(5);
			System.out.println(currentThreadName+" thread: "+Thread.currentThread().getName()+" execute <<<  end");
			this.semaphore.release();
		}catch(Exception ex){
			System.err.println("Run Exception:"+ex.getMessage());
		}
	}
}
