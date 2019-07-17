import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.management.monitor.CounterMonitor;
import javax.management.monitor.Monitor;


public class LockExample {
	public static void main(String[] args){
		Shared shared=new Shared();
		new SharedRunner(shared).start();
		new SharedRunner(shared).start();
	}
}

class Shared {
	private Lock lock=new ReentrantLock();
	// Monitor monitor=new CounterMonitor();
	
	
	public void sharedMethod(){
		System.out.println(Thread.currentThread().getName()+":begin");
		System.out.println(Thread.currentThread().getName()+":   lock:"+lock.hashCode());
		// monitor.start();
		lock.lock();
		try{
			TimeUnit.SECONDS.sleep(3);
		}catch(Exception ex){};
		System.out.println(Thread.currentThread().getName()+":   unlock:"+lock.hashCode());
		lock.unlock();
		// monitor.stop();
		System.out.println(Thread.currentThread().getName()+":   yield()");
		Thread.yield();
		System.out.println(Thread.currentThread().getName()+":   lock():"+lock.hashCode());
		lock.lock();
		// monitor.start();
		try{
			TimeUnit.SECONDS.sleep(3);
		}catch(Exception ex){};
		System.out.println(Thread.currentThread().getName()+":   unlock():"+lock.hashCode());
		lock.unlock();
		// monitor.stop();
		System.out.println(Thread.currentThread().getName()+":end");
	}
}
 
class SharedRunner extends Thread{
	private Shared shared;
	public SharedRunner(Shared shared){
		this.shared=shared;
	}
	
	public void run(){
		shared.sharedMethod();
	}
}
