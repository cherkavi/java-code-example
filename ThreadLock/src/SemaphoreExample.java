import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.management.monitor.CounterMonitor;
import javax.management.monitor.Monitor;


public class SemaphoreExample {
	public static void main(String[] args){
		SharedObject shared=new SharedObject();
		new SharedObjectRunner(shared).start();
		new SharedObjectRunner(shared).start();
	}
}

class SharedObject {
	// private Lock lock=new ReentrantLock();
	private Semaphore semaphore=new Semaphore(1);
	
	
	public void sharedMethod() throws Exception{
		Object object=semaphore;
		System.out.println(Thread.currentThread().getName()+":begin");
		System.out.println(Thread.currentThread().getName()+":   lock:"+object.hashCode());
		semaphore.acquire();
		
		// monitor.start();
		//lock.lock();
		try{
			TimeUnit.SECONDS.sleep(3);
		}catch(Exception ex){};
		System.out.println(Thread.currentThread().getName()+":   unlock:"+object.hashCode());
		semaphore.release();
		// lock.unlock();
		// monitor.stop();
		
		System.out.println(Thread.currentThread().getName()+":   yield()");
		Thread.yield();
		System.out.println(Thread.currentThread().getName()+":   lock():"+object.hashCode());
		semaphore.acquire();
		// lock.lock();
		// monitor.start();
		try{
			TimeUnit.SECONDS.sleep(3);
		}catch(Exception ex){};
		System.out.println(Thread.currentThread().getName()+":   unlock():"+object.hashCode());
		semaphore.release();
		// lock.unlock();
		// monitor.stop();
		System.out.println(Thread.currentThread().getName()+":end");
	}
}
 
class SharedObjectRunner extends Thread{
	private SharedObject shared;
	public SharedObjectRunner(SharedObject shared){
		this.shared=shared;
	}
	
	public void run(){
		try{
			shared.sharedMethod();
		}catch(Exception ex){
			System.err.println("Exception: "+ex.getMessage());
		}
		
	}
}
